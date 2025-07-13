package main

// 기본 타입 float64로 암시적 형변환 발생
const a = 1.23

// 컴파일러는 타입을 float64로 추론한다
var b = a

// 상수 1.23을 정수로 자를 수 없다
// var x = int32(a)

// b의 타입과 y의 타입이 달라 에러가 발생한다
// var y int32 = b

// 명시적 형변환을 통해 값을 할당한다 (.23은 누락됨)
var z int32 = int32(b)


const k int16 = 255

// 컴파일러는 타입을 int16으로 추론한다
var n2 = k

// 상수 형변환은 오버플로우를 허용하지 않는다
// var f = uint8(k + 1)

// k와 g의 타입이 달라 에러가 발생한다
// var g uint8 = n2 + 1

// 명시적 형변환을 통해 값을 할당한다
// h == 0 (변수 형변환은 오버플로우를 허용한다)
var h = uint8(n2 + 1)

func main() {

	type MyInt int
	type MyInt2 int

	var mi MyInt = 1
	var mi2 MyInt2 = 1

	// 컴파일 오류, Go는 기반 타입이 동일해도 서로 다른 네임드 타입의 암시적 형변환을 허락하지 않는다
	// mi = mi2

	// 동일한 기반 타입인 경우 명시적인 형변환을 해줘야 한다
	mi = MyInt(mi2)

	_ = mi
	_ = mi2
	
}