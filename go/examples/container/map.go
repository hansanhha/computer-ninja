package main

import "fmt"

func main() {

	a := map[string]int{"C": 1972, "Python": 1991, "Go": 2009}
	b := map[int]bool{}

	// 맵 요소의 값 타입: [6]string (배열)
	c := map[int16][6]string{}

	// 맵 요소의 값 타입: []string (슬라이스)
	d := map[bool][]string{}

	// 맵 요소의 키 타입: 구조체, 값 타입: *int8
	e := map[struct{x int}]*int8{}

	// 컴파일 오류
	// 맵 리터럴의 엔트리 키는 생략될 수 없으며 상수만 사용할 수 있다
	// var i = 1
	// f := map[string]int{i: 100}

	fmt.Println(a)
	fmt.Println(b)
	fmt.Println(c)
	fmt.Println(d)
	fmt.Println(e)
}