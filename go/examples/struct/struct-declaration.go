package main

import "fmt"

type Book struct {
	title   string
    pages   int
    price   int
}

func main() {
	
	// 구조체에 선언된 필드의 순서를 따르는 복합 리터럴
    goBook := Book{"golang", 100, 10_000}
    
    // 필드 이름과 매핑하여 복합 리터럴
    javaBook := Book{title: "java", pages: 200, price: 20_000}

    // 일부 필드만 이름과 매핑한 복합 리터럴 (생략한 필드는 기본값으로 대체됨)
    unknownBook := Book{title: "unknown"}

    // 모든 값을 생략한 복합 리터럴 (기본값으로 대체됨)
    emptyBook := Book{}

	fmt.Println(goBook)
	fmt.Println(javaBook)
	fmt.Println(unknownBook)
	fmt.Println(emptyBook)
}
