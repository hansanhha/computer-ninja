package common

import "errors"

const (
	SERVER_HOST = "localhost"
	SERVER_PORT = "8080"
	SERVER_TYPE = "tcp"
)

// type of operation the client is requesting
type OperationType string

const (
	AddOperation      OperationType = "add"
	SubtractOperation OperationType = "subtract"
)

var SupportedOperationTypes []OperationType = []OperationType{AddOperation, SubtractOperation}

type RPCRequest struct {
	ID       string        `json:"id"`       // request identifier
	Type     OperationType `json:"type"`     // request type (add, subtract ...)
	Operands []float64     `json:"operands"` // numbers to be used in operation
}

type RPCResponse struct {
	ID     string  `json:"id"`     // request identifier
	Result float64 `json:"result"` // opertion result
	Error  string  `json:"error"`  // error message
}

var ErrInvalidOperands = errors.New("invalid number of operands for operation")
var ErrUnknownRequestType = errors.New("unknown request type")
