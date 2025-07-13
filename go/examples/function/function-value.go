package main

import "fmt"

func add(n int) int {
	return n + n
}

// f 타입: `func(int) int`
func apply(n int, f func(int) int) int {
	return f(n)  
}

func main() {

	fmt.Printf("%T\n", add) // func(int) int

	// add = nil // 선언된 함수는 불변이므로 런타임에 수정될 수 없다

	var f func(n int) int // 기본값: nil
	
	f = add // f와 add는 동일함 함수 객체를 참조한다
	g := apply // g와 apply는 동일한 함수 객체를 참조한다

	fmt.Printf("%T\n", f) // func(int) int
	fmt.Printf("%T\n", g) // func(int, func(int) int) int

	fmt.Println(f(9))         // 18
	fmt.Println(g(6, add))	  // 12
	fmt.Println(apply(6, f))  // 12
}