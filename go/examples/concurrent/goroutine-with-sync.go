package main

import (
	"log"
	"math/rand"
	"sync"
	"time"
)

var wg sync.WaitGroup

func greetings(greeting string, times int) {
	for i := 0; i < times; i++ {
		log.Println(greeting)
		d := time.Second * time.Duration(rand.Intn(3)) / 2
		time.Sleep(d) // 0 ~ 1.5초 동안 유저 스레드 슬립
	}
	
	// 작업 종료 알림, wg.Add(-1)과 동일하다
	wg.Done()
}


// 메인 고루틴
func main() {

	log.SetFlags(0)

	// 작업 두 개 등록
	wg.Add(2) 

	// 고루틴 2개 생성
	go greetings("hi", 10)
	go greetings("hello", 10)

	// 모든 작업이 끝날 때까지 메인 고루틴이 블로킹된다
	wg.Wait()

	println("모든 작업 종료")

}

