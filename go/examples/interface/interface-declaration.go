package main

import "fmt"

/*
	=======
	타입 정의
	=======
*/

// 인터페이스 정의, speak() 메서드를 가진 모든 타입은 Speaker를 만족한다
type speaker interface {
	speak() string
}

type person struct {
	name string
}

type dog struct {
	breed string
}

/*
	========
	메서드 정의
	========
*/

// person 타입의  speak 메서드
func (p person) speak() string {
	return "hello " + p.name
}

// dog 타입의  speak 메서드
func (d dog) speak() string {
	return "woof " + d.breed
}

// 다형성을 활용하는 함수
func say(s speaker) {
	fmt.Println(s.speak())
}

func main() {

	p := person{name: "hansanhha"}
	d := dog{breed: "golden retriever"}

	say(p)
	say(d)

	// 인터페이스 변수에 직접 할당할 수도 있다
	var speaker1 speaker = p
	var speaker2 speaker = d

	fmt.Println(speaker1.speak())
	fmt.Println(speaker2.speak())

	// Go에서 인터페이스 그 자체로 타입이 되므로, 인터페이스 타입의 변수는 해당 인터페이스를 만족하는 어떤 타입의 값이라도 담을 수 있다
	var s speaker

	s = person{name: "hansanhha"}  // person은 speaker를 만족하므로 할당 가능
	s = dog{breed: "poodle"} // dog도 spearker를 만족하므로 할당 가능

	fmt.Println(s.speak())
}
