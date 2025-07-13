package main

import "fmt"

type counter struct {
	value int
}

// 포인터 리시버 메서드
func (c *counter) increment() {
	c.value++
}

// 값 리시버 메서드
func (c counter) getValue() int {
	return c.value
}


func main() {

	// 포인터 변수
	cnt := 	&counter{value: 0} 

	// 메서드 값 생성 (cnt 주소가 cntIncrement 클로저에 캡처된다)
	cntIncrement := cnt.increment 

	cntIncrement()
	cntIncrement()
	cntIncrement()

	// 원본 데이터가 변경되었다
	fmt.Println(cnt.getValue())

	// cnt의 포인터를 변경한다
	cnt = &counter{value: 100}

	cntIncrement()
	cntIncrement()
	cntIncrement()

	// cnt는 새로운 포인터를 가리키므로 변경에 영향을 받지 않는다
	fmt.Println(cnt.getValue())


	// 값 변수
	valCnt := counter{value: 0}

	// 메서드 값 생성 (valCnt의 값이 복사된 후 valCntIncrment 클로저에 캡처된다)
	valGetValue := valCnt.getValue

	// 원본 값을 변경한다
	valCnt.increment()
	valCnt.increment()
	valCnt.increment()

	// 메서드 값에 캡처된 복사본은 영향을 받지 않는다
	fmt.Println(valGetValue())
}
