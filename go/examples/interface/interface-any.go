package main

import "fmt"

func main() {

	var i interface{}

	// int 타입 값 할당
	i = 10
	fmt.Printf("%v, %T\n", i, i)

	// string 타입 값 할당
	i = "hello go"
	fmt.Printf("%v, %T\n", i, i)

	type Book struct {
		title string
		price int
	}

	b := Book{title: "golang", price: 10_000}
	i = b
	fmt.Printf("%v, %T\n", i, i)
	// `interface{}` 타입의 변수로 구조체의 필드에 접근할 수 없다
	// _  = i.title
	// _ = i.price

	i = []int{1, 2, 3}
	fmt.Printf("%v, %T\n", i, i)

	// `interface{}` 타입의 변수로 슬라이스 인덱싱을 할 수 없다
	// _ = i[0]
	// _ = i[1]
	// _ = i[2]

	// 타입 어설션(런타임 타입 확인)을 통해 구체적인 타입을 통해 값에 접근할 수 있다
	if val, ok := i.([]int); ok {
		fmt.Println(val[0])
		fmt.Println(val[1])
		fmt.Println(val[2])
	}

}
