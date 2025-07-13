package main

import "fmt"

// Number 제약 조건 인터페이스는 숫자 타입을 허용한다
// 컴파일러에게 제약조건을 알려주기 위해 사용된다
type Number interface {
    int | int8 | int16 | int32 | int64 |
        uint | uint8 | uint16 | uint32 | uint64 | uintptr |
        float32 | float64
}

// Add 함수는 Number 제약 조건을 만족하는 T 타입의 값을 더한다
func Add[T Number](a, b T) T {
    return a + b
}

type MyInt int

func AddInt[T int](x, y T) T {
	return x + y
}

func AddInt2[T ~int](x, y T) T {
	return x + y
}

func main() {
	fmt.Println(Add(10, 20))
	fmt.Println(Add(1.1, 2.2))

	var a MyInt = 1
	var b MyInt = 2
	
	// 컴파일 오류
	// MyInt는 int를 기반으로 하지만, Go는 서로 다른 타입으로 취급하기 때문에 AddInt[T int]의 타입 제약조건에 위배된다
	// AddInt(a, b)

	// ~ 연산자는 기반 타입이 같은 모든 타입을 허용한다
	// 따라서 MyInt는 [T ~int] 타입 제약조건을 충족한다
	fmt.Println(AddInt2(a, b))

}
