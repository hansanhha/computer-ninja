package main

import "fmt"

// 단순 계산 named type 함수
type calculator func(a, b int) int

// 메시지 출력 named type 함수 
type messagePrinter func(msg string)

// 비동기 작업을 완료했을 때 호출될 콜백 함수를 위한 named type 함수
type asyncCallback func(result string, err error)

// 로직을 처리하고 두 개의 값을 반환하는 named type  함수
type processor func(data []byte) (processedData string, checksum int, err error)


func main() {

	// calculator 타입의 변수 add 선언 및 익명 함수(함수 리터럴) 할당
	var add calculator = func(a, b int) int {
		return a + b
	}

	// calculator 타입의 변수 subtract 선언 및 익명 함수 할당
	// 위 코드처럼 함수 이름을 변수명 옆에 붙이거나 함수 리터럴을 함수 이름으로 감싸서 함수를 만들 수 있다
	subtract := calculator(func(a, b int) int {
		return a - b
	})

	fmt.Println(add(5, 3))		// 8
	fmt.Println(subtract(5, 3)) // 2

	var printer messagePrinter = func(name string) {
		fmt.Println("hello", name)
	}

	printer("hansanhha")  // hello hansanhha

}

