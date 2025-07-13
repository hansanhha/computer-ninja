package main

import "fmt"

func sum(values ...int64) (sum int64) {
	sum = 0
	for _, v := range values {
		sum += v
	}
	return
}

func main() {

	a0 := sum()
	a1 := sum(2)
	a2 := sum(2, 3, 5)

	// 위의 세 개 라인은 아래의 각각 라인과 동일하다
	b0 := sum([]int64{}...)
	b1 := sum([]int64{2}...)
	b2 := sum([]int64{2, 3, 5}...)

	fmt.Println(a0, a1, a2)
	fmt.Println(b0, b1, b2)

}
