package main

import "fmt"

// 타입 매개변수: T
// 타입 제약조건: [int | float64]
func sum[T int | float64](numbers []T) T {
	var total T // 변수의 타입을 타입 매개변수로 지정한다

	for _, num := range numbers {
		total += num	
	}

	return total
}

func main() {

	// 타입 인수: int (명시적 전달)
	intSum := sum[int]([]int{1, 2, 3, 4, 5})

	// 타입 인수: float (생략)
	// 컴파일러가 타입 인수를 추론할 수 있는 경우 생략할 수 있다
	floatSum := sum([]float64{1.1, 2.2, 3.3})

	fmt.Println(intSum)
	fmt.Println(floatSum)

	// 컴파일 오류
	// 타입 인수 string은 int | float64 제약 조건을 만족하지 않는다
	// stringSum := sum[string]([]string{"a", "b"})
}