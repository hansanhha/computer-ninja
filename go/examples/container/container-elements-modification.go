package main

import "fmt"

func main() {

	/*
		====================
		컨테이너 요소 접근 및 수정
		====================
	*/

	a := [3]int{-1, 0, 1}
	s := []bool{true, false}
	m := map[string]int{"abc": 123, "xyz": 789}
	fmt.Println(a[2], s[1], m["abc"])
	
	a[2], s[1], m["abc"] = 999, true, 567
	fmt.Println(a[2], s[1], m["abc"])

	n, present := m["hello"]
	fmt.Println(n, present, m["hello"])

	n, present = m["abc"]
	fmt.Println(n, present, m["abc"])

	// 컴파일 오류
	// 배열 길이보다 더 큰 인덱스를 사용할 수 없다 (상수값)
	// 인덱스에 음수를 사용할 수 없다
	// _ = a[3]
	// _ = s[-1]
	
	// 런타임 오류
	// 변수를 사용하여 인덱싱했을 때 인덱스 범위를 벗어나면 런타임 오류가 발생한다
	// 존재하지 않는 맵의 키에 값을 할당하려하면 런타임 오류가 발생한다
	// _ = a[n]
	// _ = s[n]
	// m["hello"] = 555



	/*
		====================
		컨테이너 요소 추가 및 삭제
		====================
	*/
	
	// 맵 엔트리 추가/삭제/수정
	fmt.Printf("\n\n맵 엔트리 추가 삭제\n")
	z := map[string]int{"Go": 2007}
	z["C"] = 1972     // 삽입
	z["Java"] = 1995  // 삽입
	fmt.Println(z)    // map[C:1972 Go:2007 Java:1995]

	z["Go"] = 2009    // 수정
	delete(z, "Java") // 삭제
	fmt.Println(z)	  // map[C:1972 Go:2009]

	// 슬라이스 요소 추가/삭제
	fmt.Printf("\n\n슬라이스 요소 추가 삭제\n")
	y0 := []int{2,3,4}
	fmt.Println(y0, cap(y0)) // [2 3 4] 3

	y1 := append(y0, 5)      // append(y0, []int{5}...)와 동일
	fmt.Println(y0, cap(y0)) // [2 3 4] 3
	fmt.Println(y1, cap(y1)) // [2 3 4 5] 6

	y2 := append(y1, 6,7)    // append(y0, []int{6, 7}...)와 동일
	fmt.Println(y2, cap(y2)) // [2 3 4 5 6 7] 6

	y3 := append(y0)		 // y3 := y0
	fmt.Println(y3, cap(y3)) // [2 3 4] 3

	y4 := append(y0, y0...)  // append(y0, y0[0], y0[1], y0[2])와 동일
	fmt.Println(y4, cap(y4)) // [2 3 4 2 3 4] 6

	y0[0], y1[0] = 100, 200
	fmt.Println(y2[0], y3[0], y4[0]) // 200 100 2


	/*
		===============
		컨테이너 길이와 용량
		===============
	*/

	fmt.Printf("\n\n컨테이너 길이와 용량\n")
	var q [5]int
	fmt.Println(len(q), cap(q)) // 5 5 

	var w []int
	fmt.Println(len(w), cap(w)) // 0 0

	w, w2 := []int{2, 3, 5}, []bool{}
	fmt.Println(len(w), cap(w))   // 3 3
	fmt.Println(len(w2), cap(w2)) // 0 0

	var e map[int]bool
	fmt.Println(len(e)) // 0
	e, e2 := map[int]bool{1: true, 0: false}, map[int]int{}
	fmt.Println(len(e)) // 2
	fmt.Println(len(e2)) // 0

}
