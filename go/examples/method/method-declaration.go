package main

import "fmt"

/*
	================
	값 리시버 메서드 사용
	================
*/

// 구조체(타입) 정의
type circle struct {
	radius float64
}

// circle 타입에 연결된 메서드 선언
// 값 리시버 (c circle)
func (c circle) area() float64 {
	return 3.14 * c.radius * c.radius
}

func (c circle) describe() {
	fmt.Printf("원의 반지름: %.2f, 면적: %.2f\n", c.radius, c.area())
}

/*
    위 메서드에 대해 컴파일러는 아래와 같이 함수를 암시적으로 선언한다

    func circle.area(c circle) float64 {
	    return 3.14 * c.radius * c.radius
    }

    func circle.describe(c circle) {
	    fmt.Printf("원의 반지름: %.2f, 면적: %.2f\n", c.radius, c.area())
    }
*/



/*
	===================
	포인터 리시버 메서드 사용
	===================
*/

type wallet struct {
	balance int
}

// wallet 타입에 연결된 메서드
// 포인터 리시버 (w *wallet)
func (w *wallet) deposit(amount int) {
	w.balance += amount
	fmt.Printf("%d원 입금, 현재 잔액: %d원\n", amount, w.balance)
}

/*
    위 메서드에 대해 컴파일러는 아래와 같이 함수를 암시적으로 선언한다

    func wallet.deposit(w *wallet, amount int) {
        w.balance += amount
        fmt.Printf("%d원 입금, 현재 잔액: %d원\n", amount, w.balance)
    }
*/

/*
	================================
	다른 타입에서 선언한 메서드 사용 불가 예시
	================================
*/

type myInt int

func (m myInt) isOdd() bool {
    return m%2 == 1
}

type price myInt 


func main() {

	// ciclre 타입의 값 변수 c
	c := circle{radius: 10.0}
	c.describe()

	// wallet 타입의 값 변수 w 
	w := wallet{balance: 1000}
	w.deposit(2000)


	// price 타입은 myInt 타입을 기반으로 하지만 isOdd 메서드가 허용한 타입이 아니므로 메서드를 사용할 수 없다
	var x myInt = 3
    _ = x.isOdd()

    var p price = 1000
    // _ = p.isOdd()  // 컴파일 오류: p.isOdd() undefined
    _ = p
}