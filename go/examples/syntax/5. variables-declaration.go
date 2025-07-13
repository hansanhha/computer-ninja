package main

import "fmt"

/*
	=========================================
	package-level variables (global variables)
	=========================================
*/

// 타입을 지정하면 오버플로우 값 할당 시 컴파일 에러가 발생하고, 지정하지 않으면 에러가 발생하지 않는다
// const nOverflow int = 1 << 64
const nOverflow2  = 1 << 64

// 표준 변수 선언 양식
// 한 줄에 여러 개의 변수를 정의하는 경우 타입이 모두 동일해야 한다
var name, github string = "hansanhha", "https://github.com/hansanhha"
var compiled, dynamic bool = true, false
var year int = 2025

// 타입 지정을 생략한 방식
// 컴파일러는 초기값의 타입으로 변수의 타입을 추론한다 (암시적 형변환)
// 표준 변수 선언 양식과 달리 한 줄에 서로 다른 타입을 선언할 수 있다
var name2, dynamic2 = "hansanhha", false
var compiled2, year2 = true, 2025
var github2 = "https://github.com/hansanhha"


// 초기값을 제외한 방식
// 변수는 지정된 타입의 기본값으로 초기화된다
var name3, github3 string       // blank string
var interpreted, dynamic3 bool  // false
var n int				        // 0

// 그룹화하여 선언한 방식
// 변수를 정의한 각 라인을 변수 사양(variable specification)이라고 한다
var (
	name4, year4, compiled4 = "hansanhha", 2025, true
	x, y = 10, 20
	interpreted2, dynamic4 bool
)


/*
	==========================================
	Local Variables (Function-level Variables)
	==========================================
*/


// 단축 변수 선언 양식 - 로컬 변수(함수 본문 변수) 선언에만 사용할 수 있다
// Go 컴파일러는 로컬 변수를 최소 1번은 사용하도록 강제한다
func main() {

	// var 키워드와 타입 지정을 생략해야 한다
	// = 대신 := 으로 할당해야 한다
	// := 키워드는 지역 변수에서만 사용할 수 있다
	book := "golang"
	fmt.Println(book)

	// := 연산자로 변수 초기화를 중복할 수 없다
	// book := "test"

	// 대신 새로운 변수와 함께 선언하면 가능하다
	book, greeting := "modified", "hello"

	println(book, greeting)

}