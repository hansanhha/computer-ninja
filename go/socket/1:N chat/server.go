package main

import (
	"bufio"
	"fmt"
	"io"
	"log"
	"net"
)

type client struct {
	conn     net.Conn
	reader   *bufio.Reader // buffered reader that reads messages sent by the client
	writer   *bufio.Writer // buffered writer that send messages to client
	incoming chan string   // queue that stores messages sent by client to the server
	outgoing chan string   // queue that stores messages sent by server to the clients
}

type chatMessage struct {
	sender  *client // the client that send the message
	content string
}

var clientsMap = make(map[*client]bool) // all currently connected clients
var newClients = make(chan *client)     // new client connection channel
var deadClients = make(chan *client)    // connection terminated clients
var messages = make(chan chatMessage)        // all received message from clients

// handling single connection that reads message sent by client and send it to the message channel,
// invoke goroutine which sends messages sent by other client to the corresponding client
func handleClient(c *client) {

	// A goroutine that reads messages from client
	go func() {
		for {
			line, err := c.reader.ReadString('\n')
			if err != nil {
				if err == io.EOF {
					
				} else if netErr, ok := err.(net.Error); ok && netErr.Timeout() {
					log.Printf("Client %s read timeout ", c.conn.RemoteAddr().String())
				} else {
					log.Printf("Error reading from client: %s (%v)", c.conn.RemoteAddr().String(), err)
				}
				deadClients <- c // send disconnected connection to dead channel
				return
			}
			c.incoming <- line
		}
	}()

	// A goroutine that writes messages sent by other clint to the client
	go func() {
		for msg := range c.outgoing {
			_, err := c.writer.WriteString(msg)
			if err != nil {
				log.Printf("Error writing to client: %s (%v)", c.conn.RemoteAddr().String(), err)
				deadClients <- c // send connection to dead channel when write error occurs
				return
			}
			c.writer.Flush()
		}
	}()

	// send received message from client to message channel
	for msg := range c.incoming {
		messages <- chatMessage{sender: c, content: fmt.Sprintf("[%s] %s", c.conn.RemoteAddr().String(), msg)}
	}
}

// manage clients connected to the server and forwards messages received from a client to all others
func startChatServer() {
	for {
		select {
		case c := <-newClients: // when connecting with a new client
			clientsMap[c] = true
			log.Printf("Connected a new client: %s. Total clients: %d", c.conn.RemoteAddr().String(), len(clientsMap))

		case c := <-deadClients: // when the connection with the client is lost
			if _, ok := clientsMap[c]; ok {
				delete(clientsMap, c)
				close(c.outgoing) // close the outgoing channel of corresponding client
				c.conn.Close()
				log.Printf("Disconnected a client: %s. Total clients: %d", c.conn.RemoteAddr().String(), len(clientsMap))
			}

		case msg := <-messages: // broadcast to all connected clients when received message from a client
			log.Printf("Broadcasting: %s", msg.content)
			for c := range clientsMap {
				if c != msg.sender {
					select {
					case c.outgoing <- msg.content: // try sending message to the client's message sending channel
					default: // if the channel is full or not ready to receive, message transmission is skipped
						log.Printf("Skipping messsage for client: %s (outgoing channel full)", c.conn.RemoteAddr().String())
					}
				}
			}
		}
	}
}

func main() {
	const (
		SERVER_HOST = "localhost"
		SERVER_PORT = "9988"
		SERVER_TYPE = "tcp"
	)

	listener, err := net.Listen(SERVER_TYPE, SERVER_HOST+":"+SERVER_PORT)
	if err != nil {
		log.Fatalf("Error listening: %v", err)
	}
	log.Println("Chat server listening on ", SERVER_HOST+":"+SERVER_PORT)
	defer listener.Close()

	go startChatServer()

	for {
		// waits(blocking) for a new client conenction and returns the net.Conn object when the connection is accepted
		// it can continue to accept connections at the same time from multiple clients by calling Accept() multiple times within a for loop
		conn, err := listener.Accept()
		if err != nil {
			log.Printf("Error accepting connection: %v\n", err)
			return
		}

		c := &client{
			conn:     conn,
			reader:   bufio.NewReader(conn),
			writer:   bufio.NewWriter(conn),
			incoming: make(chan string),
			outgoing: make(chan string),
		}

		newClients <- c
		go handleClient(c)
	}

}
