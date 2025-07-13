package main

import "fmt"

func deferred_func_LIFO() {
	fmt.Println("간단한 지연 함수 실행") // 즉시 실행
	defer fmt.Println("세 번째") // 가장 먼저 스택에 추가된다
	defer fmt.Println("두 번째") // 두 번째로 추가된다
	defer fmt.Println("첫 번째") // 마지막으로 추가된다

	// defer문 호출 순서: 세 번째 -> 두 번째 -> 첫 번째
	// defer문 실행 순서: 첫 번째 -> 두 번째 -> 세 번째
}

func defer_can_modify_named_return_value(n int) (r int) {

	// 지연 함수는 명명된 반환 값을 수정할 수 있다
	defer func() {
		r += 5 // 반환 값 r 수정
	}()

	return n + n // return r = n + n
}

func deffered_argument_evaluation(x int) {
	defer fmt.Println(x) // x == 0, defer문 호출 시 전달된 x 값 고정

	x++ // x == 1, 위의 defer 함수에 영향을 주지 못한다
}

func deffered_anonymous() {
	/*
	defer 인자 즉시 평가: defer 문이 선언되는 그 시점에 인자가 평가되어 고정된다 (함수 호출이 지연되지, 함수 인자 평가가 지연되지 않는다)

	i = 0일 때 `defer fmt.Println("a:", 0 + 0)`이 예약된다
	i = 1일 때 `defer fmt.Println("a:", 1 + 0)`이 예약된다
	i = 2일 때 `defer fmt.Println("a:", 2 + 0)`이 예약된다

	for문 밖에서 x = 10으로 변경해도 이미 defer 문에서 평가되어 고정된 인자 값에는 아무런 영향을 주지 못한다

	결과값
	a: 2
	a: 1
	a: 0
	*/
	func() {
		var x = 0
		for i := 0; i < 3; i++ {
			defer fmt.Println("a:", i +x)
		}
		x = 10 // defer 호출에 영향을 주지 않는 변경
	}()


	fmt.Println()

	/*
	defer 인자 즉시 평가: defer 뒤에 오는 함수 호출의 인자가 즉시 평가된다. 여기서는 `func() { fmt.Println("b:", i + x)}` 라는 익명 함수 리터럴 자체가 인자 없이 호출되고, 그 결과가 defer 호출의 인자로 전달된다 (함수 객체 자체)

	defer 되는 것은 `fmt.Println` 호출이 아니라 `defer func() {...}()` 이라는 전체 익명 함수 호출이다

	이 익명 함수는 i와 x 외부 스코프 변수들을 캡처하는 클로저가 된다
	
	클로저는 변수의 현재 값을 캡처하는 것이 아니라 해당 변수 자체(메모리 주소)를 캡처한다

	따라서 클로저가 나중에 실행될 때 i, x의 마지막 값을 참조한다

	i = 0일 때 `defer fmt.Println("a:", 0 + x)`이 예약된다
	i = 1일 때 `defer fmt.Println("a:", 1 + x)`이 예약된다
	i = 2일 때 `defer fmt.Println("a:", 2 + x)`이 예약된다

	for문 밖에서 x = 10으로 변경하면 클로저가 캡처된 x 변수에도 직접 반영된다

	클로저는 원래 외부의 마지막 값만 참조하지만 Go 1.22 버전부터 for 문의 이터레이션 변수가 독립적인 인스턴스로 취급되는 것으로 변경되어 i의 값은 즉시 반영된다
	*/
	func ()  {
		var x = 0
		for i := 0; i < 3; i++ {
			defer func() {
				fmt.Println("b:", i + x)
			}()
		}
		x = 10
	}()
	/*
		결과값
		b: 12
		b: 11
		b: 10
	*/
}

func main() {
	deferred_func_LIFO()	

	fmt.Println(defer_can_modify_named_return_value(5)) // 15

	deffered_argument_evaluation(1)

	deffered_anonymous()
}
