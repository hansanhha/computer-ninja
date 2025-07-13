package main

import "fmt"

var a int

// 소스 코드 파일의 init 함수는 자동으로 호출된다 (다른 패키지에서 임포트했을 때에도)
// init 이름과 `func ()` 타입의 함수는 여러번 선언될 수 있다

func init() {
	fmt.Println("hello init function")	
}

func init() {
	fmt.Println("hello another init function")	
	a = 1
}


func main() {
	fmt.Println(a) // 1
}
