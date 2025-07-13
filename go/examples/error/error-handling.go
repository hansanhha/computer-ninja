package main

import (
	"errors"
	"fmt"
)

func add(a, b int) (result int, err error) {

	if a < 0 || b < 0 {
		return 0, errors.New("음수의 값은 더할 수 없다")
	}

	return a + b, nil
}


func main() {

	result, err := add(10, 20)

	// 함수가 에러를 반환하는지 확인한다 (에러 처리)
	if (err != nil) {
		fmt.Println("에러 발생")
		return
	}

	fmt.Println(result)

	result, err = add(-10, 20)

	// 함수가 에러를 반환하는지 확인한다 (에러 처리)
	if (err != nil) {
		fmt.Println("에러 발생")
		return
	}

	fmt.Println(result)
}