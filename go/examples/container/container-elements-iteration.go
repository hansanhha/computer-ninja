package main

import "fmt"

func main() {

	fmt.Printf("map iteration\n")

	// 맵의 이터레이션 순서는 보장되지 않는다
	// 순회하는 도중 아직 순회하지 못한 엔트리가 삭제된 경우 순회하지 않는다
	// 순회하는 도중 새로운 엔트리가 추가된 경우 순회하거나 하지 않을 수 있다
	m := map[string]int{"C": 1972, "C++": 1983, "Go": 2009}
	for lang, year := range m {
		fmt.Printf("%v: %v \n", lang, year)
	}

	fmt.Printf("\n\narray iteration\n")
	a := [...]int{1, 2, 3, 4, 5}
	for i, prime := range a {
		fmt.Printf("%v: %v \n", i, prime)
		prime++ // for range 문 내에서 이터레이션 변수를 수정한다
	}

	// for range 문에서 배열의 요소를 수정해도 원본 배열에 반영되지 않는다
	fmt.Println(a)

	fmt.Printf("\n\nslice iteration\n")
	s := []string{"go", "defer", "goto", "var"}
	for i, keyword := range s {
		fmt.Printf("%v: %v \n", i, keyword)
		keyword = string(i) // for range 문 내에서 이터레이션 변수를 수정한다
	}

	// for range 문에서 이터레이션 변수를 수정해도
	// 원본 슬라이스 요소에 반영되지 않는다
	fmt.Println(s)
}
