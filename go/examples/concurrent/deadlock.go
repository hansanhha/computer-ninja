package main

import (
	"sync"
	"time"
)

var wg sync.WaitGroup

func main() {

	wg.Add(1)

	// fatal error: all goroutines are asleep - deadlock!
	go func() {
		time.Sleep(time.Second * 2)
		wg.Wait()
	}()

	wg.Wait()
}
