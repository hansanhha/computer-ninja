package main

func main() {

	/*
		========
		산술 연산자
		========
	*/
	println(0b1100 & 0b1010)
	println(0b1100 | 0b1010)
	println(0b1100 ^ 0b1010)
	println(0b1100 &^ 0b1010)
	println(0b1100 << 3)
	println(0b1100 >> 3)

	a := 1

	a++ // == a = a + 1
	a-- // == a = a - 1

	// 아래 코드는 전부 컴파일 에러가 발생한다
	// _ = a++
	// _ = a--
	// ++a
	// --a

	/*
		=============
		문자열 연결 연산자
		=============
	*/

	println("Go" + "lang") // Golang
	var golang = "Go"
	golang += "lang"
	println(golang) // Golang
}