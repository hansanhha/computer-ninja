package main

import (
	"bufio"
	"context"
	"encoding/json"
	"fmt"
	"io"
	"log"
	"net"
	"os"
	"strconv"
	"strings"
	"sync"
	"time"

	"github.com/google/uuid"
	"rpc-example/common"
)

// struct that allows a client send an RPC requset to the server
type clientRPCCaller struct {
	conn       net.Conn
	reader 	   *bufio.Reader
	writer 	   *bufio.Writer
	reqMutex   sync.Mutex
	pendingRes sync.Map // key: requestId (string), value chan RPCResponse
}

func newClientRPCCaller(conn net.Conn) *clientRPCCaller {
	return &clientRPCCaller{
		conn: conn,
		reader: bufio.NewReader(conn),
		writer: bufio.NewWriter(conn),
		pendingRes: sync.Map{},
	}
}

func (c *clientRPCCaller) startResponseListener(ctx context.Context, cancel func()) {
	decoder := json.NewDecoder(c.reader)

	for {
		select {
		case <- ctx.Done():
			return
		default:
		}

		c.conn.SetReadDeadline(time.Now().Add(1 * time.Minute))

		var res common.RPCResponse
		err := decoder.Decode(&res)
		if err != nil {
			if err == io.EOF {
				log.Println("Server disconnected")
			} else if netErr, ok := err.(net.Error); ok && netErr.Timeout() {
				log.Println("Server response timeout")
			} else {
				log.Printf("Error decoding response: %v", err)
			}
			cancel()
			return
		}

		log.Printf("Received RPC server response for ID: %s Result: %.2f Error: %s", res.ID, res.Result, res.Error)

		if ch, ok := c.pendingRes.Load(res.ID); ok {
			ch.(chan common.RPCResponse) <- res
			c.pendingRes.Delete(res.ID)
		} else {
			log.Printf("Received response for unknown or already processed ID: %s", res.ID)
		}
	}	
}

func (c *clientRPCCaller) callRPC(ctx context.Context, req common.RPCRequest) (common.RPCResponse, error){
	resCh := make(chan common.RPCResponse)
	c.pendingRes.Store(req.ID, resCh)

	c.reqMutex.Lock()
	encoder := json.NewEncoder(c.writer)
	err := encoder.Encode(req)
	if err != nil {
		c.reqMutex.Unlock()
		return common.RPCResponse{}, fmt.Errorf("error encoding request: %v", err)
	}

	err = c.writer.Flush()
	c.reqMutex.Unlock()
	if err != nil {
		return common.RPCResponse{}, fmt.Errorf("error sending reques: %v", err)
	}

	log.Printf("Send request ID %s", req.ID)

	select {
	case res := <- resCh:
		return res, nil
	case <- ctx.Done():
		c.pendingRes.Delete(req.ID)
		return common.RPCResponse{}, ctx.Err()
	}
}

func main() {
	conn, err := net.Dial(common.SERVER_TYPE, common.SERVER_HOST+":"+common.SERVER_PORT)
	if err != nil {
		log.Fatalf("Error connecting to server: %v", err)
	}
	defer conn.Close()
	log.Printf("Connected to RPC server on %s:%s. Type 'exit' to quit", common.SERVER_HOST, common.SERVER_PORT)

	rpcCaller := newClientRPCCaller(conn)

	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	go rpcCaller.startResponseListener(ctx, cancel)

	reader := bufio.NewReader(os.Stdin)
	for {
		fmt.Printf("Enter command: <operation> <operands> (supported operations: %s) (e.g, %s 1 2 3)\n> ",
		common.SupportedOperationTypes[0], common.SupportedOperationTypes)

		input, _ := reader.ReadString('\n')
		input = strings.TrimSpace(input)

		select {
		case <- ctx.Done():
			return
		default:
		}
		
		if input == "exit" {
			log.Printf("Exiting RPC client")
			cancel()
			return
		}

		if input == "" {
			continue
		}

		parts := strings.Fields(input)
		if len(parts) < 1 {
			continue
		}

		opType := common.OperationType(parts[0])
		operands := []float64{}

		for _, s := range parts[1:] {
			val, err := strconv.ParseFloat(s, 64)
			if err != nil {
				fmt.Printf("invalid oeprand '%s'. please enter number\n", s)
				continue
			}
			operands = append(operands, val)
		}

		reqID := uuid.New().String()

		req := common.RPCRequest {
			ID: 	  reqID,
			Type: 	  opType,
			Operands: operands,
		}

		reqCtx, reqCancel := context.WithTimeout(ctx, 5*time.Second)
		defer reqCancel()

		res, err := rpcCaller.callRPC(reqCtx, req)
		if err != nil {
			if err == context.Canceled {
				fmt.Printf("Request ID %s cancelled or timeout: %v\n", reqID, err)
			} else {
				fmt.Printf("Error during RPC call for ID %s: %v\n", reqID, err)
			}
			continue
		}
		
		if res.Error != "" {
			fmt.Printf("RPC Error Response for ID: %s Error: %s\n\n", res.ID, res.Error)
		} else {
			fmt.Printf("RPC Successful Response for ID: %s Result: %.2f\n\n", res.ID, res.Result)
		}
	}
}