package main

import (
	"fmt"
	"io"
)

/*
	==========
	구조체 임베딩
	==========
*/

// person 구조체 정의
type person struct {
	name string
}

// person 메서드
func (p person) greet() string {
	return fmt.Sprintf("hello %s", p.name)
}

// employee 구조체는 person 구조체를 임베딩한다
type employee struct {
	person
	id string
	role string
}

// employee 구조체도 person과 동일한 이름의 메서드를 정의한다
// 바깥 구조체와 임베딩한 구조체의 메서드 이름이 동일하면 바깥 구조체의 메서드가 우선된다
// 섀도잉(shadowing) 또는 프로모션된 메서드 오버라이딩이라고 한다
// 자바의 오버라이딩처럼 다형적 호출(super.method())이 발생하지 않는다
func (e employee) greet() string {
	return fmt.Sprintf("hello %s (%s %s)", e.name, e.id, e.role)
}

func struct_embedding() {
	e := employee {
		person: person{
			name: "hansanhha",
		},
		id: "1324",
		role: "coder",
	}

	// person의 필드를 마치 Employee의 필드인 것처럼 사용할 수 있다
	fmt.Println(e.name)

	// 임베딩한 타입을 통해서 접근할 수도 있다
	fmt.Println(e.person.name)
	
	// employee의 greet() 메서드가 person의 greet() 메서드보다 우선이다 (섀도잉)
	fmt.Println(e.greet())

	// 명시적으로 접근하면 임베딩한 타입의 메서드를 사용할 수 있다
	fmt.Println(e.person.greet())
}

/*
	==============
	인터페이스 임베딩
	==============
*/

// ReadWriter 인터페이스는 io.Reader와 io.Writer 인터페이스를 임베딩한다
type ReadWriter interface {
	io.Reader // Read(p []byte) (n int, err error) 메서드 포함
	io.Writer // Write(p []byte) (n int, err error) 메서드 포함
	Close() error
}

// ReaderWriter 인터페이스를 만족하는 구현체
type MyFile struct {}

func (mf MyFile) Read(p []byte) (n int, err error) {
	fmt.Println("파일 열기")
	return len(p), nil
}

func (mf MyFile) Write(p []byte) (n int, err error) {
	fmt.Println("파일 쓰기")
	return len(p), nil
}

func (mf MyFile) Close() error {
	fmt.Println("파일 닫기")
	return nil
}

func interface_embedding() {

	// MyFile은 ReadWriter 인터페이스를 만족한다
	var rw ReadWriter = MyFile{}

	// ReaderWriter 메서드 호출
	rw.Read(make([]byte, 10))
	rw.Write([]byte("hello hansanhha"))
	rw.Close()

	// ReadWriter 타입은 io.Reader 타입으로도 사용할 수 있다
	var r io.Reader = rw
	r.Read(make([]byte, 5))
}



func main() {
	// struct_embedding()

	interface_embedding()
}
