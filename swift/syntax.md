#### 인덱스
- [기본사항](#기본사항)
- [변수, 상수](#변수-상수)
- [연산자](#연산자)
- [조건문](#조건문)
- [반복문](#반복문)
- [컬렉션 타입](#컬렉션-타입)
- [함수](#함수)
- [클로저](#클로저)
- [구조체](#구조체)
- [클래스](#클래스)
- [enum](#enum)
- [프로토콜](#프로토콜)
- [익스텐션](#익스텐션)


## 기본사항

스위프트에서는 글로벌 영역에서 쓰여진 코드는 프로그램의 진입점으로 사용되므로

`main()` 함수나 입출력을 위한 라이브러리를 임포트하지 않아도 코드를 실행할 수 있다

그리고 세미콜론을 생략할 수 있다

```swift
print("Hello, World!")
// Hello, World!
```

값 타입
- `Int`
- `Float`, `Double`
- `String`, `Character`
- `Bool`
- `Array<T>`
- `Dicionary<Key, Value>`
- `Set<T>`
- Tuple
- Optional
- Struct
- Enum

참조 타입
- 클래스
- 클로저

특수 타입
- 함수
- `Any`, `AnyObject`
- `Never`
- `Void`
- `self` (`Int.self`, `String.self` 등)
- Protocol

기타
- 제네릭
- 타입 별칭
- 존재 타입


**형 변환**
- 암묵적 형 변환 X
- 명시적인 형 변환
- 정수 -> 실수 형 변환: `Double(정수값)`
- 실수 -> 정수 형 변환: `Int(실수값)` - 소수점 버림

**타입 추론**
- 컴파일러는 초기값을 기반으로 변수와 상수의 타입을 추론한다

**타입 별칭**
- 타입에 새로운 이름을 지정하여 더 명확한 의미를 제공할 수 있다
  
```swift
typealias username = String
var hansanhha: username = "hansanhha"
```


## 변수, 상수

```swift
// 변수 (var 키워드 사용)
var myVariable = 42
myVariable = 50 
print(myVariable) // 50

// 상수 (let 키워드 사용)
let myConstant = 42
myConstant = 50   // error: cannot assign to value: 'myConstant' is a 'let' constant
print(myConstant) // 42
```

#### 데이터 타입 명시

스위프트에서는 변수나 상수를 선언할 때 아래와 같이 데이터 타입을 명시적으로 지정할 수 있다

데이터 타입을 명시하지 않으면 컴파일러가 초기값을 확인하여 타입을 추론한다

```swift
var name: String = "hansanhha"
let PI: Double = 3.141592
```


## 연산자

산술 연산자
- `+`
- `-`
- `*`
- `/`
- `%`

비교 연산자
- `==`
- `!=`
- `>`
- `<`
- `>=`
- `<=`

논리 연산자
- `&&`
- `||`
- `!`

기타
- `+=`
- `-=`
- `*=`
- `/=`
- `%=`

삼항 연산자
- `expression ? true_value : false_value`

범위 연산자
- `...`: 시작 값부터 끝 값까지 포함
- `..<`: 시작 값부터  끝 값 이전까지 포함

```swift
let closedRange = 1...5 // 1, 2, 3, 4, 5
let range = 1..<5 // 1, 2, 3, 4 
```


## 조건문

```swift
// if 문
if condition {
    statement
} else if condition {
    statement
} else {
    statement
}
```

```swift
// 삼항 연산자
condition ? value1 : value2
```

```swift
// switch 문
switch value {
case pattern:
    statement
case pattern:
    statement
default:
    statement
}
```

```swift
// 다중 패턴 조건
// 하나의 case에 여러 패턴을 사용할 수 있음 (쉼표로 구분)
switch char {
case "a", "b", "c":
    print("hello")
default:
    print("world")
}
```

```swift
// 범위 연산자를 사용할 수도 있음
switch num {
case 0:
    statement
case 1...5:
    statement
case 6..<10:
    statement
default:
    statement
}
```

```swift
// 값의 일부를 상수나 변수로 바인딩할 수 있음 (값 바인딩)
// 값이 바인딩되는 부분은 조건을 만족하는 것으로 취급됨
let pointX = (2, 0)

switch point {
case (let x, 0):
    statement
case (0, let y):
    statement
case let (x, y):
    statement
}
```

```swift
// where 절을 사용하여 추가 조건 지정 가능
let pointX = (2, 0)

switch point {
case let (x, y) where x == y:
    statement
case let (x, y) where x == -y:
    statement
case let (x, y):
    statement
}
```


## 반복문

```swift
// for 문 (for in)
for element in collection {
    statement
}

// while 문
while condition {
    statement
}

// repeat-while문 (do-while 문)
repeat {
    statement
} while condition
```

```swift
// 컬렉션의 각 요소 반복
for number in 1...5 {
}
```

```swift
// 배열 순회
let friuts = ["Apple", "Banana", "Cherry"]
for fruit in fruits {
    
}

// 인덱스 포함 순회
for (index, fruit) in fruits.enumerated() {

}

// 딕셔너리 순회
let capitals = ["Korea": "Seoul", "Japan": "Tokyo"]
for (country, capital) in capitals {

}
```

```swift
// where 절을 사용하여 조건을 만족하는 요소만 순회
let numbers = [10, 20, 30, 40, 50]
for number in numbers where number > 30 {

}
```


## 컬렉션 타입

스위프트는 크게 배열, 셋, 딕셔너리 컬렉션 타입을 지원한다

### 배열

하나의 타입을 선형적으로 저장하는 컬렉션으로 인덱스를 사용하여 데이터를 색인할 수 있다

```swift
// 빈 배열 생성
var numbers: [Int] = []

// 초기값과 함께 배열 생성
var carts: [String] = ["Eggs", "Milk"]
```

```swift
// 값 추가
carts.append("Lego")
carts += ["Keyboard"]

// 값 변경
carts[0] = "Bread"

// 지정된 범위만큼 값 변경
carts[1...2] = ["Cream", "Notebook"]

// 값 제거
carts.remove(at: 1) // 두 번째 항목 제거
```

```swift
// 색인
let firstItem  = carts[0]

// 순회
for item in carts {

}
```

### 셋

유일값 저장(중복값 저장 X), 순서 보장 X

```swift
// 빈 셋 생성
var letters = Set<Character>()

// 초기값과 함께 셋 생성
var numbers: Set<Int> = [1324, 2011, 3844]
```

```swift
// 값 추가
numbers.insert(651)

// 값 제거
numbers.remove(1324)

// 값 포함 여부 확인
let containsNumber = numbers.contains(3844)
```

```swift
// 순회
for number in numbers {

}
```

### 딕셔너리

키와 값의 쌍으로 데이터 저장 (각 키 값은 고유해야 함)

순서 보장 X

```swift
// 빈 딕셔너리 생성
var score: [String: Int] = [:]

// 초기값과 함께 딕셔너리 생성
var score: [String: Int] = ["swift": 10, "java": 10, "python": 8]
```

```swift
// 값 추가
score["kotiln"] = 9

// 값 수정
score["python"] = 9

// 값 제거
score["java"] = nil
```

```swift
// 값 접근
score["swift"]

// 순회
for (name, point) in score {

}
```


## 함수

```swift
// 함수 구조
func name(parameter_name: parameter_type) -> return_type {
    statement
    return result
}
```

```swift
func add(x: Int, y: Int) -> Int {
    return a + b
}

// 함수를 호출할 때 순서에 맞게 인자값과 매개변수명을 짝지어서 전달해야 한다
let result = add(x: 10, y: 20) // 30
```

함수의 매개변수는 **내부 매개변수**와 **외부 매개변수**로 나눌 수 있다

내부 매개변수는 함수 내부에서 사용되고, 외부 매개변수는 함수 호출 시 사용된다

```swift
// from: 외부 매개변수
// hometown: 내부 매개변수
func greet(person: String, from hometown: String) -> String {
    return "Hello, \(person) from \(hometown)!"
}

greet(person: "hansanhha", from: "Seoul") // Hello, hansanhha from Seoul!
```

아래와 같이 매개변수에 **기본값**을 지정할 수 있다

기본값이 있는 매개변수는 함수 호출 시 인자를 생략할 수 있다

```swift
func greet(person: String, withGreeting greeting: String = "Hello") -> String {
    return "\(greeting), \(person)!"
}

print(greet(person: "Paul")) // Hello, Paul!
print(greet(person: "hansanhha", withGreeting: "Hi")) // Hi, hansanhha!
```

함수가 호출될 때 하나의 매개변수가 여러 개의 인자를 받을 수 있는 **가변 매개변수**를 지원한다

가변 매개변수는 함수 내에서 배열처럼 사용된다

```swift
func sumOf(numbers: Int ...) -> Int {
    var total = 0
    for number in numbers {
        total += number
    }
    return total
}

sumOf(numbers: 1, 2, 3, 4, 5) // 15
```

스위프트는 다중 반환 값을 지원하는데, 이 때 **튜플**을 사용한다

```swift
func calculate(a: Int, b: Int) -> (sum: Int, substract: Int product: Int, divide: Int) {
    let sum = a + b
    let substract = a - b
    let product = a * b
    let divide = a / b
    return (sum, substract, product, divide)
}
```


## 클로저

클로저는 함수처럼 실행 가능한 코드 블록으로, 자신이 정의될 때의 환경(변수, 상태)을 캡처하는 특징을 가진다

일반 함수는 호출될 때 외부 상태와 분리되어 동작하는데 클로저는 만들어지는 시점의 스코프를 계속 유지한다

특징
- 1급 객체 (변수에 저장, 전달, 함수 반환값으로 사용 가능)
- 외부 변수 캡처
- 간결한 문법
- 비동기/콜백 중심 구조
- ARC와 결합된 메모리 이슈 발생 가능

```swift
// 구조
{
    (parameters) -> return_type in
        statement
}
```

```swift
let add = { (a: Int, b: Int) -> Int in 
    return a + b
}
```

클로저는 컨텍스트를 파악하여 매개변수와 반환 타입을 유추할 수 있어서 아래와 같이 매개변수 타입과 반환 타입을 생략할 수 있다

```swift
let numbers = [1, 2, 3, 4, 5]
let doubledNumbers = numbers.map { number in 
    return number * 2
}
// [2, 4, 6, 8, 10]
```

단일 표현식을 반환하면 `return` 키워드를 생략할 수 있다

```swift
let doubledNumbers = numbers.map { number in 
    number * 2
}
// [2, 4, 6, 8, 10]
```

클로저에서는 매개변수 이름을 `$0`, `$1` 와 같은 축약 인자를 사용할 수 있다

```swift
let doubledNumbers = numbers.map {
    $0 * 2
}
// [2, 4, 6, 8, 10]
```

### 클로저 활용 방법

클로저를 활용하면 상태 은닉, 함수형 프로그래밍, 비동기 처리에서의 컨텍스트 유지, 의존성 없이 로직 캡슐화 등을 구현할 수 있다

#### 1. 컬렉션 처리

```swift
let numbers = [1, 2, 3, 4]
let doubled = numbers.map { $0 * 2 }
```

#### 2. 비동기 처리

```swift
func fetchData(completion: (String) -> Void) {
    completion("fetched data")
}

fetchData { result in
    print(result)
}
// fetched data
```

#### 3. 클로저 캡처

클로저에서 외부 변수를 캡처한다

```swift
var count = 0

let closure = {
    count += 1
}

closure() // count: 1
closure() // count: 2
```

```swift
// 위의 closure를 풀어쓰면 아래와 같다
let closure: () -> Void = { () -> Void in 
    count += 1
}
```

### guard 문

guard는 조건이 만족되지 않으면 함수나 반복문을 탈출하거나 값을 반환하게 한다 (조건문을 한 줄로 정의하는 도구)

-> 예외 처리를 초기에 제거하는 방식

일반적으로 다음과 같은 상황에서 guard를 사용한다
- 조건이 실패하면 코드를 더 진행할 필요가 없는 경우
- 예외 케이스를 초기에 제거하고 싶은 경우
- 조건식에서 할당한 값을 이후에도 사용하는 경우

```swift
// 구조
guard condtion else {
    // 조건이 false일 때 실행
    return / break / continue / throw
}

// value는 guard 블록 이후에도 사용할 수 있다
guard let value = optionalValue else {
    return
}
```

```swift
// if 문을 사용할 때
if user != nil {
    print(user!) // 강제 언래핑
}

// guard 방식
guard let user = user else {
    return
}

print(user) // 안전한 언래핑
```


```swift
// 중첩 if 문
if let user = user {
    if user.isActive {
        if user.age > 18 {
            print("OK")
        }
    }
}

// guard 사용
guard let user = user else { return }
guard user.isActive else { return }
guard user.age > 18 else { return }

print("OK")

// 동시에 여러 조건 처리
guard let user = user,
      user.age > 18,
      isLoggedIn else {
    return
}
```

```swift
// 반복문에서의 guard
// 조건에 맞는 것만 출력
for item in items {
    guard item.isValid else { continue }
    print(item)
}
```


## 구조체

구조체는 관련된 데이터와 동작을 하나로 묶는 값 타입이다

```swift
struct Product {
    var name: String
    var amount: Int

    func getAmount() -> Int {
        return amount
    }
}

// 스위프트 구조체는 모든 속성을 초기화하는 생성자를 기본적으로 제공한다
let laptop = Product(name: "Macbook-Air M5", amount: 100000)

laptop.getAmount() // 100000
```

```swift
struct Product {
    var name: String
    var amount: Int
    var originalAmount: Int

    // 커스텀 생성자
    init(name: String, amount: Int) {
        self.name = name
        self.amount = amount
        self.originalAmount = amount
    }
}
```

구조체는 전체가 값이므로 인스턴스가 `let`으로 선언되면 내부도 전부 불변이 된다

이 경우 프로퍼티를 변경하려면 [`mutating`](#mutating) 키워드를 사용해야 한다

```swift
struct User {
    var name: String
}

let user = User(name: "hansanhha")
user.name = "unknown" // 변경 불가
```

### 구조체 프로퍼티

구조체 프로퍼티는 크게 3가지로 구분된다
- 저장 프로퍼티: 실제로 값을 메모리에 저장하는 프로퍼티
- 연산 프로퍼티: 값을 저장하지 않고 계산하는 프로퍼티
- 타입 프로퍼티: 인스턴스가 아니라 타입 자체에 속하는 프로퍼티 (static)

#### 1. 저장 프로퍼티

```swift
// 저장 프로퍼티
struct Product {
    var name: String = "Unknown" // 초기값이 있으면 생성자에서 생략 가능
    let createdAt: String
}

// 저장 프로퍼티 (lazy 프로퍼티)
// 프로퍼티에 처음 접근할 때 loadData()가 실행된다
// 비용이 큰 연산에 사용하기 적합한 방식
struct DataLoader {
    lazy var data: [Int] = loadData()

    func loadData() -> [Int] {
        return [1, 2, 3]
    }
}

// 프로퍼티 옵저버 (willSet / didSet)
// 프로퍼티의 상태 변화를 추적한다
struct User {
    var age: Int = 0 {
        willSet {
            print("변경 예정: \(age)")
        }

        didSet {
            print("변경 완료: \(oldValue) -> \(newValue)")
        }
    }
}
```

#### 2. 연산 프로퍼티

연산 프로퍼티는 값을 저장하지 않고 계산하며 항상 최신 상태를 보장한다 (계산 비용이 있긴 함)

```swift
struct Rectangle {
    var width: Double
    var height: Double

    // area는 메모리에 저장되지 않는다
    var area: Double {
        width * height
    }
}
```

```swift
// getter, setter
struct Rectangle {
    var width: Double
    var height: Double

    var area: Double {
        get {
            width * height
        }

        set {
            width = newValue / height
        }
    }
}

var rect = Rectangle(width: 10, height: 20)

rect.area // 200

rect.area = 400
rect.width // 20
```

#### 3. 타입 프로퍼티

인스턴스가 아닌 타입에 속한 프로퍼티 (정적 필드)

```swift
struct Math {
    static let PI = 3.14
}
```

### 구조체 복사

구조체는 값 타입이므로 할당 연산자를 이용하여 다른 변수에 대입하면 복사본이 생성된다

```swift
var macbookAirM3 = Product(name: "Macbook-Air M3", amount: 100000)
var macbookAirM5 = macbookAirM3

macbookAirM5.name = "Macbook-Air M5"
// macbookAirM3.name: Macbook-Air M3
// macbookAirM5.name: Macbook-Air M5
```

### 구조체 메서드 (`mutating`)

스위프트의 구조체에는 인스턴스와 타입(정적) 메서드가 있다

구조체의 속성 값을 바꾸는 인스턴스 메서드에는 `mutating` 키워드를 사용한다

`mutating` 키워드없이 구조체의 필드를 변경하려고 하면 컴파일 에러가 발생한다

```swift
struct Counter {
    var count = 0

    mutating func increment() {
        count += 1
    }
}

// let으로 선언하면 mutating으로 선언된 increment를 호출할 수 없다
var counter = Counter()
counter.increment()
counter.increment()
counter.increment()

counter.count // 3
```

```swift
// static 메서드
struct Math {
    static func square(_ number: Int) -> Int {
        return number * number
    }
}

let result = Math.sqaure(5) // 25
```

### 구조체 기반 타입

Int

Double

String

Array

Dictionary

Set


## 클래스

클래스도 구조체처럼 여러 속성과 메서드를 가지는 데이터 타입이지만, 값 타입인 구조체와 달리 **참조 타입**이며 **상속**을 지원한다

```swift
class Product {
    var name: String
    var amount: Int

    init(name: String, amount: Int) {
        self.name = name
        self.amount = amount
    }
}

let keyboard = Product(name: "Magic Keyboard", amount: 100000)

print(keyboard.name) // Magic Keyboard
print(keyboard.amount) // 100000
```

클래스의 경우 모든 속성이 기본값을 가지면 컴파일러가 자동으로 기본값으로 속성을 설정하는 이니셜라이저를 생성한다

커스텀 이니셜라이저를 만들어 별도 방식으로 클래스를 초기화할 수 있다

기본 이니셜라이저와 커스텀 이니셜라이저가 모두 없는 경우 클래스를 생성할 수 없다

```swift
// 모든 속성이 기본값을 가지면 자동 이니셜라이저가 생성된다
// 단, 자동 이니셜라이저를 이용할 때는 생성자에 인자를 전달할 수 없다
class Product {
    var name: String = ""
    var amount: Int = 0
}

var p = Product()
var p2 = Product(name: "pencil", amount: 1000) // 컴파일 오류


// 기본, 커스텀 이니셜라이저 모두 없으면 컴파일 오류가 발생한다
class Product {
    var name: String
    var amount: Int
}
```

클래스는 인스턴스가 메모리에서 해제되기 직전에 호출되는 **디이니셜라이저**를 가질 수 있다

```swift
class Product {
    var name: String = ""
    var amount: Int = 0

    init(name: String, amount: Int) {
        self.name = name
        self.amount = amount
    }

    deinit {
        print("\(name) is being deinitialized")
    }
}

// nil로 설정될 수 있게 인스턴스를 옵셔널로 설정
var keyboard: Product? = Product(name: "Magic Keyboard", amount: 100000)
keyboard = nil // Magic Keyboard is deinitialized
```

### 클래스 vs 구조체

참조 타입/값 타입
- 클래스는 참조 타입으로 인스턴스를 변수나 상수에 할당하거나 함수에 전달할 때 실제 데이터가 아닌 참조가 전달된다
- 구조체는 값 타입으로 인스턴스를 변수나 상수에 할당하거나 함수에 전달할 때 값이 복사된다

상속
- 클래스: 상속 지원
- 구조체: 상속 미지원

디이니셜라이저
- 클래스: 디이니셜라이저 지원
- 구조체: 디이니셜라이저 미지원

### 상속

```swift
// 부모 클래스
class Product {
    var name: String = ""
    var amount: Double  = 0.0

    func describe() -> String {
        return "\(name) is \(amount) won"
    }
}

// 자식 클래스
class Snack: Product {
    var category: String = ""
}

let lays = Snack()
lays.name = "Lays"
lays.amount = 5000
print(lays.describe()) // lays is 5000.0 won
```

```swift
// 메서드 오버라이드
class Car: Product {
    var brand: String = ""

    override func describe() -> String {
        return "\(name) (\(brand)) is \(amount) won"
    }
}

let car = Car()
car.brand = "KIA"
car.name = "K5"
car.amount = 30000000
print(car.describe()) // K5 (KIA) is 30000000.0 won
```

```swift
// super 키워드
super.부모클래스_속성
super.부모클래스_메서드()
```

### 동일성과 동등성

동일성: 참조가 동일한지 (`===`)

동등성: 값이 동일한지

```swift
class Product {
    var name: String

    init(name: String) {
        self.name = name
    }
}

let p1 = Product(name: "hansanhha")
let p2 = p1

// 동일한 참조를 공유 (동등성)
p1.name = "userA"
print(p1.name) // "userA"
print(p2.name) // "userB"
print(p1 === p2) // true

// 동일한 값을 보유 (동일성)
let p3 = Product(name: "MUJI")
let p4 = Product(name: "MUJI")
print(p3 === p4) // false
print(p3.name == p4.name) // true
```

### 강한 참조 사이클

스위프트는 GC 대신 ARC (Automatic Reference Counting)라는 메커니즘을 통해 메모리를 관리한다

ARC는 객체 참조 수를 추적하여, 참조 수가 0이 되면 해당 인스턴스의 메모리를 해제한다

이 떄 두 개 이상의 인스턴스가 서로를 참조하고 있으면 실제로 사용하지 않더라도 참조가 유지되어 메모리에서 해제되지 않는 상황이 발생할 수 있다

이를 강한 참조 사이클이라고 한다

```swift
class Person {
    var name: String
    var friend: Person?

    init(name: String) {
        self.name = name
    }
}

let alice = Person(name: "Alice")
let bob = Person(name: "Bob")

alice.friend = bob
bob.friend = alice
```

alice와 bob은 friend 속성을 통해 서로를 참조하고 있어서 메모리에서 해제될 수 없다 -> 강한 참조 사이클 발생

이를 해결하려면 **약한 참조(weak reference)**나 **미소유 참조(unowned reference)**를 사용할 수 있다

#### 약한 참조

**약한 참조**는 `weak` 키워드를 사용하는데 참조 대상 객체를 소유하지 않음으로써 ARC 카운트를 증가시키지 않는다

반드시 옵셔널 타입으로 선언되어야 하며, 참조 대상이 메모리에서 해제되면 자동으로 nil로 변경된다

```swift
class Person {
    var name: String
    weak var friend: Person?

    init(name: String) {
        self.name = name
    }
}

let alice = Person(name: "Alice")
let bob = Person(name: "Bob")

alice.friend = bob
bob.friend = alice
```

#### 미소유 참조

미소유 참조도 약한 참조와 같이 참조를 소유하지 않아서 ARC 카운트가 증가되지 않는다

다만 절대 nil이 되지 않으며 항상 값이 있다고 가정한다

참조 대상이 먼저 해제되면 런타임 오류가 발생한다 (대신 옵셔널 처리는 필요 없음)

```swift
class Customer {
    let name: String
    var card: CreditCard?

    init(name: String) {
        self.name = name
    }
}

class CreditCard {
    let number: UInt64
    unowned let customer: Customer

    init(number: UInt64, customer: Customer) {
        self.number = number
        self.customer = customer
    }
}
```


## enum

```swift
// enum의 각 요소에 case 키워드를 사용한다
enum Direction {
    case north
    case south
    case east
    case west
}

let dir = Direction.north
```


```swift
// 각 case에 기본값을 설정할 수 있다
enum HttpStatus: Int {
    case success = 200
    case notFound = 404
}

let ok_value = HttpStatus.success.rawValue // 200
let notFound = HttpStatus(rawValue: 404) // HttpStatus? 
```

```swift
// 메서드도 포함할 수 있다
enum Direction {
    case north, south, east, west

    func description() -> String {
        switch self {
        case .north: return "북"
        case .south: return "남"
        case .east: return "동"
        case .west: return "서"
        }
    }
}
```

```swift
// CaseIterable을 상속받아서 모든 case를 순회할 수 있다
enum Direction: CaseIterable {
    case north, south, east, west
}

for d in Direction.allCases {
    print(d)
}
```


## 프로토콜

프로토콜은 인터페이스처럼 요구사항을 정의하는 타입이다

-> 확장성 증가, 결합도 낮춤

```swift
// 프로토콜 정의 
protocol Flyable {
    func fly()
}

// 프로토콜 구현(채택)
// 클래스, 구조체, enum 모두 프로토콜을 구현할 수 있다
struct Bird: Flyable {
    func fly() {
        print("~~~")
    }
}
```


프로토콜은 메서드와 프로퍼티 요구사항을 정의할 수 있다

```swift
// 메서드 요구사항 정의
// 프로토콜을 채택한 타입은 이 형태의 메서드를 구현해야 한다는 계약을 정의한다
protocol Drawable {
    func draw()
}

// 프로퍼티 요구사항 정의
// 프로토콜을 채택한 타입은 이 형태의 값을 제공할 수 있어야 한다는 계약을 정의한다
protocol Named {

    // name이라는 프로퍼티가 있어야 한다
    // 타입: String
    // 읽기(get), 쓰기(set)이 가능해야 한다
    // 다만 꼭 저장 프로퍼티일 필요는 없다
    var name: String { get set }

    // 타입 프로퍼티 요구사항 
    // 읽기만 가능해야 한다
    static var id: String { get }
}
```


## 익스텐션

익스텐션은 기존 타입을 건드리지 않고 기능을 추가할 수 있다

```swift
// 구조
extension 기존타입 {
    // 기능 추가
}
```

```swift
// 프로토콜에 대한 기능 추가
protocol Printable {
    func printValue()
}

extension Int: Printable {
    func printValue() {
        print(self)
    }
}
```

### 익스텐션 예시

```swift
protocol Describable {
    func describe() -> String
}

extension Describable {
    func describe() -> String {
        return "설명 없음"
    }
}

struct User: Describable {
    let name: String
}

let user = User(name: "홍길동")
print(user.describe())  // "설명 없음"
```


```swift
class User {
    let name: String
    
    init(name: String) {
        self.name = name
    }
}

// MARK: - Formatting
extension User {
    func displayName() -> String {
        return "사용자: \(name)"
    }
}

var u = User(name: "hansanhha")
u.displayName() // "사용자: hansanhha"
```