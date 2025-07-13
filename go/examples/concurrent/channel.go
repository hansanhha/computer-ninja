package main

import (
	"fmt"
)


func unbuffered_channel() {
	fmt.Printf("\n\n기본 비버퍼 채널 사용\n")

	ch := make(chan int)

	go func() {
		ch <- 10    // 값 송신
	}()

	val := <- ch     // ch 채널에서 값을 수신
	fmt.Println(val) // 10

	close(ch)

	_, ok := <-ch // ch가 비어있으므로 0, false이 반환된다
	if !ok {
		fmt.Println("채널이 닫혔다")
	}
}

func buffered_channel() {
	fmt.Printf("\n\n버퍼 채널(for range 수신)\n")
	const bufSize = 5

	ch := make(chan int, bufSize)
	
	go func() {
		for i := range bufSize {
			ch <- i
		}
		close(ch) // 송신이 끝나면 채널 닫기, 버퍼에 값이 있는 상태
	}()

	for val := range ch {
		fmt.Println("값 수신:", val)
	}

	// 위의 채널 for range 제어문은 아래와 같다

	/*
	for {
		val, ok = <-ch

		if !ok {
			break
		}
	}
	*/

}

func main() {

	unbuffered_channel()

	buffered_channel()
}
