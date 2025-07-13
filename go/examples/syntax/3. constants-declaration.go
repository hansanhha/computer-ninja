package main

/*
	===========================
	untyped named constants 관련
	===========================
*/

// 두 개의 상수 정의
// 아스키문자가 아닌 글자를 식별자로 사용될 수 있다
const π = 3.1416
const Pi = π

// 그룹으로 여러 개의 상수 정의
const (
	// untyped named constants
	NO         = !Yes
	Yes        = true
	MaxDegrees = 360
	Unit       = "radian"
)



/*
	===========================
	typed named constants 관련
	===========================
*/
const X float32 = 3.14

const (
	A, B int64   = -3, 5
	Y    float32 = 2.718
)

/*
	============================
	named contstants 타입 변환 관련
	============================
*/

// 명시적인 타입 변환을 통해 Go 컴파일러에게 타입 추론 정보를 줄 수 있다
const X2 = float32(3.14)
const (
	A2, B2 = int64(-3), int64(5)
	Y2 	   = float32(2.718)
)

/*
	========================
	유효하지 않은 typed 상수 정의
	========================
*/

// const a uint8 = 256   			   // uint8 오버플로우 (256)
// const b = uint8(255) + unit8(1)	   // uint8 오버플로우 (256)
// const c = int8(-128) / int8(1)	   // int8 오버플로우 (128)
// const MaxUint_a = uint(^0)		   // uint 오버플로우 (-1)
// const MaxUint_b uint = ^0		   // uint 오버플로우 (-1)
// const MaxUint uint = (1 << 64) - 1  // 32비트 OS에서 유효하지 않은 정의

/*
	==================
	int, uint 최대값 정의
	==================
*/

const maxUnit = ^uint(0)
const maxInt = int(^uint(0) >> 1)
const Is64bitOS = ^uint(0) >> 63 != 0
const Is32bitOS = ^uint(0) >> 32 == 0


/*
	================
	상수값 자동 할당 관련
	================
*/

// 제일 위의 상수 사양에 정의된 식별자 개수만큼 하위 상수 사양의 식별자 개수가 정해진다
const (
	X3 float32 = 3.14
	Y3          
	Z3          

	A3, B3 = "Go", "language"
	C3, _
)

// Go 컴파일러가 자동으로 값을 할당한다
const (
	X4 float32 = 3.14
	Y4 float32 = 3.14
	Z4 float32 = 3.14

	A4, B4 = "Go", "language"
	C4, _ = "Go", "language"
)


func main() {
	// untyped, typed named constants
	// 한 줄에 여러 개의 상수를 정의할 수 있다
	const TwoPi, HalfPi, Unit2 = π * 2, π * 0.5, "degree"
	const NAME string = "hansanhha"
}
