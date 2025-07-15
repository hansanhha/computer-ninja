package main

import (
	"fmt"
	"net"
)

func main() {

	// establishes tcp/ip connnection (host:port)
	conn, err := net.Dial("tcp", "localhost:9988")
	if err != nil {
		panic(err)
	}

	// send data bytes to established connnection object
	fmt.Println("Sending: Hello Server")
	_, err = conn.Write([]byte("Hello Server"))

	buffer := make([]byte, 1024)

	// read data bytes with the same connnection
	mLen, err := conn.Read(buffer)
	if err != nil {
		fmt.Println("Error reading:", err.Error())
	}
	fmt.Println("Received:", string(buffer[:mLen]))
	
	defer conn.Close()
}