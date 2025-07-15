package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net"
	"sync"
	"time"

	"rpc-example/common"
)

// status of each client connected to the server
type clientState struct {
	conn   net.Conn
	reader *bufio.Reader
	writer *bufio.Writer
	mu     sync.Mutex // writer concuccrency protection
}

// the client's request info decoded as json
type clientRequest struct {
	client  *clientState
	request common.RPCRequest
}

// server global state which includes connected all clients
type rpcServer struct {
	clients    		  map[net.Conn]*clientState
	clientsMutex   	  sync.RWMutex    		 // client map protection
	clientRequestChan chan clientRequest 	 // channel to process requests received from connected clients
}

func newRPCServer() *rpcServer {
	return &rpcServer{
		clients:     	   make(map[net.Conn]*clientState),
		clientRequestChan: make(chan clientRequest),
	}
}

// create state of connected client and put them on server's client map
// decode request of client and push it to server's queue that process request operation
// also in this goroutine, we call a defer anonymous function that performs the connection release
func (s *rpcServer) handleClientConnection(conn net.Conn) {
	log.Printf("Connnected client: %s", conn.RemoteAddr().String())

	defer conn.Close()

	cs := &clientState{
		conn:   conn,
		reader: bufio.NewReader(conn),
		writer: bufio.NewWriter(conn),
	}

	// register client state to client map (write lock)
	s.clientsMutex.Lock()
	s.clients[conn] = cs
	s.clientsMutex.Unlock()

	// defer function that remove client state from client map with write lock
	defer func() {
		s.clientsMutex.Lock()
		delete(s.clients, conn)
		s.clientsMutex.Unlock()
		log.Printf("Disconneted cliet: %s", conn.RemoteAddr().String())
	}()

	// decode request sent by client and send cilentRequest to server operation queue
	decoder := json.NewDecoder(cs.reader)
	for {
		// timeout if there is no data for one minute
		conn.SetReadDeadline(time.Now().Add(1 * time.Minute))

		var req common.RPCRequest
		err := decoder.Decode(&req)
		if err != nil {
			if err == io.EOF { // client terminated the connection normally
				return 
			} else if netErr, ok := err.(net.Error); ok && netErr.Timeout() {
				log.Printf("Timeout for reading the client: %s, closing connection", conn.RemoteAddr())
				return
			}

			// response to an invalid request
			log.Printf("Error decoding request from %s: %v", conn.RemoteAddr(), err)
			s.sendResponse(cs, common.RPCResponse{
				ID:    req.ID,
				Error: fmt.Sprintf("invalid request format: %v", err.Error()),
			})
			continue // continue process the next request for corresponding client connection
		}

		s.clientRequestChan <- clientRequest{client: cs, request: req} // send recevied the operation request to processing channel
	}

}

func (s *rpcServer) processRequests() {
	for clientReq := range s.clientRequestChan {
		var res common.RPCResponse
		cs := clientReq.client
		req := clientReq.request
		res.ID = req.ID

		log.Printf("Processing request ID: %s Type: %s Operands: %v", req.ID, req.Type, req.Operands)

		switch req.Type {
		case common.AddOperation:
			if len(req.Operands) < 2 {
				res.Error = common.ErrInvalidOperands.Error()
			} else {
				sum := 0.0
				for _, op := range req.Operands {
					sum += op
				}
				res.Result = sum
			}
		case common.SubtractOperation:
			if len(req.Operands) < 2 {
				res.Error = common.ErrUnknownRequestType.Error()
			} else {
				diff := req.Operands[0]
				for i := 1; i < len(req.Operands); i++ {
					diff -= req.Operands[i]
				}
				res.Result = diff
			}
		default: 
			res.Error = common.ErrUnknownRequestType.Error()
		}

		s.sendResponse(cs, res)
	}
}

func (s *rpcServer) sendResponse(cs *clientState, res common.RPCResponse) {
	cs.mu.Lock()
	defer cs.mu.Unlock()

	encoder := json.NewEncoder(cs.writer)
	err := encoder.Encode(res)
	if err != nil {
		log.Printf("Error encoding response for %s: %v", cs.conn.RemoteAddr(), err)
		return
	}
	err = cs.writer.Flush()
	if err != nil {
		log.Printf("Error sending response for %s: %v", cs.conn.RemoteAddr(), err)
	}

	log.Printf("Sending response request ID: %s Result: %.2f", res.ID, res.Result)
}

func main() {
	server := newRPCServer()

	listener, err := net.Listen(common.SERVER_TYPE, common.SERVER_HOST+":"+common.SERVER_PORT)
	if err != nil {
		log.Fatalf("Error RPC server start: %v", err)
	}
	defer listener.Close()
	log.Printf("RPC server successfully start. listening on %s:%s", common.SERVER_HOST, common.SERVER_PORT)

	go server.processRequests()

	for {
		conn, err := listener.Accept()
		if err != nil {
			log.Printf("Error accepting connection: %v", err)
			return
		}
		go server.handleClientConnection(conn)
	}
}
