package main

import "fmt"

type MyInt int64
type Ta *int64
type Tb *MyInt

func main() {

	var x int64 = 100
	var y MyInt = 200

	// 포인터 p0의 값은 Ta 포인터 타입과 기반 타입(*int64)이 동일하여 암시적 형변환이 가능하다
	var p0 *int64 = &x
	var p1 Ta = p0

	// 포인터 p2의 값은 p3 포인터 타입과 기반 타입(*MyInt)이 동일하여 암시적 형변환이 가능하다
	var p2 *MyInt = &y
	var p3 Tb = p2

	// 포인터 p2의 값(*MyInt)은 p4 포인터 타입(*Int64)과 타입이 다르기 때문에 암시적 형변환이 불가능하다 (컴파일 오류)
	// var p4 *int64 = p2

	// 대신 명시적 형변환을 통해 *MyInt 포인터를 *int64 포인터로 변환할 수 있다 (두 포인터가 동일한 기반 타입인 int64를 가리킨다는 것을 Go 컴파일러에게 알려준다)
	var p4 *int64 = (*int64)(p2)

	// p5(Tb)와 p1(Ta)은 타입이 달라 암시적/명시적 형변환이 불가능하다
	// var p5 Tb = p1
	// var p5 Tb = (Tb) (p1)

	// 대신 아래와 같이 세 단계의 명시적 형변환을 하면 가능하다
	var p5 Tb = Tb((*MyInt)((*int64)(p1)))

	fmt.Printf("p0: %T \n", p0)
	fmt.Printf("p1: %T \n", p1)
	fmt.Printf("p2: %T \n", p2)
	fmt.Printf("p3: %T \n", p3)
	fmt.Printf("p4: %T \n", p4)
	fmt.Printf("p5: %T \n", p5)
}