module example/greeter

go 1.24.4

// 가상 버전 또는 placeholder 버전
require example/message v0.0.0-incompatible

// 로컬 'message' 모듈로 사용하도록 대체
// Go 빌드 시스템은 example/message 모듈을 임포트할 때 대체된 경로의 디렉토리를 참조한다
replace example/message => ../message
