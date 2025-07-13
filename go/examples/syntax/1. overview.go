// 소스 파일의 패키지 명시
package main

// 표준 패키지 import
import "math/rand"
import "fmt"

// 상수 정의
const MaxRnd = 16


// (n int): 매개변수
// (int, int): 반환 타입 (go는 여러 개의 값을 반환할 수 있다)
func StatRandomNumbers(n int) (int, int) {

	// 표준 변수 정의 (0으로 초기화됨)
	var a, b int

	// for-loop문
	// i의 단축 변수 정의 (:= 를 사용하면 컴파일러의 타입 추론을 이용할 수 있다)
	for i := 0; i < n; i++ {
		if rand.Intn(MaxRnd) < MaxRnd/2 {
			a = a + 1 // a++과 동일
		} else {
			b++		  // b = b + 1과 동일
		}
	}

	// 두 개의 결과값 반환
	return a, b
}

// 메인 함수가 프로그램의 진입점이 된다
func main() {
	// 표준 변수 정의
	var num = 100

	// 함수 호출, 단축 변수 정의
	x, y := StatRandomNumbers(num)

	// 내장 함수 호출
	print("Result: ", x, " + ", y, " = ", num, " ? ")
	println(x+y == num)

	// 내장 함수 대신, fmt 표준 패키지의 함수를 사용하는 것이 좋다
	fmt.Print("Result: ", x, " + ", y, " = ", num, " ? ")
	fmt.Println(x+y == num)
}