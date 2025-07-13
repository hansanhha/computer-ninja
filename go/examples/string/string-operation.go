package main

import (
	"fmt"
	"strings"
)

func main() {

	const world = "world"
	const hello = "hello"

	// 문자열 연결
	helloWorld := "hello" + " " + world
	helloWorld += "!"
	fmt.Println(helloWorld) // hello world!

	// 문자열 비교
	fmt.Println(hello == "hello") // true
	fmt.Println(hello > helloWorld) // false

	hello2 := helloWorld[:5] // 서브 스트링을 지원한다
	fmt.Println(hello2)
	fmt.Printf("%T \n", hello2[0]) // 문자열 인덱싱을 지원한다, uint8
	fmt.Println(hello2[0]) // 104

	// 문자열의 각 바이트는 주소 지정 불가능하며 불변이다
	// 인덱싱이나 포인터를 통해 특정 바이트를 수정할 수 없다
	// hello2[0] = 'H' 
	// fmt.Println(&[hello]0)

	// string 타입 자체엔 빌트인 함수를 제공하지 않는다
	// 대신 strings 표준 패키지를 이용하여 문자열을 조작할 수 있다
	fmt.Println(strings.HasPrefix(helloWorld, hello))
	
}
