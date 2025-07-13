package main

import "fmt"

func main() {
	
	type Book struct {
        pages int
    }

	// book을 통해 구조체의 주소에 접근할 수 있다
    var book = Book{pages: 100}
	println("book pages:", book.pages)
    b := &book.pages // . 연산자는 & 연산자보다 우선순위가 높다
    *b = 200
	println("book pages:", book.pages)

    // 복합 리터럴은 주소 지정할 수 없으며 수정될 수도 없다
    // Book{}.pages = 100
    // b = &(Book{}.pages)

    // syntatic sugar를 사용하면 복합 리터럴을 주소 지정할 수 있다
    // 아래는 tmp := Book{100}; book2 := &tmp 와 동일하다
    book2 := &Book{100}
    println("book2 pages:", book2.pages)

    book3 := &Book{100}
    // new 함수를 통해 구조체를 생성할 수도 있다
    book4 := new(Book)

    book4.pages = book3.pages
    
    // 아래 코드는 위 코드와 동일하다
    // 값을 받는 변수가 포인터라면 암묵적으로 역참조가 발생한다
    // (*book4).pages = (*book3).pages
    fmt.Println("book3 %T:", book3)
    fmt.Println("book4 %T:", book4)

    book3.pages = 200
    fmt.Println("book3.pages:", book3.pages)
    fmt.Println("book4.pages:", book4.pages)
}
