package main

import (
	"log"
	"math/rand"
	"time"
)

func greetings(greeting string, times int) {
	for i := 0; i < times; i++ {
		log.Println(greeting)
		d := time.Second * time.Duration(rand.Intn(3)) / 2
		time.Sleep(d) // 0 ~ 1.5초 동안 유저 스레드 슬립
	}
}


// 메인 고루틴
func main() {

	log.SetFlags(0)

	// 고루틴 2개 생성
	go greetings("hi", 10)   
	go greetings("hello", 10) 

	// 메인 고루틴이 종료되면 전체 프로그램이 종료되어 실행 중인 다른 고루틴도 함께 종료된다
	time.Sleep(2 * time.Second) 
}
