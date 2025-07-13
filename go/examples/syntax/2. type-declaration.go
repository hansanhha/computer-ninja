package main

/*
	====================
	명명된 타입(Named Type)
	====================
*/

type status bool     // 기본 타입의 별칭
type MyString string // 기본 타입의 별칭

type Point struct {	 // 구조체 타입
	x int
	y int
}

type PointQueue []Point // 슬라이스 타입
type WordDictionary map[string]string // 맵 타입
type Greeter func(name string) string // 함수 타입

func main() {

	/*
		=====================
		익명 타입(Unnamed Type)
		=====================
	*/

	point := struct {
		x int
		y int
	}{x: 10, y: 20}

	// point와 동일한 구조의 또다른 익명 타입
	point2 := struct {
		x int
		y int
	}{x: 30, y: 40}

	// Go에서는 동일한 구조의 익명 타입끼리 호환된다 (명명된 타입은 구조가 같아도 안된다)
	point = point2

	add := func(a, b int) int {
		return a + b
	}

	result := add(10, 20)

	// 배열 타입, 슬라이스 타입 (타입 리터럴 표현)
	var numbers []int 	// []int는 슬라이스 타입의 익명 타입이다
	var matrix [3][3]float64 // [3][3]float64는 배열 타입의 익명 타입이다

	_ = point
	_ = result
	_ = numbers
	_ = matrix

}