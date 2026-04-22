#### 인덱스
- [스위프트를 실행하는 방법](#스위프트를-실행하는-방법)
- [언어적 특성](#언어적-특성)
- [언어 패러다임](#언어-패러다임)
- [값 타입과 참조 타입](#값-타입과-참조-타입)
- [에러 처리 모델](#에러-처리-모델)
- [메모리 관리 방식](#메모리-관리-방식)
- [타입 안정성](#타입-안정성)


## 스위프트를 실행하는 방법

Xcode, Swift Playground (앱스토어에서 설치)

Swift REPL (`swfit repl`)

`.swift` 파일 작성 후 `swift <file>`로 실행

## 언어적 특성

스위프트는 강타입/정적 타입언어로 다음과 같은 특성을 가진다
- 엄격한 변수 타입 구분
- 암묵적 타입 변환 X
- 컴파일 시점에 타입 결정

```swift
var a = 10
var b = 3.14

var c = a + b 
// error: binary operator '+' cannot be applied to operands of type 'Int' and 'Double'
```

```swift
// 명시적 변환 필요
let c = Double(a) + b
```

```swift
var name = "Swift" // 컴파일 시 String 타입으로 확정
```

컴파일 언어이므로 소스코드 작성 -> 컴파일 -> 기계어 변환 -> 실행 흐름을 가진다

다만 REPL을 지원하여 스크립트처럼 실행할 수 있다


## 언어 패러다임

스위프트가 추구하는 패러다임은 크게 3가지이다

#### 1. 객체지향 

Class, 상속, 캡슐화 등

#### 2. 프로토콜 지향

protocol + extension

인터페이스 기반 설계

#### 3. 함수형 프로그래밍

클로저

map, filter, reduce

불변성


## 값 타입과 참조 타입

**값 타입**
- 데이터를 복사해서 전달하는 타입
- `struct`, `enum`
- 독립적인 데이터를 가지게 되어 서로 영향을 주고 받지 않음 -> 스레드 세이프
- 불변성이 중요한 경우 값 타입 사용
- 즉시 데이터를 복사하지 않고 필요할 때만 복사하는 'Copy-on-Write'를 통해 성능과 안정성을 지킴

```swift
struct User {
    var name: String
}

var u1 = User(name: "A")
var u2 = u1

u2.name = "B"

print(u1.name) // A
print(u2.name) // B
```

**참조 타입**
- 주소(참조)를 공유하는 타입
- `class`
- 하나의 데이터를 여러 곳에서 공유하는 방식 -> 데이터 변경 시 모든 참조에 영향을 끼침
- 상태를 공유하는 경우나 UI 컴포넌트에 사용함

```swift
class User {
    var name: String = ""
}

var u1 = User()
u1.name = "A"

var u2 = u1
u2.name = "B"

print(u1.name) // B
print(u2.name) // B
```


## 에러 처리 모델

스위프트는 자바와 같은 언체크 예외가 없으며 명시적인 에러 처리를 강제한다

```swift
do {
    try someFunction()
} catch {
    print(error)
}
```


## 메모리 관리 방식

스위프트는 메모리를 관리할 때 가비지 컬렉터 대신 ARC라는 메커니즘을 사용한다

ARC(Automatic Reference Counting)는 객체 참조 수를 추적하여, 참조 수가 0이 되면 자동으로 메모리를 해제한다


## 타입 안정성

타입 시스템에 Optional을 추가하여 아래와 같이 값이 없을 수도 있음을 타입에 포함한다

```
var name: String? = nil
```