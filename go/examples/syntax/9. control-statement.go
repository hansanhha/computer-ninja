package main

import (
	"fmt"
	"math/rand"
	"time"
)

func if_else() {

	if n := rand.Intn(100); n%2 ==0 {
		fmt.Println(n, "is an even number")
	} else {
		fmt.Println(n, "is an odd number")
	}

	// 위 if-else의 초기문에 사용된 n과 다른 변수이다
	n := rand.Int() % 2

	if n % 2 == 0 { fmt.Println("An even number") }
	if ; n % 2 != 0 { fmt.Println("An odd number") }

	// else-if 사용
	if h := time.Now().Hour(); h < 12 {
		fmt.Println("Now is AM time")
	} else if h > 19 {
		fmt.Println("Now is evening time")
	} else {
		fmt.Println("Now is after noon time")
	}

}

func for_loop() {

	for i := 0; i < 10; i++ {
		fmt.Println(i)
	}

	var i = 0
	// 세미콜론으로 초기문이나 후속문을 생략할 수 있다
	// 이터레이션마다 조건문만 평가하여 다른 프로그래밍 언어의 while 문과 같아진다
	for ; i < 10; {
		fmt.Println(i)
		i++
	}

	i = 0
	// 초기문과 후속문이 모두 생략된 경우 세미콜론도 생략할 수 있다
	for i < 10 {
		fmt.Println(i)
		i++
	}

	// 조건문을 생략하면 컴파일러는 true로 취급한다
	// for i := 0; true; i++
	for i := 0; ; i++ {  
		if i >= 10 {
			break
		}
		fmt.Println(i)
	}

	for i := 0; i < 3; i++ {

		// 루프 변수 i 값 출력
		fmt.Println(i)

		// 왼쪽 i: 새로 선언한 변수
		// 오른쪽 i: 루프 변수
		i := i
		i = 10

		// 10 출력
		fmt.Println(i)
	}

	// 무한히 반복하는 반복문
	// for ; true; {
	// }
	// for true {
	// }
	// for ; ; {
	// }
	// for {
	// }

	i = 0
	for i = range 10 {
		fmt.Println(i)
	}

	for j := range 10 {
		fmt.Println(j)
	}
	
}

func switch_case() {

	// switch n := rand.Intn(100); n%9 {

	// case 0:
	// 	fmt.Println(n, "is a multiple of 9.")
		
	// case 1,2,3:
	// 	fmt.Println(n, "mod 9 is 1, 2 or 3.")

	// case 4,5,6:
	// 	fmt.Println(n, "mod 9 is 4, 5 or 6.")
	
	// default:
	// 	fmt.Println(n, "mod 9 is 7 or 8.")
	// }


	switch n := rand.Intn(100) % 5; n {

	case 0, 1, 2, 3, 4:
		fmt.Println("n = ", n) // n
		fallthrough // 다음 스위치문 실행

	case 5, 6, 7, 8:
		n := 99
		fmt.Println("n = ", n) // 99
		fallthrough // 다음 스위치문 실행
	
	default:
		fmt.Println("n = ", n) // n
	}

	switch n := 5; n {

	}

	switch 5 {

	}

	switch _ = 5; {

	}

	switch {
		
	}

	// switch true 와 동일하다
	switch { 
	case true: fmt.Println("hello")
	default: fmt.Println("bye")
	}
}

func goto_label() {

	i := 0

Next:
	println(i)
	i++
	if i < 5 {
		goto Next
	}


	// 라벨 선언 이전/이후 상관없이 사용할 수 있다
	goto PrintName

	PrintName:
		println("hello goto")

	// 라벨이 선언된 코드 블록 바깥에서 라벨을 참조할 수 없다
	// goto Label1
	// {
	// 	Label:
	// 	goto Label2
	// }
	// {
	// 	Label2:
	// }

	// 변수 k의 범위 밖에 선언된 라벨
	Next2:
		if i >= 5  {
			// 컴파일 오류, 변수 선언을 건너뛰고 점프할 수 없다
			// goto Exit
		}

		k := i + 1
		println(k)
		i++
		goto Next2

	// 변수 k의 범위에 선언된 라벨
	// k의 범위 밖에서만 라벨을 사용할 수 있다
	// Exit:
}


// 두 개의 채널 중 먼저 데이터가 도작하는 쪽의 값을 수신한다
func select_case1() {

	ch1 := make(chan int)
	ch2 := make(chan int)

	go func() {
		time.Sleep(1 * time.Second)
		ch1 <- 10
	}()

	go func() {
		time.Sleep(2 * time.Second)
		ch1 <- 20
	}()

	select {
	case v1 := <-ch1:
		fmt.Println("메시지 수신:", v1)
	case v2 := <-ch2:
		fmt.Println("메시지 수신:", v2) 
	}
	
}

// 채널 작업 타임아웃 처리
func select_case2() {

	mc := make(chan string)

	go func() {
		time.Sleep(2 * time.Second)
		mc <- "hello"
	}()

	select {
	case msg := <-mc:
		fmt.Println("메시지 수신:", msg)
	case <- time.After(1 * time.Second): // 채널 작업이 특정 시간 내에 완료되지 않으면 타임아웃 처리
		fmt.Println("타임아웃 발생")
	}

}


// 논블로킹 채널 송수신 처리 (default 절 사용)
func select_case3() {
	dc := make(chan int, 1) // 버퍼 크기: 1

	// 버퍼에 공간이 있으므로 송신 성공
	select {
	case dc <- 1:
		fmt.Println("채널 데이터 송신 성공 (값: 1)")
	default:
		fmt.Println("채널 데이터 송신 실패 (값: 1)")
	}

	// 버퍼에 공간이 없으므로 송신 실패
	select {
	case dc <- 2:
		fmt.Println("채널 데이터 송신 성공 (값: 2)")
	default:
		fmt.Println("채널 데이터 송신 실패 (값: 2)")
	}

	// 버퍼에 값이 있으므로 수신 성공
	select {
	case v := <- dc:
		fmt.Println("채널 데이터 수신 성공 (값:", v, ")")
	default:
		fmt.Println("채널 데이터 수신 실패")
	}

	// 버퍼가 비었으므로 송신 성공
	select {
	case dc <- 3:
		fmt.Println("채널 데이터 송신 성공 (값: 3)")
	default:
		fmt.Println("채널 데이터 송신 실패 (값: 3)")
	}

	// 버퍼 비우기
	for len(dc) > 0 {
		_ = <-dc
	}

	// 버퍼가 비었으므로 수신 실패
	select {
	case v := <- dc:
		fmt.Println("채널 데이터 수신 성공 (값:", v, ")")
	default:
		fmt.Println("채널 데이터 수신 실패")
	}


}


func type_switch(s Shape) {

	switch v := s.(type) {
	case Circle:
		fmt.Println("원")
		fmt.Println(v.Area())
	case Rectangle:
		fmt.Println("직사각형")
		fmt.Println(v.Area())
	case nil:
		fmt.Println("nil")
	default:
		fmt.Println(v.Area())
	}
}

func main() {

	// if_else()
	// for_loop()
	// switch_case()
	// goto_label()
	// select_case1()
	// select_case2()
	// select_case3()

	c := Circle{Radius: 5}
	r := Rectangle{Width: 4, Height: 6}
	// t := Triangle{Base: 3, Height: 4}

	type_switch(c)
	type_switch(r)
	// type_switch(t) // Triangle은 Shape 인터페이스 타입이 아니므로 호출할 수 없다
	type_switch(nil)
}





type Shape interface {
	Area() float64
}

type Circle struct {
	Radius float64
}

type Rectangle struct {
	Width float64
	Height float64
}

type Triangle struct {
	Base float64
	Height float64
}

func (c Circle) Area() float64 {
	return 3.14 * c.Radius * c.Radius
}

func (r Rectangle) Area() float64 {
	return r.Width * r.Height
}