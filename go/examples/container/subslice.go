package main

import "fmt"

func main() {


	a := [...]int{0, 1, 2, 3, 4, 5, 6}

	s0 := a[:]      // so := [0:7:7]
	s1 := s0[:]     // s1 := s0
	s2 := s1[1:3]   // s2 := a[1:3]
	s3 := s1[3:]	// s3 := s1[3:7]
	s4 := s0[3:5]   // s4 := s0[3:5:7]
	s5 := s4[:2:2]  // s5 := s0[3:5:5]
	s6 := append(s4, 7)
	s7 := append(s5, 8)
	s8 := append(s7, 9)
	s3[1] = 10

	fmt.Println(len(s0), cap(s0), s0) // 7 7 [0 1 2 3 10 7 6]
	fmt.Println(len(s1), cap(s1), s1) // 7 7 [0 1 2 3 10 7 6]
	fmt.Println(len(s2), cap(s2), s2) // 2 6 [1 2]
	fmt.Println(len(s3), cap(s3), s3) // 4 4 [3 10 7 6]
	fmt.Println(len(s4), cap(s4), s4) // 2 4 [3 10]
	fmt.Println(len(s5), cap(s5), s5) // 2 2 [3 10]
	fmt.Println(len(s6), cap(s6), s6) // 3 4 [3 10 7]
	fmt.Println(len(s7), cap(s7), s7) // 3 4 [3 4 8]
	fmt.Println(len(s8), cap(s8), s8) // 4 4 [3 4 8 9]
}
