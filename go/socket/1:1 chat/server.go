package main

import (
	"fmt"
	"net"
	"os"
)

func main() {
	fmt.Println("Server Running...")

	server, err := net.Listen("tcp", "localhost:9988")
	if err != nil {
		fmt.Println("Error Listening:", err.Error())
		os.Exit(1)
	}
	defer server.Close()

	fmt.Println("Listening on " + "localhost:9988")
	fmt.Println("Wainting for client...")

	for {
		conn, err := server.Accept()
		if err != nil {
			fmt.Println("Error accepting:", err.Error())
			os.Exit(1)
		}

		fmt.Println("Connected to client:", conn.RemoteAddr().String())
		go communicate(conn)
	}

}

func communicate(conn net.Conn) {
	buffer := make([]byte, 1024)
	mLen, err := conn.Read(buffer)
	if err != nil {
		fmt.Println("Error reading:", err.Error())
	}

	fmt.Println("Received:", string(buffer[:mLen]))

	_, err = conn.Write([]byte("Hello Client\tYour Message: " + string(buffer[:mLen])))
	conn.Close()
}
