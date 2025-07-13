package main

import (
	"errors"
	"fmt"
)

/*
	====================
	fmt.Errorf(), %w 예시
	====================
*/

// 전역 에러 변수 선언
var Unauthorized = errors.New("unauthorized")

// 에러 반환
func authenticate(username string) (bool, error) {
	if username != "hansanhha" {
		return false, Unauthorized
	}

	return true, nil
}

func authorize(username string) (bool, error) {
	_, err := authenticate(username)

	// authenticate 함수에서 반환한 에러를 래핑하여 새로운 에러를 반환한다
	if err != nil {
		return false, fmt.Errorf("authorization failed: %w", err)
	}

	return true, nil
}

func fmt_Errorf_w() {
	_, err := authorize("user")

	if err != nil {
		// Unauthorized를 
		fmt.Println(err)

		// 래핑된 에러가 Unauthorized와 같은 타입이고 값이 일치하는지 확인한다
		if errors.Is(err, Unauthorized) {
			fmt.Println(Unauthorized)
		}
	}
}

/*
	=========================
	커스텀 Wrapper 인터페이스 구현
	=========================
*/

var PostNotFound = errors.New("Post Not Found")

// 커스텀 에러 타입 (다른 에러를 감싸는 에러 래핑)
type EntityNotFound struct {
	Msg string
	Cause error
}

// Error() 메서드 구현: error 인터페이스 만족
func (e *EntityNotFound) Error() string {
	if e.Cause != nil {
		return fmt.Sprintf("%s: %v", e.Msg, e.Cause)
	}

	return e.Msg
}

// Unwrap() 메서드 구현: Wrapper 인터페이스 만족
func (e *EntityNotFound) Unwrap() error {
	return e.Cause
}

func findPost(id int) (string, error) {
	if id == 0 {

		// 에러 래핑(EntityNotFound)를 통해 PostNotFound를 감싼다
		return "", &EntityNotFound {
			Msg: "엔티티 이름이 비어있다",
			Cause: PostNotFound,
		}
	}

	return "post", nil
}

func custom_error_wrapper() {
	_, err := findPost(0)

	if err != nil {
		// 전체 에러 출력
		fmt.Println(err)

		// 래핑된 에러가 PostNotFound 타입이고 값이 일치하는지 확인한다
		if errors.Is(err, PostNotFound) {
			fmt.Printf("에러 발생: %v\n", PostNotFound)
		}

		// 에러가 래핑 에러인지 확인하고 래핑 에러의 정보를 추출한다
		var wrappingErr *EntityNotFound
		if errors.As(err, &wrappingErr) {
			fmt.Printf("래핑 에러 메시지: %s\n", wrappingErr.Msg)
			fmt.Printf("래핑 에러의 원본 에러: %v\n", wrappingErr.Cause)
		}
	}

}


func main() {

	// fmt_Errorf_w()

	custom_error_wrapper()
}
