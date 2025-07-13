package main

import (
	"errors"
	"fmt"
)


/*
	===============
	errors.Is() 예시
	===============
*/

// 전역 에러 변수 선언
var ErrRecordNotFound = errors.New("기록을 찾을 수 없다")

func findRecord(id int) (string, error) {
	if id == 404 {
		return "", ErrRecordNotFound
	}

	return "찾은 기록", nil
}

func errors_is() {
	data, err := findRecord(404)

	// 에러가 ErrRecordNotFound와 같은 타입이고 값이 일치하는지 확인한다
	if errors.Is(err, ErrRecordNotFound) {
		fmt.Println("404 에러 처리")
	} else if err != nil {
		fmt.Println("다른 에러 처리")
	} else {
		fmt.Println(data)
	}
}

/*
	===============
	error.As() 예시
	===============
*/

// 커스텀 에러 타입
type UserNotFound struct {
	Code int
	Message string
}

// error 인터페이스 구현
func (e *UserNotFound) Error() string {
	return fmt.Sprintf("사용자를 찾을 수 없다 (%d: %s)", e.Code, e.Message)
}

func findUser(id int) (string, error) {
	if id == 404 {
		return "", &UserNotFound{Code: 1001, Message: "DB 문제 발생"}
	}

	return "hansanhha", nil
}

func errors_as() {
	username, err := findUser(404)

	_ = username
	var userNotFound *UserNotFound

	if err != nil && errors.As(err, &userNotFound) {
		fmt.Println(userNotFound)
	}
}

func main() {
	// errors_is()

	errors_as()

}
