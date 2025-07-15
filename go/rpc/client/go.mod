module rpc-example/client

go 1.24.4

require (
	github.com/google/uuid v1.6.0
	rpc-example/common v0.0.1
)

replace rpc-example/common v0.0.1 => ../common
