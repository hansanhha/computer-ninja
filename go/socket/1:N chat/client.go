package main

import (
	"bufio"
	"context"
	"fmt"
	"io"
	"net"
	"os"
	"strings"
)

func main() {
	conn, err := net.Dial("tcp", "localhost:9988")
	if err != nil {
		fmt.Println("Error connecting to server: %v\n",err)
		return
	}
	defer conn.Close()
	fmt.Println("Connected to server on localhost:9988. Type 'exit' to quit")

	// received message from the server
	type message struct { 
		content string
		err     error
	}

	// queue that stores messages received by the server
	messages := make(chan message)            
	serverReader := bufio.NewReader(conn)    // buffered reader that reads messages sent by the server
	serverWriter := bufio.NewWriter(conn)    // buffered writer that send messages to server
	stdinReader := bufio.NewReader(os.Stdin) // buffered reader for receiving user input

	client, quitClient := context.WithCancel(context.Background())
	defer quitClient()

	// print server sending message on standard output by comsuming message channel
	// it terminates when the server shutdown or user types 'exit'
	go func() {
		for {
			select {
			case <- client.Done():
				return
			case msg := <- messages:
				if msg.err != nil {
					if msg.err == io.EOF {
						fmt.Println("Server disconnected")
					} else  if netErr, ok := msg.err.(net.Error); ok && netErr.Timeout() {
						fmt.Println("Server read timeout")
					} else {
						fmt.Printf("Error reading from server: %v\n", err)
					}
					// send message reading termination signal
					quitClient()
					return
				}
				fmt.Print(msg.content)
				fmt.Print("> ")
			default:				
			}
		}
	}()

	// receive message sent by server, send it to the client message channel
	go func() {
		for {
			// ReadString method is blocking
			content, err := serverReader.ReadString('\n')
			messages <- message{content: content, err: err}
		}
	}()

	// main routine that reads user input and sends them to the server
	for {
		fmt.Printf("> ") // input prompt
		msg, _ := stdinReader.ReadString('\n')
		msg = strings.TrimSpace(msg)

		select {
			case <- client.Done():
				return
			default:
		}

		if msg == "exit" {
			fmt.Println("Existing chat client")
			quitClient() // send message reading termination singal to reading goroutine
			return
		}

		if msg == "" {
			continue
		}

		_, err := serverWriter.WriteString(msg + "\n")
		if err != nil {
			fmt.Printf("Error sending message: %v\n", err)
			quitClient()
			return
		}
		serverWriter.Flush()
	}

}