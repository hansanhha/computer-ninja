package main

import "fmt"

func main() {

	a := []int64{1,2,3,4,5,6,7,8,9}
	b := []map[int]bool{} // 슬라이스 요소 타입: map[int]bool

	// 슬라이스 요소 타입: *int
	// 포인터 타입은 nil 값으로 초기화된다
	c := []*int{} 

	// 배열과 슬라이스는 인덱스를 사용해서 초기화할 수 있다
	d := []string{2: "fallthrough", 1: "continue", 0: "break"}

	// nil 값으로 초기화
	e := []string(nil)

	// 중첩된 복합 리터럴을 포함하는 경우 아래와 같이 타입을 생략하여 선언할 수 있다
	f := []*[4]byte{
		&[4]byte{'P', 'N', 'G', ' '},
		&[4]byte{'G', 'I', 'F', ' '},
		&[4]byte{'J', 'P', 'E', 'G'},
	}

	f2 := []*[4]byte{
		{'P', 'N', 'G', ' '},
		{'G', 'I', 'F', ' '},
		{'J', 'P', 'E', 'G'},
	}

	fmt.Println(a)
	fmt.Println(b)
	fmt.Println(c)
	fmt.Println(d)
	fmt.Println(e)
	fmt.Println(f)
	fmt.Println(f2)


	/*
		==================
		슬라이스 -> 배열 형변환
		==================
	*/

	var x = []int{0, 1, 2, 3}
	var y = [4]int(x[:])  // 형 변환

	fmt.Println(x) // [0 1 2 3]
	fmt.Println(y) // [0 1 2 3]

	x[2] = 10

	fmt.Println(x) // [0 1 10 3]
	fmt.Println(y) // [0 1 2 3]

	// 형변환 시 배열 크기가 슬라이스 크기보다 큰 경우 런타임 오류가 발생한다
	// _ = [3]int(x[:2])

}
