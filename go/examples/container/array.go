package main

import "fmt"

type Book struct {
	title string
	pages int
}

func main() {

	const Size = 32

	a := [2]string{"hello", "golang"}

	// 모든 요소가 int 타입의 기본 값으로 초기화된다
	// 상수값만 배열의 크기로 지정할 수 있다
	b := [Size]int{}

	// 중첩된 복합 리터럴을 포함하는 경우 아래와 같이 타입을 생략하여 선언할 수 있다
	c := [2]Book{
		{title: "gobook", pages: 10}, 
		{title: "example", pages: 20},
	}

	d := [16][]byte{} // 배열 요소의 타입: []byte

	// 배열과 슬라이스는 인덱스를 사용해서 초기화할 수 있다
	e := [4]bool{0: false, 1: true, 2: true, 3: false}

	// `...`: 초기화 시 길이를 컴파일러가 추론할 수 있게 한다
	f := [...]bool{false, true, false, false}

	fmt.Println(a)
	fmt.Println(b)
	fmt.Println(c)
	fmt.Println(d)
	fmt.Println(e)
	fmt.Println(f)
}
