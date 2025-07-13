package main

import "fmt"

type writer interface {
	write() string
}

type filmWriter struct {
	title string
}

// writer 인터페이스의 메서드를 포인터 리시버 메서드로 구현한다
func (f *filmWriter) write() string {
	return f.title
}

func main() {

	/*
		아래의 코드는 모두 컴파일 오류가 발생한다
		
		컴파일러는 구체 타입의 메서드 시그니처와 리시버 타입을 정확히 일치시켜야만 인터페이스를 만족한다고 판단한다

		fileWriter의 write() 메서드는 값 타입이 아닌 포인터 리시버 메서드이므로 포인터 타입(*filmWriter)만이 writer 인터페이스의 writer()를 구현한 것으로 간주한다

		var w writer

		writer = filmWriter{title: "the shawshank redemption"}
	
		fw := filmWriter{title: "the shawshank redemption"}
		w = fw	
	*/

	var w writer

	// 값 타입으로 선언하고 주소를 넘겨주면 컴파일 오류를 해결할 수 있다
	fw := filmWriter{title: "the shawshank redemption"}
	w = &fw
	fmt.Println(w.write())

	w = &filmWriter{title: "inception"}
	fmt.Println(w.write())
}
