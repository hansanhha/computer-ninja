package main

import (
	"fmt"
	"example/message"
)

func main() {
	
	greeting := message.GetGreetingMessage()
	farewell := message.GetFarewellMessage()

	fmt.Println(greeting)
	fmt.Println(farewell)

}