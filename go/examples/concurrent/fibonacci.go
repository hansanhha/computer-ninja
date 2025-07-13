package main

import "fmt"

func main() {

	fibonacci := func() chan uint64 {
		c := make(chan uint64)

		go func() {
			var x, y uint64 = 0, 1

			// 송수신 연산은 단순 문장으로 취급되어 기본 제어문 블록에서 사용할 수 있다
			for ; y < (1 << 63); c <- y { 
				x, y = y, x+y
			}
			close(c)
		}()

		return c
	}

	c := fibonacci()

	// 수신 연산은 항상 단일 값 표현식으로써 사용할 수 있다
	// 송수신 연산은 단순 문장으로 취급되어 기본 제어문 블록에서 사용할 수 있다
	for x, ok := <-c; ok; x, ok = <- c {
		fmt.Println(x)
	}
}
