package main

import "fmt"

func main() {

	// 타입 int에 대한 메모리 공간 할당 및 주소 반환
	// var p *int
	p := new(int) 
	println("p 참조값:", p)    // 포인터 p가 가리키는 메모리 주소 값 출력
	println("p 역참조값:", *p)   // int 기본 값 0 출력

	t := 1      // 타입 int에 대한 값 t
	p1 := &t     // 변수 t에 대한 메모리 주소 할당
	println("p1 참조값:", p1)  // 변수 t의 메모리 주소 출력
	println("p1 역참조값:",*p1) // 변수 t의 값 1 출력(역참조)


	x := *p				// p의 역참조값 복사
	xp1, xp2 := &x, &x  // x 주소값 할당
	println("x:", x)
	println("xp1 == xp2:", xp1==xp2)
	println("p == xp1:", p==xp1)

	xp3 := &*p  // &*p0 == &(*p0) == p0
	println("p == xp3:", p == xp3)

	*p = 100
	println("*p, *xp3:", *p, *xp3)
	println("x, *xp1, *xp2: ", x, *xp1, *xp2)

	// 역참조한 값의 타입
	fmt.Printf("*p: %T, x: %T \n", *p, x)
	// 포인터 타입
	fmt.Printf("p: %T, xp1: %T \n", p, xp1)
}