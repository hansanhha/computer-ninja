package main

import "fmt"


type Speaker[T string] interface {
	Speak(T) T
}

type HelloMachine struct {
	name string
	count int
}

func (h *HelloMachine) Speak(greeting string) string {
	return greeting + h.name
}

func main() {
	var h Speaker[string] = &HelloMachine{name: "hansanhha"}

	fmt.Println(h.Speak("hello "))
}
