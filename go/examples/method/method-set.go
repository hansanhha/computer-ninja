package main

import "fmt"

// 구조체(타입) 정의
// MyStruct 타입의 메서드 집합: {getValue, setValue}
type MyStruct struct {
	value int
}


// 값 리시버 메서드
func (s MyStruct) getValue() int {
	return s.value
}

// 포인터 리시버 메서드
func (s *MyStruct) setValue(value int) {
	s.value = value
}


func main() {

	// 값 타입 변수
	// s1 (MyStruct 타입)은 getValue(), setValue() 모두 호출할 수 있다
	var s1 MyStruct 

	// getValue()는 값 리시버 메서드이므로 변환하지 않고 호출한다
	fmt.Println(s1.getValue()) 

	// setValue()는 포인터 리시버 메서드지만 컴파일러가 (&s1).setValue(10)으로 변환 후 호출한다
	s1.setValue(10)			   
	fmt.Println(s1.getValue()) 


	// 포인터 타입 변수
	// s2 (MyStruct 타입)은 getValue(), setValue() 모두 호출할 수 있다
	s2 := &MyStruct{} 

	// getValue()는 값 리시버 메서드지만 컴파일러가 (*s2).getValue()로 변환 후 호출한다 
	fmt.Println(s2.getValue())

	// setValue()는 포인터 리시버 메서드이므로 변환하지 않고 호출한다
	s2.setValue(20)
	fmt.Println(s2.getValue())
}