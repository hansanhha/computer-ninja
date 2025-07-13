package main

import "fmt"

func main() {

	// 변수만 선언한다 (메모리 할당 X)
	var a [16]byte
	var s []int
	var m map[string]int

	fmt.Println(a == a) // true
	fmt.Println(m == nil) // true
	fmt.Println(s == nil) // true

	// 컨테이너 리터럴과 nil 값은 같지 않다
	fmt.Println(nil == map[string]int{}) // false
	fmt.Println(nil == []int{}) // false

	// 컴파일 오류, 맵과 슬라이스는 비교할 수 없는 타입이다
	// _ = m == m
	// _ = m == map[string]int(nil)
	// _ s == s
	// _ s == []int(nil)

}
