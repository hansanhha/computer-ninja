package main

import "fmt"

func HalfAndnegative(n int) (int, int) {
	return n/2, -n
}

func AddSub(a, b int) (int, int) {
	return a+b, a-b
}

func Dummy(values ...int) {}


func main() {

	fmt.Println(AddSub(HalfAndnegative(6)))
	fmt.Println(AddSub(AddSub(AddSub(7, 5))))
	fmt.Println(AddSub(HalfAndnegative(6)))
	Dummy(HalfAndnegative(6))
	_, _ = AddSub(7, 5)

	// 다중 값을 반환하는 함수는 다른 할당값(source value)와 섞어서 값을 할당할 수 없다
	// _, _, _ = 6, AddSub(7, 5)

	// 다중 값을 반환하는 함수는 다른 인자와 함께 인자로 전달될 수 없다
	// Dummy(AddSub(7, 5), 9)
	// Dummy(AddSub(7, 5), HalfAndnegative(6))
}
