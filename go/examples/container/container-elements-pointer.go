package main

import "fmt"

func main() {

	/*
		=================
		컨테이너 값 포인터 사용
		=================
	*/

	a := [5]int{1,2,3,4}
	s := make([]bool, 2)

	pa2, ps1 := &a[2], &s[1]
	fmt.Println(*pa2, *ps1) // 3, false

	a[2], s[1] = 100, true
	fmt.Println(*pa2, *ps1) // 100, true

	ps0 := &[]string{"Go", "C"}[0]
	fmt.Println(*ps0) // Go

	m := map[int]bool{1: true}
	_ = m

	// 배열은 맵, 슬라이스와 달리 직접 값 부분(Direct Part)에 데이터를 할당하므로 포인터 syntatic sugar를 사용할 수 없다
	// _ = &[3]int{2,3,5}[0]

	// 할당되지 않은 엔트리를 포인터로 사용할 수 없다
	// _ = &map[int]bool{1: true}[1]

	// 맵의 값을 포인터로 사용할 수 없다
	// _ = &m[1]

	/*
		=============================================
		맵의 값 타입이 구조체나 배열인 경우, 부분 수정이 불가능하다
		=============================================
	*/


	type Book struct {price int}
	mb := map[string]Book{}
	mb["Go"] = Book{price: 100}
	fmt.Println(mb) // map[Go:{100}]

	ma := map[int][5]int{}
	ma[1] = [5]int{1: 789}
	fmt.Println(ma) // map[1:[0 789 0 0 0]]

	// 컴파일 오류
	// 맵 엔트리의 직접 값 부분(Direct Part)은 부분 수정을 할 수 없다
	// 따라서 구조체나 배열의 특정 일부 값을 수정할 수 없다
	// mb["Go"].price = 200
	// ma[1][1] = 123

	// 대신 전체 수정(덮어쓰기)는 허용한다
	mb["Go"] = Book{price: 200}
	ma[1] = [5]int{1: 123}
	fmt.Println(mb) // map[Go:{200}]
	fmt.Println(ma) // map[1:[0 123 0 0 0]]

	// 덮어쓰기 메커니즘을 이용해서 엔트리를 임시 변수에 옮기고 
	// 값을 수정한 뒤 다시 덮어쓰면 일부분만 수정하는 것과 같아진다
	tmp := mb["Go"]
	tmp.price = 300
	mb["Go"] = tmp
	fmt.Println(mb) // map[Go:{300}]
}
