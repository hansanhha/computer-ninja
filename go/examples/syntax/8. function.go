package main


/*
	===============
		함수 선언
	===============

	func <function_name> <(parameters)> <(return values) or return type> {
		method body
		return
	}
*/

// 각 파라미터와 반환 값은 이름과 타입으로 구성한다
// 파라미터와 반환 값은 로컬 변수와 동일하게 취급된다
// 반환 값의 이름은 모두 명시되거나 모두 생략해야 한다
func SqaureOfSumAndDiff(a int64, b int64) (s int64, d int64) {
	x, y := a + b, a - b

	// 반환값의 이름을 지정하면 := 키워드를 사용할 수 없다
	s = x * x
	d = y * y

	// 함수 정의 시 반환 값 이름을 이용하면 return 문에서 생략할 수 있다
	// return s, d와 동일하다
	return 
}

func SqaureOfSumAndDiff2(a int64, b int64) (int64, int64) {
	x, y := a + b, a - b

	// 반환값의 이름을 생략하면 = 키워드를 사용할 수 없다
	s := x * x
	d := y * y

	// 함수 정의 시 반환 값 이름을 생략하면 반환값 순서에 따라 명시해야 한다
	return s, d
}

// 파라미터나 반환값의 타입이 동일한 경우 한 번만 지정하여 공유할 수 있다
func SqaureOfSumAndDiff3(a, b int64) (s, d int64) {
	return (a+b) * (a+b), (a-b) * (a-b)

	// s = (a+b) * (a+b), d = (a-b) * (a-b)와 동일하다
}

// 이름없는 한 개의 값만 반환하는 경우 괄호를 생략할 수 있다
func multiply(a int64, b int64) int64 {
	return a * b
}

func multiply2(a int64, b int64) (x int64) {
	x = a * b
	return
}

// 반환 값이 없는 경우 아예 생략할 수 있다
// 파라미터 선언 부분은 파라미터가 없어도 생략할 수 없다
func greeting() {
	println("hello golang")
}


/*
	=========================
	함수 파라미터, 반환 값의 기본 값
	=========================
*/

// 함수 반환 값은 해당 타입의 기본 값으로 초기화된다
// 0, false 반환
func foo() (x int, y bool) {
	println(x, y)
	return
}

// 함수 파라미터의 기본 값을 지원하지 않는다
// func bar(x int = 1, y int = 1) {
// 	println(x, y)
// 	return
// }


func main() {

	println(SqaureOfSumAndDiff(10, 20))
	println(SqaureOfSumAndDiff2(10, 20))
	println(multiply(10, 10))
	println(multiply2(10, 10))

	foo()


	/*
		=========================
				익명 함수
		=========================
	*/

	// 익명 함수를 정의하고 바로 호출할 수 있다
	x, y := func() (int, int) {
		println("empty parameter function")
		return 3, 4
	}()


	// 지역변수 x,y를 사용할 수 있다
	func(a, b int) {
		println("a*a + b*b = ", a*a + b*b)
	}(x, y)


	// 파라미터 x는 바깥 지역 변수 x를 가린다
	// 인자 y는 익명 함수 파라미터 x로 전달되고, 내부에서 바깥의 지역 변수 y를 사용한다
	func(x int) {
		println("x*x + y*y =", x*x + y*y)
	}(y)

	// 클로저
	func() {
		println("x*x + y*y =", x*x + y*y)
	}()

}
