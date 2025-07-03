[이전으로](../README.md)

[자바 문법 요약](#자바-문법-요약)

[LTS 버전 별 주요 기능](#lts-버전-별-주요-기능)

**주요 문법 변화**

[신규 타입: Record](#신규-타입-record)

[신규 타입: Sealed-Class](#신규-타입-sealed-class)

[신규 타입 (프리뷰): Value Class (Primitive Class)](#신규-타입-프리뷰-value-class-primitive-class)

[패턴 매칭: instanceof, Switch(구조적 분해, 완전한 패턴 매칭, Guarded 패턴)](#패턴-매칭-instanceof-switch구조적-분해-완전한-패턴-매칭-guarded-패턴)

[문자열: Text Block, String Template](#문자열-지원-text-block-string-template)

[Unnamed Pattern/Variable/Class](#unnamed-patternvariableclass)

[(논의중) Null-restricted Type](#논의중-null-restricted-type)

[`ScopedValue<T>`](#scopedvaluet)


## 자바 문법 요약

### 기본

변수 선언: 변수, 상수 (final)

기본 자료형: int, float, double, char, boolean, byte, short, long

제어문
- 조건: if, switch, switch pattern, instanceof pattern
- 반복: for, foreach while, do-while
- 분기: break, continue, return

문자열
- 텍스트 블록
- string templates

지역 변수 타입 추론: var

Project Amber
- unnamed patterns
- unnamed variable
- unnamed classes & main

Project Valhalla
- primitive class (value class)

### 객체 지향

타입: class (abstract, final), interface, enum, annotation, record, sealed/permits/non-sealed 

상속/확장: abstract, extends, implements

참조: this, super

생성자: this(), super()

접근 제어자: public, protected, private, package-private (default)

### 제네릭

제네릭: `<T>`, `<E>`, `<K, V>`

와일드카드: `?`, `<? extends T>`, `<? super T>`

제네릭 메서드: `<T> T method(T param)`

### 예외 처리

try-catch-finally

try-with-resources

throw, throws

checked vs unchecked 예외

### 함수형 프로그래밍

람다식
- () -> expression
- x -> expression
- (x, y) -> expression
- x -> { statements; }
- () -> { statements; }
- (x, y) -> { statements; }

메서드 참조
- `ClassName::staticMethod`
- `instance::instanceMethod`
- `ClassName::new`

@FunctionalInterface
- `Supplier<T>`: () -> T
- `Consumer<T>`: T -> void
- `Function<T, R>`: T -> R
- `Predicate<T>`: T -> boolean
- `BiFunction<T, U, R>`: (T, U) -> R

Stream/Optional API
- `Stream<T>`
- 중간 연산: filter, map, flatMap 등
- 최종 연산: collect, forEach, reduce 등
- `Optional<T>`: isPresent, ifPresent, orElse, orElseGet, orElseThrow. map 등

### 모듈

모듈 설정: module-info.java

모듈 정의: module

의존성 선언: requires, requires static, requires transitive

모듈 공개: exports, opens

모듈 사용: requires, uses, provides

### 키워드

**제어문**: if/else, switch/case/default/yield, while/do/for/break/continue/return

**클래스 관련**: class, interface, enum, record, sealed/permits/non-sealed

**객체 지향**: extends, implements, super, this, instanceof, new, default/static/final/abstract

**접근 제어**: public/protected/private

**예외 처리**: try/catch/finally, throw/throws

**타입 관련**: var, int/long/short/byte, float/double, chat, boolean, void, null

**수식**
- synchronized: 스레드 동기화 시 사용 
- volatile: 변수의 값을 항상 메인 메모리에서 읽도록 지정
- native: 자바에서 외부 코드와 연동할 때 사용 (JNI 등)
- transient: 직렬화에서 제외할 필드 지정
- strictfp: 부동소수점 계산을 플랫폼에 관계없이 일관되게 처리

**모듈**: module, requires, exports, opens, uses, provides

**기타**: package, import, unnamed, assert

사용되지 않지만 예약된 키워드: const, goto


## LTS 버전 별 주요 기능

### 8 버전: 함수형 패러다임 도입

람다식, 메서드 참조, 함수형 인터페이스, 스트림/옵셔널 API

인터페이스 defalut, static 메서드

날짜 및 시간 API: java.time 패키지

### 11 버전: 모듈 시스템 도입, 실용적 개선

JPMS(Java Platform Module System) 모듈 시스템 도입

HttpClient: java.net.http 패키지

String API: String.isBlank, String.lines, String.strip() 등

Collection API 개선: List.of(), Set.of(), Map.of() 등

Flight Recorder/JFR: 저오버헤드 성능/진단 기록 기능

Launch Single-File Programs: 단일 파일로 자바 프로그램 실행 가능

### 17 버전: 현대적 문법 도입

Sealed Class, Record 도입

instanceof 패턴 매칭 개선

Switch 표현식: yield 키워드 도입

Text Block

### 21 버전: 경량 스레드 + 패턴 매칭 고도화

가상 스레드: 수천 개의 경량 스레드 생성 가능

Structured Concurrency: 여러 스레드로 실행되는 작업을 구조적으로 정리

switch 패턴 매칭 개선

레코드 필드 패턴 매칭 가능

Sequenced Collection: 순서 보장 컬렉션/맵 도입

String Template

프리뷰 기능
- Foreign Function & Memory API: C/C++ 네이티브 코드 호출 (JNI 대체)
- Unnamed Patterns and Variables: _ 기호로 미사용 변수 처리
- Scoped Values: 스레드 세이프한 데이터 전달 방식 (ThreadLocal 대체)

### 25 버전: 구조적 패턴 매칭과 메모리 모델 개선

Unnamed Classes, Variables, Patterns: 코드 작성 간결화

Value Class: identity 없는 경량 객체 지원 (val class)

Null-restricted Type: null을 허용하지 않는 타입 (논의 중)

switch 패턴 매칭 개선: switch 문에서 구조적 패턴 사용

FFM API 정식화: C 라이브러리 호출, 메모리 접근 표준화

Class File API: 바이트코드 분석을 위한 공식 API 도입


## 신규 타입: Record

[전체 예시 코드](./src/main/java/record)

[전체 테스트 코드](./src/test/java/record)

기존에 자바에서 POJO(Plain Old Java Object) 클래스 (또는 데이터 클래스)를 하나 만들 때 아래와 같이 장황하고 반복적인 코드를 작성해야 했다

그래서 IDE의 도움을 받거나 Lombok을 사용하여 생성자, 게터/세터, equals, hashCode, toString 등을 자동으로 생성하는 방법을 사용했다

```java
// 기존 클래스 정의
// 장황하고 반복되는 코드가 많다
public class Person_ {

    private final String name;
    private final int age;

    public Person_(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + age;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person_ person_ &&
                this.name.equals(person_.name) &&
                this.age == person_.age;
    }

    @Override
    public String toString() {
        return "Person_ name: " + name + " age: " + age;
    }

}
```

이러한 보일러 플레이트 코드를 줄이고 간결하게 데이터 클래스를 정의할 수 있도록 자바는 16 버전에 Record 타입을 정식으로 도입했다

record 키워드를 사용하며 레코드 타입 이름 옆의 헤더(괄호)에 컴포넌트(필드)를 나열하여 정의할 수 있다

레코드는 컴포넌트만 정의하면 자바 컴파일러가 암묵적으로 필드, 생성자, 게터 등을 생성해준다

```java
// 레코드 타입 정의
// 레코드는 불변 객체를 만들기 위한 간결한 방법을 제공한다
public record Person(String name, int age) {
    
    /*
        암묵적으로 생성되는 코드

        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        equals, hashCode, toString 메서드도 자동으로 생성된다
     */
}
```

레코드를 정의하면 다음과 같은 기능을 자동으로 제공한다
- private final 필드 (레코드 헤더에 정의된 컴포넌트)
- public 생성자: `public Person(String name, int age)`
- 각 필드에 대한 게터 (메서드 이름은 필드명과 동일함)
- equals, hashCode, toString 메서드

레코드는 생성된 이후 내부 상태가 절대로 변하지 않는 불변 객체(Immutable Object)이다

생성자에서만 값을 설정할 수 있고, 세터가 없어서 멀티스레딩 환경에서 안전하다 (동시성에 강하며 버그 발생 가능성이 낮음)

-> 다만 mutable 타입을 필드로 갖는 경우 내부 참조는 바뀔 수 있다

참고로 **레코드는 final이기 때문에 상속할 수 없고, 다른 클래스를 상속할 수도 없다**

### 특이한 Record 생성자

레코드는 세 가지의 생성자를 지원하며 각 생성자는 레코드의 접근 제어자보다 더 제한적인 접근 제어자를 가질 수 없다

#### 1. Canonical Constructor (표준 생성자)

표준 생성자는 레코드 헤더에 정의된 컴포넌트(필드)와 동일한 이름과 타입을 갖는 생성자이다

암묵적으로 생성되지만 아래와 같이 유효성 검사 등의 목적으로 명시적으로 정의하여 오버라이드할 수 있다

```java
public record Person(String name, int age) {
    
    // Canonical Constructor
    public Person(String name, int age) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }
        if (age < 0) {
            throw new IllegalArgumentException("나이는 음수일 수 없습니다");
        }
    }
}
```

#### 2. Compact Constructor (축약 생성자)

Canonical Constructor과 동일하지만 파라미터 선언 없이 블록만 작성할 수 있다

참고로 Canonical Constructor와 Compact Constructor는 둘 중 하나만 정의할 수 있다

```java
public record Person(String name, int age) {
    
    // Compact Constructor
    public Person {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비어있을 수 없습니다");
        }
        if (age < 0) {
            throw new IllegalArgumentException("나이는 음수일 수 없습니다");
        }
    }
}
```

#### 3. Non-canonical Constructor

비표준 생성자는 필드 개수나 이름이 레코드 헤더와 다를 수 있으며, 여러 개를 정의할 수 있다

다만 반드시 this()를 호출하여 Canonical/Compactor Constructor를 반드시 호출해야 한다

this() 호출 후 로직을 추가하여 유효성 검사나 기본값 설정 등을 할 수 있다

```java
public record Person(String name, int age) {
    
    // Non-canonical Constructor
    public Person(String firstName, String lastName, int age) {
        this(firstName + " " + lastName, age);
    }
    
    // Non-canonical Constructor
    public Person(String name) {
        this("이름 없음", 0);
    }
}
```

#### 중첩 레코드

[테스트 코드](./src/test/java/record/NestedRecordTest.java)

클래스에서 중첩 클래스를 정의하거나 포함하듯이 레코드에서도 또 다른 레코드를 정의하거나 헤더에 포함할 수 있다

중첩 레코드를 적절히 사용하면 불변성을 자연스럽게 유지하고 도메인 단위의 모델을 계층적으로 구성할 수 있으며 JSON 직렬화/역직렬화 시에도 유용하다

```java
public record Address(String street, String city) { }

// Address 레코드를 포함하는 Person 레코드
public record Person(String name, int age, Address address) { }
```

```java
// Vehicle.SportsCar
// Vehicle.Truck
// Vehicle.Motorcycle
public record Vehicle(String name, int price) {

    public record SportsCar(String name, int price, String color) { }

    public record Truck(String name, int price, int capacity) { }

    public record Motorcycle(String name, int price, String type) { }

}
```

### Record 직렬화/역직렬화

[테스트 코드](./src/test/java/record/SerializationTest.java)

레코드는 Jackson 2.12.0 이상에서 직렬화가 지원된다 (Gson도 사용 가능)

레코드를 JSON으로 직렬화할 때는 레코드의 필드 이름이 JSON 키로 사용되며, 반대로 역직렬화 시 생성자를 기반으로 하여 JSON 키가 레코드의 필드 이름에 매칭된다

따라서 레코드의 필드 이름과 JSON 키가 일치해야 한다

```java
ObjectMapper mapper = new ObjectMapper();

// 레코드 타입 정의
record Person(String name, int age) {}
Person person = new Person("hansanhha", 30);

// 직렬화
String serializedPerson = mapper.writeValueAsString(person);

// 역직렬화
Person deserializedPerson = mapper.readValue(serializedPerson, Person.class);
```

필드명과 JSON 키를 다르게 관리하고 싶은 경우 @JsonCreator와 @JsonProperty 어노테이션을 사용하여 서로 매핑할 수 있다

```java
record Person(String name, int age) {

    // 직렬화 시 name 필드가 fullName으로 매핑된다
    // 역직렬화 시 fullName 키를 name 필드로 매핑한다
    @JsonCreator
    Person(@JsonProperty("fullName") String name, @JsonProperty("age") int age) {
        this.name = name;
        this.age = age;
    }
}
```

### Local Record

레코드는 로컬 클래스처럼 메서드 내부에서 임시적으로 record 타입을 정의할 수 있다

DTO처럼 사용할 수 있으며 메서드 내부 로직에서만 쓰이므로 캡슐화가 좋으며 스트림 처리에 활용할 수 있다

로컬 레코드는 public 등 접근 제어자를 지정할 수 없고, 메서드 내부에서만 사용 가능하다

```java
@ParameterizedTest
@ValueSource(strings = {"java", "kotlin", "python"})
void 레코드는_메서드_내부에_정의할_수_있다(String word) {
    
    // 메서드 내부에 레코드 타입 정의
    record WordLength(String word, int length) {}

    WordLength wordLength = new WordLength(word, word.length());
}
```

### 레코드의 equals/hashCode

레코드는 컴포넌트(필드)의 **모든 값이 동일하면** equals가 true를 반환한다

hashCode는 모든 필드를 기반으로 계산한다 (Objects.hash 메서드 사용)

기본적으로 레코드의 equals와 hasCode는 **모든 필드를 사용하여 동작한다**

### 레코드 용도

레코드는 타입 안정성과 간결한 문법을 제공하는 값 객체로써 불변성을 가진다

이러한 특성을 활용하여 VO, DTO, 컬렉션 키 등 다양하게 활용할 수 있다

```java
record Post(String title) {}

// 컬렉션 키
Map<Post, List<Comment>> posts;
```

## 신규 타입: Sealed-Class

[전체 예시 코드](./src/main/java/sealed)

봉인된 클래스(Sealed Class)는 자바 17에 정식 도입된 기능으로 상속 가능한 하위 클래스를 명시적으로 제한할 수 있는 기능이다

일반적으로 자바에서 더이상의 상속을 허용하지 않으려면 아래와 같이 클래스에 `final` 키워드를 붙인다

```java
// final 키워드를 사용하여 클래스 상속을 제한할 수 있다
// 다만 모든 클래스의 상속을 제한하게 된다
public final class FinalClass { }
```

무분별한 확장을 방지하면서 누가 상속 가능한지 명시적으로 정의하기 위해 Sealed Class 개념이 도입되었다

#### Sealed Class (Interface) 정의 방법 
- 확장을 방지할 클래스에 sealed 키워드를 붙이고, permits 키워드로 허용할 하위 클래스를 명시한다 (컴파일 타임에 강제됨)
- permits 키워드에 명시된 클래스는 반드시 해당 sealed 클래스를 상속해야 한다
- 또한 하위 클래스는 반드시 sealed, non-sealed, final 중 하나의 키워드를 사용하여 상속 정책을 정의해야 한다

sealed: 상속을 제한적으로 허용한다 (permits 키워드로 명시된 클래스만 sealed 클래스를 상속할 수 있다)

non-sealed: 모든 클래스의 상속을 허용한다 (해당 클래스의 하위 클래스는 자유롭게 상속할 수 있다)

final: 해당 클래스의 상속을 완전히 제한한다

```java
// Sealed Class 정의, permits 키워드를 사용하여 상속을 제한한다
// Circle, Rectangle, Triangle 클래스만 상속할 수 있다
public sealed class Shape
        permits Circle, Rectangle, Triangle { }


// Circle 클래스는 Shape 클래스를 상속하며 final 키워드를 사용하여 더 이상 상속할 수 없음을 명시한다
public final class Circle
        extends Shape { }


// Rectangle 클래스는 Shape 클래스를 상속하며 non-sealed 키워드를 사용하여 자유로운 상속을 명시한다
public non-sealed class Rectangle
        extends Shape { }


// Rectangle 클래스의 non-sealed로 인해 Square 클래스는 Rectangle 클래스를 상속할 수 있다
public class Square extends Rectangle { }


// Triangle 클래스는 Shape 클래스를 상속하며 sealed 키워드를 사용하여 하위 클래스를 제한한다
public sealed class Triangle
        extends Shape
        permits DoubleTriangle { }

// DoubleTriangle 클래스는 Triangle 클래스를 상속하며 non-sealed 키워드를 사용하여 자유로운 상속을 명시한다
public non-sealed class DoubleTriangle
        extends Triangle { }
```

인터페이스도 sealed로 제한할 수 있다

참고로 Record 타입은 final이므로 더 이상의 확장을 이어나갈 수 없다 

```java
public sealed interface Expression 
        permits Add, Subtract, Multiply, Divide {

    // Expression 인터페이스는 Add, Subtract, Multiply, Divide 클래스만 구현할 수 있다
    // permits 키워드를 사용하여 구현 가능한 클래스들을 제한한다
    double evaluate();

}

public record Add(double x, double b) implements Expression {

    @Override
    public double evaluate() {
        return x + b;
    }

}
```

### Sealed Class 제약 사항

permits에 명시된 모든 하위 타입은 같은 패키지 또는 같은 모듈(자바 모듈)에 있어야 한다

permits에 명시된 클래스는 컴파일 시 존재해야 한다

하위 클래스는 반드시 sealed, non-sealed, final 중 하나의 키워드를 사용해야 한다


## 신규 타입 (프리뷰): Value Class (Primitive Class)

[프로젝트 발할라 공식 발표 영상](https://www.youtube.com/watch?v=IF9l8fYfSnI)

[프로젝트 발할라 공식 발표 영상 2](https://www.youtube.com/watch?v=a3VRwz4zbdw&t=1014s)

[참고하기 좋은 글](https://jaeyeong951.medium.com/project-valhalla-value-class-092a25aec7a6)

Value Class는 Project Valhalla에서 도입하려는 기능으로 동등성, 불변성, 표현 효율성을 제공하는 **새로운 클래스**이다

프로젝트 발할라는 진행된지 10년이 넘은 프로젝트인만큼 기존 자바/JVM 모델에 많은 변화를 가져올 것으로 보인다

발할라의 슬로건은 다음과 같다

**"Codes like a class, works like an int"**

기존의 자바 언어와 JVM이 객체를 관리하는 방식을 메모리/성능 관점에서 효율적으로 개선하려고 하는 것인데, 기존 모델이 성능적으로 어떤 약점을 가지고 있는지 알아보자

자바는 원래 객체와 primitive 타입을 모두 지원한다. 제네릭이나 컬렉션 프레임워크 등을 사용할 때는 primitive 타입을 사용할 수 없기 때문에 암묵적으로 컴파일러의 오토 박싱이 필요하다

또한 모든 객체는 JVM 힙에 할당되며 각 객체에 접근하기 위해 참조를 유지해야 하고, 객체가 위치한 메모리 주소(고유값, ID)로 동일성을 비교한다

-> 래퍼 클래스로 인한 불필요한 기능(오토박싱, 래퍼 클래스를 지원하는 유틸 기능) + 참조 유지 + 간접 참조 오버헤드로 인한 성능 저하 발생

```java
/*
   p1, p2는 실제 객체를 가리키는 참조 변수(reference)이다
   실제 객체는 JVM의 힙에 할당된다
   JVM은 가비지 컬렉션을 통해 해당 객체의 생명 주기를 관리한다
   
   new Point(10, 5)로 생성된 모든 객체는 서로 다른 객체이며
   == 연산자를 이용하여 메모리 주소(ID)를 기반으로 비교한다 (동일성 비교)     
 */
Point p1 = new Point(10 ,5);
Point p2 = new Point(10 ,5);
```

자바가 데이터 중심 프로그래밍(수치 계산, GPU 프로그래밍 등)으로 진화하는데 기존 동작 방식은 다음과 같은 성능 병목을 유발한다
- **불필요한 힙 사용**: 값만 필요한 객체라도 힙을 사용해야 된다
- **GC 부하 증가**: 짧은 생명 주기를 가진 객체가 대량 생성되면 그만큼 GC에도 부하가 증가한다
- **메모리 비효율**: 작은 값 객체에도 포인터를 거쳐야 된다
- **캐시 비효율**: 객체들은 메모리 상에 산재되어 있어 CPU 캐시 최적화가 어렵다

Value Class 도입은 기존 자바의 객체 관리 모델을 아래와 같이 개선하고자 하는 것이다

Identity(힙에 할당된 메모리 고유값) 중심 -> Value(값 객체가 가진 단순 값) 중심

Heap 객체 기반 -> 인라인화 기반 (연속적인 메모리에 직접 할당)

동일성 (==) -> 동등성 (equals)

간접 참조 기반 (포인터) -> 직접 메모리 접근

GC 부담 -> GC 부하 감소

**Value Class는 JVM 입장에서 참조를 제거한 객체다**

레코드는 이와 달리 참조를 유지한 값 객체로, 힙에 할당되며 Identity를 갖는다

Reference Semantics -> Value Semantics: 객체가 참조가 제거되고 메모리에 직접 할당되어 마치 primitive 타입처럼 동작한다

인스턴스를 메모리에 직접 할당하기 때문에(인라인화) 객체의 주소(ID)가 없어서 Identityless Object라고도 한다

다만 참조가 없기 때문에 synchronized, this, 상속 등의 기능이 일부 제한되지만 메서드, 생성자, static 메서드 등을 이용하면서 래퍼 클래스와 오토 박싱없이 코드를 작성할 수 있다

Value Class 주요 특징
- Identity 없음
- 불변성 (모두 final 필드)
- 인라인화 (JVM이 최적화하여 메모리에 직접 할당)

참고로 값만 비교하기 때문에 서로 다른 Value Class 인스턴스라도 값이 같으면 equals가 true를 반환한다


## 패턴 매칭: instanceof, Switch(구조적 분해, 완전한 패턴 매칭, Guarded 패턴)

[instanceof 예시 코드](./src/test/java/pattern/InstanceOfTest.java)

자바는 **명시적인 타입 검사**에서 **구조적 분해 (Destructuring)** -> **패턴 기반 조건 분기** 방향으로 프로그래밍 모델을 진화시키고 있다

코드의 간결함, 가독성을 대폭 상향시키기 위해 record, sealed class, 가상 스레드, value class 등 새로운 문법과 함께 패턴 매칭을 개선하는 것이다

### instanceof 패턴 매칭

전통적으로 instanceof 연산자 이후 명시적인 형 변환이 필요했기 때문에 동일한 타입 정보를 두 번 반복해야 하므로 중복, 가독성 저하 등의 문제가 있었다

```java
Object obj = "Hello, World!";

// 명시적인 형변환 필요
if (obj instanceof String) {
    String str = (String) obj;
}
```

또한 Record, Sealed Class 같은 새로운 타입의 도입과 함께 패턴 매칭을 통해 타입 검사와 형 변환을 간결하게 처리할 필요가 생겼다

**객체의 내부 구조를 분해해서 추출하는 구조적 분해 (Destructuring)**을 이용하면 자연스럽고 읽기 쉬운 코드를 작성할 수 있다

자바 16 버전부터 instanceof 연산자에 패턴 매칭을 도입하여, 타입 검사와 형 변환을 동시에 처리할 수 있게 되었다

```java
Object obj = "hello instanceof";

// instanceof 연산 후 자동으로 타입 검사와 함께, 변수에 할당해준다
if (obj instanceof String str) {
    // str은 String 타입으로 자동 형변환된다
    System.out.println(str);
}
```

### switch 패턴 매칭, yield 키워드

[switch 예시 코드](./src/test/java/pattern/SwitchTest.java)

기존 switch 문은 case 레이블에 상수만 허용했기 때문에 복잡한 조건 분기를 처리하기 어려웠다

```java
/*
    기존 switch 문법

    switch (value) {
        case condition:
            statement;
            break;
        case condition:
            statement;
            break;
        default:
            statement;
    }
 */

int value = 10;

switch (value) {
    case 1:
        System.out.println("value는 1이다");
        break;
    case 2:
        System.out.println("value는 2이다");
        break;
    default:
        System.out.println("value는 1도 아니고 2도 아니다");
}
```

개선된 switch 문법은 조건문 안에서 instanceof와 변수 바인딩을 자동으로 지원한다

컴파일러가 타입 검사와 형 변환을 자동으로 수행하여 각 case 문에서 명시적인 캐스팅을 하지 않아도 된다

또한 break 키워드가 필요 없으며, case 문에 화살표(->)를 사용하여 간결하게 표현할 수 있다

```java
/*
    개선된 switch 문법

    switch (value) {
        case condition -> statement;
        case condition -> statement;
        default -> statement;
    }

    - switch 안에서 instanceof + 변수 바인딩이 지원된다
    - break 키워드가 필요없다
    - case 문에 화살표(->)를 사용하여 간결하게 표현할 수 있다 (람다 스타일)
 */

Object value = "hello switch";
Object intValue = 10;

// 컴파일러가 자동으로 타입 검사와 형 변환을 수행한다
switch (intValue) {
    case String str -> System.out.println("value는 String 타입: " + str);
    case Integer num -> System.out.println("value는 Integer 타입: " + num);
    default -> System.out.println("value는 다른 타입이다: " + value.getClass().getSimpleName());
}
```

#### yield 키워드

yield 키워드는 switch의 각 case 문에서 값을 반환해야 할 때 사용된다 (return문 대신 yield를 사용)

표현식은 블록없이 값을 반환할 수 있지만 블록을 사용하는 경우에 yield 키워드를 사용하여 값을 반환할 수 있다

```java
String result2 = switch (day) {
    case "MONDAY" -> "월요일";
    case "TUESDAY" -> "화요일";
    
    // 블록에서 값을 반환할 때 yield 키워드를 사용한다
    default -> {
        System.out.println("알 수 없는 요일을 처리한다");
        yield "알 수 없는 요일";
    }
};
```

#### Record/Sealed Class와 패턴 매칭 (구조적 분해, 완전한 패턴 매칭)

레코드의 필드를 switch 문이나 if 문에서 직접 분해하여 사용할 수 있다

기존에는 일일이 명시적인 형변환이 필요했지만 **구조적 분해(Destructuring)**를 이용하면 한 줄에 구조를 분해하고 매칭할 수 있다 

```java
// 예시 레코드 타입
sealed interface Shape permits Circle, Rectangle { }

record Circle(double radius) implements Shape { }
record Rectangle(double width, double height) implements Shape { }


/*
    구조적 분해 패턴 매칭을 사용하여 레코드의 필드를 직접 추출할 수 있다
    기존에는 ((Rectangle) shape).width()와 같이 명시적인 형변환이 필요했다
 */
return switch (shape) {
    case Rectangle(double w, double h) -> w * h;
    case Circle(double r) -> Math.PI * r * r;
};
```

sealed 클래스를 사용하면 컴파일러가 하위 클래스를 모두 알고 있으므로 switch 문에서 default 절을 생략할 수 있다

이를 완전한 패턴 매칭 (Exhaustive Pattern Matching)이라고 한다

```java
/*
    sealed 인터페이스를 사용하여 모든 하위 타입을 완전히 분기할 수 있다
    case 문이 하나라도 빠지면 컴파일 에러가 발생한다
 */
switch (shape) {
    case Circle(double radius) -> System.out.println("원 둘레: " + radius);
    case Rectangle(double width, double height) -> System.out.println("직사각형 너비: " + width + ", 높이: " + height);
}
```

#### Guarded Pattern

구조적 분해에 조건을 추가하고 싶을 때 when 절을 사용하는 것을 Guarded Pattern이라고 한다

특정 조건을 만족하는 경우에만 이 패턴이 매칭되도록 제어할 수 있다

when은 Boolean 조건을 통해 케이스를 필터링할 수 있게 해준다

아래의 경우 Rectangle의 너비와 높이가 동일한 경우에만 정사각형으로 처리하고, 그렇지 않으면 직사각형으로 처리한다

```java
switch (shape) {
    case Rectangle(double w, double h) when w == h -> System.out.println("정사각형");
    case Rectangle(double w, double h) -> System.out.println("직사각형");
    case Circle(double r) -> System.out.println("원");
}
```


## 문자열 지원: Text Block, String Template

[텍스트 블록 예시 코드](./src/test/java/string/TextBlockTest.java)

[문자열 템플릿 예시 코드](./src/test/java/string/StringTemplateTest.java)

텍스트 블록은 자바 15 버전부터 도입된 멀티라인 문자열 리터럴을 표현할 수 있는 기능이다

삼중 따옴표로 시작하고 끝나며, 줄바꿈과 들여쓰기를 그대로 유지할 수 있고 따옴표, 탭을 포함한다

```java
String user =
        """
        {
            "name": "hansanhha",
            "language": java,
        }
        """;

/* 출력 내용
{
    "name": "hansanhha",
    "language": java,
}       
 */
```


## Unnamed Pattern/Variable/Class

자바 21버전부터 프리뷰 기능으로 등장한 unnamed 관련 기능들은 코드의 간결성을 높이고, 일회성 사용을 위한 최소한의 문법을 제공한다

### Unnamed Pattern (_ in Pattern Matching)

패턴 매칭에서 필요없는 값을 무시할 수 있도록 `_` 와일드카드 패턴을 사용할 수 있다

변수에 바인딩하지 않고도 형 검사를 위해 구조를 분해할 수 있다

```java
// _ 와일드카드 패턴을 사용하여 변수에 바인딩하지 않고 형 검사만 수행할 수 있다
if (circle instanceof Circle _) {
    System.out.println("원");
}
```

### Unnamed Variable (_ for unused local variable)

unnamed variable은 지역 변수로 선언은 하지만 사용하지 않을 때 `_`를 사용하여 명시적으로 표시할 수 있다

하나의 블록 내에서 `_`는 한 번만 사용 가능하며 여러 개를 쓰면 컴파일 에러가 발생한다

```java
for (String _ : List.of("a", "b", "c")) {
    // 아무것도 하지 않는다
}


try (var _ = openConnection()) {
    // 연결은 열어야 하는데 변수는 쓰지 않는다
}
```

### Unnamed Classes

아직 정식으로 도입되지 않은 기능으로 Project Amber의 제안 단계에 있다

현재 사용 가능한 형태는 21 버전에 프리뷰로 도입된 Unnamed Classes and Instance Main Methods 기능으로 클래스 선언과 static 선언 없이 코드를 실행할 수 있다

```java
// Hello.java
void main() {
    System.out.println("Hello unnamed class!");
}
```

파일명과 동일한 이름의 클래스가 컴파일 시 자동 생성된다

따라서 컴파일러가 내부적으로 아래처럼 처리하는 것과 동일하다

```java
class Hello {
    void main() {
        System.out.println("Hello, unnamed class!");
    }
}
```

## (논의중) Null-restricted Type

널-제한 타입은 null이 절대 허용되지 않는 참조 타입을 정의할 수 있는 기능이다 ([Value Class](#신규-타입-프리뷰-value-class-primitive-class)와 마찬가지로 프로젝트 발할라의 기능 중 하나)

아래와 같이 문법적 마커 또는 어노테이션을 통해 해당 참조가 절대 null이 아님을 컴파일러 수준에서 명시하고 보장하는 것이다

```java
// null이면 컴파일 에러 발생
String! name = "hansanhha";

// null이 허용되는 일반 String 타입
String name = null;
```

자바의 모든 참조형 변수는 기본적으로 null을 허용하여 여러 가지 문제를 야기한다

NullPointerException: 자주 발생하는 런타임 예외

nullable/non-null 구분 (@NonNull, @Nullable 등)

방어적 코딩/Optional 남발 등

Null-restricted 타입은 언어 문법 차원에서 이러한 문제를 차단하고 컴파일러가 더 엄격하게 널 검사를 할 수 있도록 한다


## `ScopedValue<T>`

[테스트 코드](./src/test/java/valhalla/ScopedValueTest.java)

`ScopedValue<T>`는 불변 값을 코드 실행 범위(Scope) 내에서 안전하게 전달할 수 있는 기능이다

함수 호출 체인 전체에 걸쳐 일정한 값을 안전하게 공유할 수 있는 메커니즘을 제공한다

### 사용법

**`ScopedValue<T>` 생성**

```java
// 새로운 스코프값 `ScopedValue<T>` 생성, 마치 스레드 로컬처럼 값을 전달할 통로(키)를 생성한다
static final ScopedValue<User> USER_SCOPED_VALUE = ScopedValue.newInstance();
```

**값 바인딩과 사용**

```java
/*
    ScopedValue.where(key, value).run(Runnable): 지정한 값으로 컨텍스트를 바인딩하고 실행한다
    
    key.isBound(): 현재 스코프에서 값이 바인딩되어 있는지 확인한다
    key.get(): 현재 바인딩된 값을 반환한다 (스코프 밖에서 접근하면 예외 발생)
    key.orElse(<T>): 값이 바인딩되어 있지 않으면 주어진 값을 반환한다 
    key.orElseThrow(Supplier<Exception>): 값이 바인딩되어 있지 않으면 주어진 예외를 발생시킨다
     
    ScopeValue는 값을 설정하는 메서드가 없다
    대신, 스코프 블록 안에 값을 바인딩하고 실행하는 방식으로 사용한다
*/
ScopedValue.where(USER_SCOPED_VALUE, new User("hansanhha")).run(() -> {
    System.out.println("스코프 바운드: " + USER_SCOPED_VALUE.isBound());
    greet();
});

void greet() {
    User bound = USER_SCOPED_VALUE.orElseThrow(IllegalStateException::new);
    System.out.println("이름: " + bound.name);
}
```

**중첩 사용**

```java
// 가장 가까운 바인딩이 우선 적용된다
ScopedValue.where(USER_SCOPED_VALUE, new User("hansanhha")).run(() -> {

    System.out.println("외부 스코프 접근");
    greet();

    ScopedValue.where(USER_SCOPED_VALUE, new User("new user")).run(() -> {
        System.out.println("내부 스코프 접근");
        greet();
    });

    greet();
});
```

**가상 스레드에서 사용**

```java
try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {

    System.out.println("스레드: " + Thread.currentThread().threadId());

    // 가상 스레드에서 실행할 스코프와 Runnable 설정
    executorService.submit(() ->
            ScopedValue.where(USER_SCOPED_VALUE, new User("hansanhha")).run(() -> {
                System.out.println("가상 스레드에서 스코프 바운드 접근, 스레드: " + Thread.currentThread().threadId());
                greet();
            })
    );
}
```

그런데 이미 자바는 1.2 버전부터 스레드 내에서 값을 공유할 수 있는 ThreadLocal을 제공하고 있었는데 ScopedValue를 도입하려는 이유가 뭘까?  

이를 알기 위해서 ThreadLocal의 단점과 함수형 프로그래밍의 철학과 맞지 않는 점에 대해 알아보고 ScopedValue가 이러한 단점을 어떻게 메꾸는지 알아보자

### 1. 메모리 누수의 위험

ThreadLocal은 값이 스레드의 생명주기와 함께 살아있다

일반적으로 웹 애플리케이션 서버나 스레드 풀 환경에서는 스레드가 재사용되기 때문에 잘못 관리된 ThreadLocal은 사용이 끝나도 GC되지 않고 계속 남아 있는 값을 갖게 된다

-> 심각한 메모리 누수로 이어질 수 있음

```java
ThreadLocal<Map<String, String>> context = new ThreadLocal();
context.set(new HashMap<>());

// context.remove();를 하지 않으면 메모리 누수 발생
```

### 2. 자유로운 변경과 불안정한 스코프

ThreadLocal은 한 번 설정되면 여러 하위 메서드에서 자유롭게 접근할 수 있다

이는 테스트나 비동기 실행 환경에서는 누가 값을 설정했고 언제 사라지는지 명확하지 않아 디버깅을 어렵게 만든다

즉, 값 설정과 참조가 동일한 **스코프**가 아니므로 의도치 않은 공유나 부작용이 일어날 수 있다

스코프란 변수나 상태가 유효한 코드 영역을 의미하는데, 값을 설정한 곳과 값을 사용하는 곳이 명확히 같은 코드 범위 내에 있어야 부작용이 없다

하지만 ThreadLocal은 설정과 참조가 물리적으로 분리되어 있어 누가 값을 설정했는지 불명확할 뿐더러 참조 타이밍도 예측하기 어렵다

```java
public class ThreadLocalLeakExample {
    
    private static final ThreadLocal<Map<String, Integer>> CONTEXT =  new ThreadLocal<>();
    
    public static void main(String[] args) {
        runMainLogic();
        runSubLogic();
    }
    
    static void runMainLogic() {
        // 값 설정 (스코프 A)
        // CONTEXT.remove(); 생략으로 인해 스코프 A에서 설정한 값이 유출된다
        CONTEXT.set("user", 100);
        
        // 값은 다른 메서드에서 참조한다 (스코프 B)
        addUserData();
    }
    
    static void addUserData() {
        // 어디서 설정됐는지 모르고 참조한다
        Map<String, Integer> userMap = CONTEXT.get(); 
        CONTEXT.set("user", userMap.get("user") + 1);
    }
    
    static void runSubLogic() {
        // 이전에 설정한 값이 아직 살아있다
        addUserData();
    }
}
```

### 3. 강한 스레드와의 결합

스레드 풀 환경에서는 스레드 재사용으로 인해 이전 작업의 ThreadLocal 값이 다음 작업에 의도치 않게 유출될 수 있다

### 4. 불변/함수형 프로그래밍과 맞지 않는 철학

불변성은 객체 상태를 한 번 설정하면 변경하지 않는 것을 말한다

객체의 상태를 변경하기 위해선 값을 바꾼 새로운 객체를 만들어야 한다

공유 상태 없이 데이터와 동작을 분리하기 때문에 사이드 이펙트가 발생하지 않는다

만약 공유 상태가 있으면 다른 어디선가 값을 수정하는 바람에 의도하던 대로 동작하지 않을 수 있다

```java
record Book(String name, int price) {}

Book book = new Book("kick-the-java", 10000);

// 가격을 변경하려면 새로운 객체를 만들어야 한다
Book updated = new Book("kick-the-java", 9000);
```

함수형 프로그래밍은 **순수 함수**를 추구하여 명시적 입력과 명시적 출력에 의존한다

순수 함수란 같은 입력은 같은 출력을 반환하는 함수를 말하며 함수 외부 상태를 읽거나 변경하지 않기 때문에 사이드 이펙트가 발생하지 않는다

```java
/*
   순수 함수 예시, add(10, 10)은 항상 20을 반환한다
   오직 매개변수만 이용하며 외부 상태를 수정하거나 변경하지 않는다     
*/
Runnable run = (a, b) -> a + b;
```

이에 반해 스레드 로컬은 스레드를 기준으로 전역 상태 공유 기반이므로 함수 외부에 있는 숨겨진 상태를 사용하거나 변경할 수 있다 

또한 다른 값으로 변경할 수 있기 때문에 함수형 프로그래밍이 강조하는 상태 불변성을 깨뜨린다

```java
ThreadLocal<String> CONTEXT = new ThreadLocal<>();

void run() {
    // 외부 상태에 의존한다 (사이드 이펙트 발생 가능)
    CONTEXT.set("hello"); 
    
    // 함수가 상태를 몰래 참조한다
    System.out.println(get());
}

String get() {
    // 외부 상태를 참조한다 (비순수 함수)
    return CONTEXT.get();
}
```

따라서 스레드 로컬은 OOP나 명령형 프로그래밍에선 유용하지만 함수형 프로그래밍에서 추구하는 불변성, 명시적, 순수성과는 맞지 않으며 컨텍스트 전파, 병렬 실행, 디버깅 측면에서 부작용을 낳을 수 있다

ScopedValue는 다음과 같은 특성을 추구하여 스레드 로컬이 가지는 문제점을 해결하고자 한다

**명시적인 값 설정**, **명시적인 값 참조**, **블록 내부에서만 유지되는 범위**, **값 자동 해제**

명시적 전달, 불변값을 기반으로 사이드 이펙트 발생을 최소화하여 순수 함수를 설계할 수 있게 한다

### 컨텍스트 누수 방지

ScopedValue는 스코프가 끝나면 자동으로 값이 제거되므로 리소스 누수를 방지할 수 있다

```java
ScopedValue<String> CONTEXT = ScopedValue.newInstance();

// 이 스코프가 끝나면 더 이상 CONTEXT.get()을 호출할 수 없다
ScopedValue.where(CONTEXT, "userA").run(() ->{
    System.out.println(CONTEXT.get());
});
```

### 안전한 동시성 (Concurrency safety)

스레드 로컬은 스레드 단위라서 동시 처리와 예측 가능한 동작 보장이 어렵다

반면 ScopedValue는 immutable value를 스코프 단위로 고정해서 병렬 처리나 작업 분할에서도 값 전파와 격리가 명확하다

```java
// ScopedValue는 병렬 작업에서도 격리를 보장한다
ScopedValue.where(CONTEXT, "abc").run(() -> {
    CompletableFuture.runAsync(() -> {
        // 여기서 값에 접근하면 스코프 외부이므로 값이 없거나 예외 발생한다
    });
});
```

### ThreadLocal과의 차이점 요약

| 항목          | ThreadLocal | ScopedValue     |
|-------------|-------------|-----------------|
| 값 수정 여부     | 가능 (가변)     | 불가능 (불변)        |
| GC 처리       | 누수 가능성 존재   | 안전              |
| 호출 스택 전달    | 암시적         | 명시적 Scope안에서만 유효 |
| API 스타일     | 객체 기반 상태 저장 | 함수형, 컨텍스트 기반 전달 |
