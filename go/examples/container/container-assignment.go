package main

import "fmt"

func main() {

	m0 := map[int]int{0:7, 1:8, 2:9}
	m1 := m0
	m1[0] = 2
	fmt.Println(m0, m1) // map[0:2 1:8 2:9] map[0:2 1:8 2:9]

	s0 := []int{7,8,9}
	s1 := s0
	s1[0] = 2
	fmt.Println(s0, s1) // [2 8 9] [2 8 9]

	a0 := [...]int{7,8,9}
	a1 := a0
	a1[0] = 2
	fmt.Println(a0, a1)	// [7 8 9] [2 8 9]
}
