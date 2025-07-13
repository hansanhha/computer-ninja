package main

import "fmt"

// 타입 매개변수: x, y
// 타입 제약조건: 각각의 매개변수에 제약조건을 지정한다
type pair[x string, y int] struct {
	first x	   // 구조체 필드의 타입을 타입 매개변수로 지정한다
	second y
}

// 제네릭 타입 pair 구조체를 생성하고 반환하는 함수
// 타입 매개변수: T1, T2
// 타입 제약조건: pair 타입 제약조건과 동일하다
// 반환 타입: pair[T1, T2]
func getPair[T1 string, T2 int](val1 T1, val2 T2) pair[T1, T2] {
	return pair[T1, T2] {
		first: val1,
		second: val2,
	}
}

func main() {

	// 구조체 생성할 때는 타입 추론이 잘 되지 않아 타입 인수를 명시적으로 전달해야 한다
	p1 := pair[string, int]{first: "hello", second: 100}
	p2 := pair[string, int]{first: "golang", second: 200}

	// 제네릭 구조체를 반환하는 제네릭 함수를 사용하면 타입 추론이 가능해진다
	// 명시적인 타입 인수 생략 가능
	p3 := getPair("generics", 300)


	fmt.Println(p1)
	fmt.Println(p2)
	fmt.Println(p3)

}
