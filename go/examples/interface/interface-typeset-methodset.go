package main

import "fmt"

type Speaker interface {
    Speak() string  // Speaker 인터페이스의 메서드 셋: {Speack()}
}

type Animal struct {
    Name string
}

// 1. 값 리시버 메서드
func (a Animal) SayHello() string {
    return "hello " + a.Name
}

// 2. 포인터 리시버 메서드
func (a *Animal) SetName(newName string) {
    a.Name = newName
}

// 3. 인터페이스 Speaker의 메서드와 동일한 시그니처 (값 리시버)
func (a Animal) Speak() string {
    return a.Name + " speaks"
}

func main() {

    // Animal (값 타입)의 메서드 셋: {SayHello() string, Speak() string}
    var lion Animal = Animal{Name: "Lion"}

    fmt.Println(lion.SayHello())
    fmt.Println(lion.Speak())
	// lion의 메서드 셋에 포함되지 않지만 자동 변환 규칙에 의해 (&lion).SetName("New Lion")으로 호출된다
	// SetName은 인터페이스의 메서드가 아니므로 자동 변환 규칙이 적용된다
    lion.SetName("New Lion")

    // lion은 Speaker 인터페이스를 만족한다
    var s Speaker = lion
    fmt.Println(s.Speak())


    // Animal (포인터 타입)의 메서드 셋: {SayHello() string, SetName(string), Speak() string}
    var tiger *Animal = &Animal{Name: "Tiger"}

    fmt.Println(tiger.SayHello())
    fmt.Println(tiger.Speak())
    tiger.SetName("New Tiger")

	// 포인터 타입은 값 타입의 메서드셋도 포함하므로 인터페이스를 만족한다
    s = tiger
	fmt.Println(s.Speak())
}
