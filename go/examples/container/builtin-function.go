package main

import "fmt"

func main() {

	/*
		======================
		make 함수(맵, 슬라이스 생성)
		======================
	*/

	fmt.Println(make(map[string]int)) // map[]

	m := make(map[string]int, 3)
	fmt.Println(m, len(m)) // map[] 0

	m["C"] = 1972
	m["Go"] = 2009
	fmt.Println(m, len(m)) // map[C:1972 Go:2009] 2

	s := make([]int, 3, 5)
	fmt.Println(s, len(s), cap(s)) // [0 0 0] 3 5

	s = make([]int, 2)
	fmt.Println(s, len(s), cap(s)) // [0 0] 2 2


	/*
		===========================
		make 함수(배열, 맵, 슬라이스 생성)
		============================
	*/

	x := *new(map[string]int) 	// x map[string]int
	fmt.Println(x == nil)	  	// true

	y := *new([]int)		  	// var y []int
	fmt.Println(y == nil)	  	// true

	z := *new([5]bool)		  	// var a [5]bool
	fmt.Println(z == [5]bool{}) // true

	/*
		========================
		copy 함수(배열, 슬라이스 복사)
		========================
	*/

	type Ta []int
	type Tb []int
	dest := Ta{1, 2, 3}
	src := Tb{4, 5, 6, 7, 8, 9}

	n := copy(dest, src)
	fmt.Println(n, dest) // 3 [4 5 6]

	n = copy(dest[1:], dest)
	fmt.Println(n, dest) // 2 [4 4 5]

	a_dest := [4]int{}

	n = copy(a_dest[:], src)
	fmt.Println(n, a_dest) // 4 [4 5 6 7]

	n = copy(a_dest[:], a_dest[2:])
	fmt.Println(n, a_dest) // 2 [6 7 6 7]


}
