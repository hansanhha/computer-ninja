package main

import (
	"fmt"
	"time"
)

func panic_with_recover() {
	defer func() {
		fmt.Println("정상 종료")
	}()

	fmt.Println("hi")

	defer func() {
		v := recover()
		fmt.Println("패닉 복구:", v)
	}()

	panic("패닉 발생")

}

func panic_without_recover() {
	fmt.Println("hi")

	go func() {
		panic("패닉 발생")
	}()

	time.Sleep(time.Second)
}

func main() {
	panic_with_recover()
	// panic_without_recover()
}
