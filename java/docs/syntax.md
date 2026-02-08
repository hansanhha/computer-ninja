#### 인덱스



[LTS 핵심 키워드](#lts-핵심-키워드)

[타입: Record](#타입-record)

[타입: Sealed-Class](#타입-sealed-class)

[타입: Value Class (Primitive Class)](#타입-value-class-primitive-class)

[패턴 매칭: instanceof, Switch (구조적 분해, 완전한 패턴 매칭, Guarded 패턴)](#패턴-매칭-instanceof-switch-구조적-분해-완전한-패턴-매칭-guarded-패턴)

[문자열: Text Block, String Template](#문자열-지원-text-block-string-template)

[문법 개선: Unnamed Pattern/Variable/Class](#문법-개선-unnamed-patternvariableclass)

[Null-restricted Type](#null-restricted-type)

## 기본 문법

변수 선언

```java
// [접근 제어자] [static] [final] 자료형 변수명 = 표현식 (값)
// var: 지역 변수 전용 예약어
```

기본 자료형
- 정수: `byte`(1), `short`(2), `int`(4, default), `long`(8)
- 부동소수점: `float`(4), `double`(8, default) 
- 문자: `char`(2) (UTF-16)
- 참/거짓: `boolean`(1~4)

제어문
- 조건: `if`, `switch`, `instanceof`
- 반복: `for`, `while`, `do-while`
- 분기: `break`, `continue`, `return`

문자열
- text block
- string templates

지역 변수 타입 추론: `var`

기타
- unnamed patterns
- unnamed variable
- unnamed classes & main
- primitive class (value class)


## 객체지향 예약어

클래스
- `class`: 필드(상태)와 메서드(행위)를 가질 수 있는 객체
- `interface`: 추상 메서드와 상수 집합만을 가질 수 있는 특수 클래스. 클래스들이 특정 기능을 구현하도록 계약(contract)를 정의한다. `default`, `static` 메서드를 선언할 수 있다 (static 메서드는 오버라이드 불가)
- `enum`: 상수 집합을 정의할 수 있는 클래스 (enum의 모든 요소에 대한 인스턴스는 런타임에 불변형으로 자동 생성된다)
- `@interface`: 코드에 대한 메타데이터를 제공하는 인터페이스, 컴파일러나 런타임 시점에 특별한 처리를 할 수 있도록 정보를 제공한다
- `record`: 불변 데이터 전달(DTO)에 특화된 클래스. 레코드 헤더를 기반으로 필드, 생성자, `equals()`, `hashCode()`, `toString()` 메서드를 컴파일러가 대신 생성해준다
- `sealed class`: 특정 클래스나 인터페이스에게만 상속을 제한적으로 허용하는 클래스
- `value class`: 값으로만 식별되는 클래스. 스택이나 배열에 직접 할당되며 불변이고 null이 될 수 없다

상속/확장
- `abstract`: 추상 클래스 또는 메서드를 정의한다
- `extends`: 특정 클래스를 상속한다
- `implements`: 특정 인터페이스를 구현한다
- `final`: 더 이상 상속/확장할 수 없음을 나타낸다
- `sealed`: 자신을 특정 클래스/인터페이스에게만 상속을 허용하고자 할 때 사용한다
- `permit`: 자신을 상속할 수 있는 클래스/인터페이스를 명시한다
- `non-sealed`: `sealed` 클래스를 상속받는 클래스가 더 이상 봉인되지 않고 어떤 클래스든 상속받을 수 있도록 허용한다

참조
- `this`: 메모리에 할당된 인스턴스 자기 자신을 가리킨다 (현재 실행 중인 객체의 실제 타입을 가리킴)
- `super`: this의 부모 타입을 가리킨다
- `this()`: 현재 실행 중인 객체의 생성자를 가리킨다
- `super()`: 현재 실행 중인 객체의 부모 생성자를 가리킨다

접근 제어자
- `public`: 모든 곳에서 접근할 수 있다
- `protected`: 같은 패키지 또는 상속받은 객체만 접근할 수 있다
- package-private (default): 같은 패키지에서만 접근할 수 있다
- `private`: 객체 내부에서만 접근할 수 있다

## 제네릭

제네릭 타입
- 제네릭을 선언한 타입(클래스, 인터페이스 등)
- `FruitBox<T>`
- `Supplier<R>`
- `Function<T, R>`

타입 매개변수
- 제네릭 클래스나 메서드가 받을 데이터 타입에 대한 매개변수
- int 타입 파라미터에 여러 값(1, 5, 10 등)을 전달할 수 있는 것처럼 '타입' 자체를 매개변수화하여 여러 타입을 전달할 수 있게 한다
- 따라서 제네릭을 사용하면 기본적으로 모든 객체 타입을 받을 수 있는데 이를 와일드카드나 바운드로 범위를 제한할 수 있다
- 일반적으로 사용되는 타입 매개변수는 아래와 같다
- `<T>`: Type
- `<E>`: Element
- `<N>`: Number
- `<R>`: Return
- `<K, V>`: Key, Value
- `<S>, <U>, <V>`: 2nd, 3rd, 4th Types

매개변수화된 타입
- 제네릭 클래스에 실제 데이터 타입이 전달된 상태 (정확히는 해당 인스턴스에 특정 데이터 타입이 전달되어 결정된 상태)
- 실제 지정된 타입은 컴파일 시점에만 유효하다 (`FruitBox<Apple>`은 런타임에 `FruitBox<Object>`가 됨)
- `FruitBox<Apple>`
- `FruitBox<Orange>`
- `Supplier<FruitBox<Apple>>` (`Supplier`와 `Function`은 자바에서 제공하는 함수형 인터페이스임)
- `Function<Integer, Boolean>`

제네릭 제한 (타입 매개변수)
- 제네릭 제한은 제네릭 클래스에 허용되는 데이터 타입을 특정 클래스나 인터페이스의 자손/조상으로 한정한다
- 지정된 타입 이외의 객체가 들어오면 컴파일러가 오류를 발생시켜 런타임 에러를 방지한다
- `<T extends Type>`: `T`에 Type과 Type을 상속받은 자손 클래스만 허용
- 타입 매개변수에는 `super`를 사용하여 하한을 제한할 수 없다 - 와일드카드에서만 허용된다
- 타입 매개변수 하한 제한은 생성자 입장에서 타입이 확정되지 않기 때문에 타입 안정성을 유지할 수 없기 때문

```java
// public class Box<T super Number> 불가능

public class Box<T extends Number> {
    private T item;

    public void setItem(T item) { this.item = item; }
    public T getItem() { return item; }

    // extends를 사용하여 T가 Number를 상속받았음이 보장되므로 제네릭 클래스 내에서 상위 타입 메서드 호출 가능
    public double getDoubleValue() { return item.doubleValue(); }
}

public class Main {
    public static void main(String[] args) {
        Box<Integer> intBox = new Box(); // 가능
        Box<String> strBox = new Box(); // 컴파일 오류 
    }
}
```

제네릭 제한 (와일드카드)
- 타입 매개변수(`<T>` 등)는 특정 타입을 정의하여 코드 내에서 재사용한다 (메서드, 클래스 내에서 `T` 재사용 가능)
- 반면 와일드카드는 알 수 없는 타입을 제한하고 PECS(Producer-Extends, Consumer-Super)을 통해 유연성을 높일 수 있지만 코드 내에서 재사용이 불가능하며 특정 타입으로 고정시킬 수 없다
- `?`: <Object>와 동일 (Unbounded)
- `<? extends T>`: `T`와 하위 클래스만 허용 - 상위 제한(upper bound) 또는 공변 (Producer-Extends)
- `<? super T>`: `T`와 상위 클래스만 허용 - 하위 제한(lower bound) 또는 반공변 (Consumer-Super)
- **`<T extends ...>`는 타입을 정의하는 것**
- **`<? extends T`>는 이미 정의된 타입을 제한해서 사용하는 것**

```java
// T라는 이름의 타입 매개변수 정의
// Number의 하위 타입만 허용한다
// 한 번 정해지면 끝까지 동일한 타입이고, 컴파일러는 결정된 T가 무엇인지 알고 있다
// 클래스, 메서드 내에서 T를 자유롭게 사용할 수 있다
class Box<T extends Number> {
    private T value;

    T get() { return value; }
}

// T는 하나의 고정된 타입이다
Box<Integer> box = new Box<>();
Box<Double> box2 = new Box<>();
```

```java
// 타입 매개변수의 이름이 없다
// 'Number의 하위 타입 중 하나'를 의미한다
// 타입 매개변수와 달리 매번 다른 하위 타입일 수 있고 컴파일러는 구체적인 타입을 모른다
void print(List<? extends Number> list) {

    for (int i = 0; i < list.size(); i++) {
        // Number까지는 확실하나 정확히 무슨 타입인지는 알 수 없다
        Number n = list.get(i);
    }
}
```

```java
// <T extends ...>는 읽고 쓸 수 있다
class Box<T extends Number> {
    void set(T item) { this.item = item; }
    T get() { return item; }
}

// <? extends T>는 읽을 수만 있다
void add(List<? extends Number> list) {
    list.add(10);   // 컴파일 에러
    list.add(null); // 유일하게 가능
}
```

일반적으로 API를 구현할 때는 `<T extends ...>`를, API를 사용할 때는 `<? extends ...>`를 사용한다

```java
// 타입 간 관계가 중요하며 동일한 T를 여러 곳에서 사용하는 상황
public <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) ? a : b;
}
```

```java
// 타입을 유연하게 받되 읽기만 해도 충분한 상황
public void printAll(List<? extends Number> numbers) {
    numbers.forEach(System.out::println);
}
```

PECS(Producer-Extends, Consumer-Super)
- 데이터를 꺼내기만 하면 `extends` (`list.get(i)`)
- 데이터를 넣기만 하면 `super` (`list.set(i)`)

```java
void read(List<? extends Number> list) { }
void write(List<? super Integer> list) { }
```

`<? extends T>`
- `T`는 고정된 특정 타입 매개변수를 말한다
- `?`는 특정 타입으로 제한되지 않는 타입 매개변수이다
- `T`를 모르고 `<? extends T>`도 정확히 모르기 때문에 대부분의 경우 의미가 불분명하다
- 틀린 문법은 아니지만 상황에 맞도록 `<? extends ...>` 또는 `<T extends ...>`를 사용하는 게 코드를 명확하게 작성할 수 있는 방법이다

제네릭 메서드
- `<T> T method(T param)`
- `<T extends Fruit> T getFruit(String name)`

### 예외 처리

**try-catch-finally**

```java
try {
    business logic
} catch (exception) {
    exception handling
} finally {
    release resource
    required processing
}
```

**try-with-resources**

```java
try (resource initialization...) {
    business logic
} catch (exception) {
    exception handling
}
```

**throw, throws**

```java
public void method() throws RuntimeException {
    throw new RuntimeException("unauthorized")
}
```

checked exception
- 컴파일러에 의해 예외 처리가 강제된다
- `IOException`, `FileNotFoundException`, `SQLException`

unchecked exception
- 컴파일러가 예외 처리를 강제하지 않는다 -> `try-catch`, `throws`를 생략할 수 있음
- RuntimeException 클래스를 상속받는다
- 런타임 예외 발생 가능
- `NullPointerException`, `IllegalArgumentException`

### 함수형 프로그래밍

함수형 인터페이스
- 단 하나의 추상 메서드를 정의한 인터페이스로 보통 @FunctionalInterface 어노테이션을 클래스에 적용하여 나타낸다
- `Supplier<T>`: () -> T
- `Consumer<T>`: T -> void
- `Function<T, R>`: T -> R
- `Predicate<T>`: T -> boolean
- `BiFunction<T, U, R>`: (T, U) -> R
- `Runnalbe`: () -> void

**람다 표현식**
- 메서드를 하나의 표현식으로 표현한 것으로 코드의 가독성과 간결함을 높이는 기법이다
- 람다식의 시그니처가 함수형 인터페이스의 추상 메서드 시그니처(함수형 디스크립터)와 일치해야 한다 (컴파일러의 타겟 타입 추론)
- 익명 함수형 인터페이스의 구현체 (단 하나의 추상 메서드를 가진 인터페이스)
- 시그니처: 메서드 이름, 매개변수 목록, 반환 타입
- 1급 객체(first-class citizen): 변수 할당, 메서드 인자 전달, 메서드 반환 값 등으로 사용할 수 있다

**람다식 예시**
- () -> expression: 매개변수를 받지 않고 값을 반환하는 람다식, `Supplier<T>` 만족
- x -> expression: 하나의 매개변수를 받고 값을 반환하는 람다식, `Function<T, R>` 만족
- (x, y) -> expression: 두 개의 매개변수를 받고 값을 반환하는 람다식, `BiFunction<T, U, R>` 만족
- x -> { statements; }: 하나의 매개변수를 받고 값을 반환하지 않는 람다식, `Consumer<T>` 만족
- () -> { statements; }: 매개변수를 받지 않고 값을 반환하지 않는 람다식, `Runnable` 만족
- () -> { return statements; }: 괄호를 감싼 상태에서 값을 반환하려면 return 키워드를 사용해야 한다, `Supplier<T>` 만족

**메서드 참조**
- 이미 존재하는 메서드를 람다식처럼 전달할 수 있는 기법(syntatic sugar)
- 메서드 참조는 `::` 이중 콜론 연산자를 사용하여 특정 람다식을 축약하여 표현할 수 있다
- 다만 모든 람다식을 메서드 참조로 바꿀 수 없다
- 컴파일러가 메서드 참조가 사용된 문맥을 바탕으로 함수형 디스크립터와 일치하는 메서드를 찾아 자동으로 연결한다

**메서드 참조 유형**
- `ClassName::staticMethod`: 클래스 정적 메서드 참조
- `ClassName:instanceMethod`: 클래스 인스턴스 메서드 참조
- `instance::method`: 클래스 인스턴스의 메서드 참조
- `ClassName::new`: 생성자 참조

**`Stream<T>`**
- 데이터 소스(배열, 컬렉션)의 원본 데이터를 변경하지 않고 일련의 연산을 수행할 수 있도록 도와주는 기법
- 중간 연산을 즉시 실행하지 않고 최종 연산이 호출될 때 모든 연산이 한 번에 처리된다
- 스트림이 내부적으로 요소를 반복하여 개발자가 `for`, `while`문을 직접 작성하지 않아도 된다 (Internal Iteration)
- 동작 방식: 생성 -> 중간 연산 -> 최종 연산

**Stream API**
- 생성: `Collection.stream()` 또는 `Arrays.stream()`
- 중간 연산: 스트림의 요소를 변환하거나 필터링하는 연산으로 스트림을 반환하며 지연 처리된다. `filter`, `map`, `flatMap`, `sorted`
- 최종 연산: 스트림을 소비하고 결과를 반환하는 연산으로, 이 연산이 호출되어야 중간 연산들이 실행된다. `count`, `collect`, `forEach`, `reduce`

**`Optional<T>`**
- null 값을 안전하게 다루기 위해 설계된 래퍼 클래스 (잠재적인 `NullPointerException` 처리 강제)
- `Optional<T>`는 값을 가지고 있거나(present) 가지고 있지 않을 수 있다(empty)
- 컬렉션이나 배열처럼 여러 개의 값을 담는 컨테이너가 아니라, 단 하나의 값만 담을 수 있는 단일 값 컨테이너이다
- `isPresent`, `ifPresent`, `orElse`, `orElseGet`, `orElseThrow`. `map`

### 모듈

JPMS (Java Platform Module System): 자바 플랫폼을 모듈화하여 런타임을 경량화할 수 있는 시스템

JPMS 필요성
- 신뢰할 수 없는 구성: 기존에는 애플리케이션에 필요한 JAR 파일이 모두 로드되었는지 확인할 수 없어 런타임 오류가 발생할 수 있다
- 약한 캡슐화: 클래스 경로에서 모든 public 클래스에 접근할 수 있어 리팩토링 시 문제를 일으킬 수 있다
- 성능 오버헤드: 작은 애플리케이션을 개발하더라도 전체 JDK 라이브러리를 포함해야 했다

JDK 8은 약 4천개 이상의 패키지와 2만개가 넘는 클래스가 `rt.jar` 이라는 하나의 파일에 묶여있었는데 JPMS가 도입되면서 `rt.jar`를 약 90여개의 모듈로 분할하여 애플리케이션이 필요한 모듈만 가져다 쓸 수 있게 되었다

모듈 핵심 개념
- 모듈: JPMS의 기본 단위로 패키지, 클래스, 리소스 등을 포함한 논리적 컨테이너 단위이다. 각 모듈은 고유한 이름을 가지며 외부에 제공할 패키지(`export`)와 의존하는 모듈(`require`)을 명시한다
- 모듈 디스크립터: `module-info.java`라는 파일에 작성되는 모듈의 메타데이터이다. 모듈의 이름, 다른 모듈에 대한 의존성, 외부에 공개할 패키지 등을 정의한다
- 모듈 경로: 모듈화된 JAR 파일을 찾는 경로를 의미한다. 기존의 클래스 경로와 달리 모듈 경로는 모듈 간의 의존성 정보를 바탕으로 모듈을 찾고 로딩한다

`module-info.java` 파일은 애플리케이션 최상위 모듈 소스 디렉토리에 단 하나만 작성되어야 한다 (JAR 파일 당 하나)

애플리케이션이 여러 개의 모듈을 가지는 경우, 각 모듈에는 자체적인 `module-info.java` 파일이 존재할 수 있다

`module-info.java` 파일이 없는 JAR 파일은 **자동 모듈(Automatic Module)**로 간주되어 클래스 경로 방식으로 동작한다

클래스 경로 방식은 모듈 경로 방식(**Named Module**)과 달리 모든 JAR 파일과 클래스 파일들이 단일 클래스 경로에 로드되며 모든 public 클래스를 외부에 노출한다

또한 다른 모든 자동 모듈과 클래스 경로에 있는 클래스까지 읽을 수 있다 -> JPMS 모듈 시스템 기능을 이용할 수 없다

의존성 관리
- `requires`: 현재 모듈이 다른 모듈의 공개된 패키지를 사용한다
- `requires static`: 컴파일 타임에만 의존성이 필요함을 선언한다
- `requires transitive`: 전이적 의존성을 선언한다
- `uses`: 현재 모듈이 특정 서비스 인터페이스를 사용함을 선언한다 (런타임에 서비스 로더 메커니즘을 통해 서비스 제공자를 동적으로 찾아서 사용한다)
- `provides with`: 현재 모듈이 `use`로 선언된 서비스 인터페이스의 구현체를 제공함을 선언한다

모듈 공개
- `exports`: 현재 모듈의 특정 패키지를 외부에 공개한다 (다른 모듈은 공개된 패키지의 public 타입에 접근할 수 있다)
- `opens`: 런타임에 리플렉션을 통해 접근할 수 있도록 허용한다

### 기타

synchronized: 스레드 동기화 시 사용 

volatile: 변수의 값을 항상 메인 메모리에서 읽도록 지정

native: 자바에서 외부 코드와 연동할 때 사용 (JNI 등)

transient: 직렬화에서 제외할 필드 지정

strictfp: 부동소수점 계산을 플랫폼에 관계없이 일관되게 처리

assert: 코드의 특정 조건이 참임을 가정하고 테스트하는 데 사용

package, import: 패키지 선언, 의존 패키지 명시

const, goto: 지원하지 않는 예약어


## LTS 핵심 키워드

자바 릴리즈 규칙
- 6개월마다 릴리즈
- 2년마다 한 번씩(4사이클) LTS(Long-Term Support) 버전 발표
- LTS: 8, 11, 17, 21, 25
- [오라클 자바 릴리즈 로드맵](https://www.oracle.com/kr/java/technologies/java-se-support-roadmap.html)

### 8: 함수형 패러다임 도입

람다식, 메서드 참조, 함수형 인터페이스, 스트림/옵셔널 API

인터페이스 defalut, static 메서드

날짜 및 시간 API: java.time 패키지

### 11: 모듈 시스템 도입

JPMS(Java Platform Module System) 모듈 시스템 도입

HttpClient: java.net.http 패키지

String API: String.isBlank, String.lines, String.strip() 등

Collection API 개선: List.of(), Set.of(), Map.of() 등

Flight Recorder/JFR: 저오버헤드 성능/진단 기록 기능

Launch Single-File Programs: 단일 파일로 자바 프로그램 실행 가능

### 17: 타입 추가, 문법 개선

Sealed Class, Record 신규 타입 추가

instanceof 패턴 매칭 개선

Switch 표현식: yield 키워드 도입

Text Block

### 21: 경량 스레드, 패턴 매칭 고도화

가상 스레드: 수천 개의 경량 스레드 생성 가능

Structured Concurrency: 여러 스레드로 실행되는 작업을 구조적으로 정리

switch 패턴 매칭 개선

레코드 필드 패턴 매칭 가능

Sequenced Collection: 순서 보장 컬렉션/맵 도입

String Template

프리뷰/인큐베이터
- Foreign Function & Memory API: C/C++ 네이티브 코드 호출 (JNI 대체)
- Unnamed Patterns and Variables: _ 기호로 미사용 변수 처리
- Scoped Values: 스레드 세이프한 데이터 전달 방식 (ThreadLocal 대체)

### 25: 문법 개선, 동시성 모델 API

문법 개선
- 생성자에서 `super()` `this()` 호출 전 코드 실행 허용
- 컴팩트 소스 파일 및 인스턴스 메인 메서드
- 원시 타입 패턴 매칭(instanceof, switch) 허용
- Unnamed Classes, Variables, Patterns: 코드 작성 간결화

동시성 프로그래밍 패러다임
- Value Class: identity 없는 경량 객체 지원 (val class)
- Scoped Values

성능 최적화
- AOT (Ahead-Of-Time) 메서드 프로파일링: 애플리케이션 웜업 시간을 단축하여 마이크로서비스/서버리스 환경에서 콜드 스타트 문제를 해결하는 데 기여
- @Stable 불변 값 정의
- FFM API 정식화: C 라이브러리 호출, 메모리 접근 표준화
- Class File API: 바이트코드 분석을 위한 공식 API 도입

프리뷰/인큐베이터
- 벡터 API
- Structured Concurrency


## 타입: Record

[전체 예시 코드](./src/main/java/record)

[전체 테스트 코드](./src/test/java/record)

기존에는 POJO(Plain Old Java Object) 클래스를 만들려면 아래와 같이 장황한 코드를 여러 번 반복 작성해야 해서 IDE나 Lombok의 도움을 받았다

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

자바는 간결하게 클래스를 정의하고 타입 안정성을 높이기 위해 16 버전에 Record 타입을 정식으로 도입했다

`record` 키워드를 사용하며 레코드 타입 이름 옆의 헤더(괄호)에 컴포넌트(필드)를 나열하여 정의할 수 있다

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

레코드는 불변 객체로 한 번 생성된 이후로 내부 상태가 변하지 않는다

생성자에서만 값을 설정할 수 있고, 기본적으로 세터를 지원하지 않기 때문에 멀티스레딩 환경에서 안전하다 (동시성에 강하며 버그 발생 가능성이 낮음)

-> 다만 mutable 타입을 필드로 갖는 경우 내부 참조는 바뀔 수 있다

이러한 특성을 활용하여 VO, DTO, 컬렉션 키 등 다양하게 활용할 수 있다

```java
record Post(String title) {}

// 컬렉션 키
Map<Post, List<Comment>> posts;
```

참고로 **레코드는 그 자체로 final이기 때문에 상속할 수 없고, 다른 클래스를 상속할 수도 없다**

대신 인터페이스를 인터페이스를 구현할 수 있다

### Record 생성자

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

클래스에서 중첩 클래스를 정의하는 것처럼 레코드 내부에 다른 레코드를 정의할 수 있다

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


## 타입: Sealed-Class

[전체 예시 코드](./src/main/java/sealed)

봉인된 클래스(Sealed Class)는 자바 17에 정식 도입된 기능으로 상속 가능한 하위 클래스를 명시적으로 제한할 수 있는 기능이다

일반적으로 자바에서 더이상의 상속을 허용하지 않으려면 아래와 같이 클래스에 `final` 키워드를 붙인다

```java
// final 키워드를 사용하여 클래스 상속을 제한할 수 있다
// 다만 모든 클래스의 상속을 제한하게 된다
public final class FinalClass { }
```

무분별한 확장을 방지하면서 final보다 유연하게 상속을 제한하기 위해 Sealed Class 개념이 도입되었다

### Sealed Class/Interface 정의 방법 
- 상속/확장을 제한할 부모에 sealed 키워드를 붙이고, permits 키워드로 허용할 자식을 명시한다 (명시하지 않으면 컴파일 오류가 발생한다)
- permits 키워드에 명시된 클래스는 반드시 해당 sealed 클래스를 상속해야 한다
- 또한 해당 클래스는 sealed, non-sealed, final 중 하나의 키워드를 사용하여 자신의 상속 정책을 정의해야 한다

sealed: 상속을 제한적으로 허용한다 (permits 키워드로 명시된 클래스만 sealed 클래스를 상속할 수 있다)

non-sealed: 모든 클래스의 상속을 허용한다 (해당 클래스의 하위 클래스는 자유롭게 상속할 수 있다, 일반적인 클래스와 동일하다)

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

참고로 Record 타입은 이미 final이므로 sealed 또는 non-sealed를 사용하여 상속을 이어갈 수 없다

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


## 타입: Value Class (Primitive Class)

[프로젝트 발할라 공식 발표 영상](https://www.youtube.com/watch?v=IF9l8fYfSnI)

[프로젝트 발할라 공식 발표 영상 2](https://www.youtube.com/watch?v=a3VRwz4zbdw&t=1014s)

[참고하기 좋은 글](https://jaeyeong951.medium.com/project-valhalla-value-class-092a25aec7a6)

값 타입(Value Class)은 기본형(primitive)처럼 값 그 자체를 다루면서도 클래스처럼 메서드와 필드를 가질 수 있는 데이터 타입이다

기존 객체와 달리 힙 메모리에 별도로 인스턴스가 저장되지 않고 값이 스택이나 배열에 저장된다

자바가 이런 특수한 타입을 도입하려는 이유가 뭘까?

현재 자바는 크게 기본형과 참조형(객체형) 타입을 지원한다

`int`, `long` 등과 같은 기본형 타입은 연산이 빠르고 메모리 효율이 좋지만 객체가 아니어서 메서드를 가질 수 없고 제네릭에 사용할 수 없다

`Integer`, `Long` 등과 같은 참조형 타입은 메서드를 가질 수 있고 객체 지향의 장점, 제네릭을 활용할 수 있지만 힙 메모리에 할당되고 참조를 통해 접근해야 한다 (각 객체는 메모리 주소를 기반으로 동일성을 비교한다)

-> 래퍼 클래스로 인한 불필요한 기능(오토박싱, 래퍼 클래스를 지원하는 유틸 기능) + 참조 유지 + 간접 참조로 인한 성능 오버헤드가 발생한다

자바의 기존 타입 모델은 다음과 같은 성능 병목을 유발한다
- **불필요한 힙 사용**: 값만 필요한 객체라도 힙을 사용해야 된다
- **GC 부하 증가**: 짧은 생명 주기를 가진 객체가 대량 생성되면 그만큼 GC에도 부하가 증가한다
- **메모리 비효율**: 작은 값 객체에도 포인터를 거쳐야 된다
- **캐시 비효율**: 객체들은 메모리 상에 산재되어 있어 CPU 캐시 최적화가 어렵다

자바는 값 타입을 도입하여 자바의 객체 관리 모델을 개선하며 데이터 중심 프로그래밍(수치 계산, GPU 프로그래밍 등)으로 진화하고자 하는 것이다

|기존|값 타입|
|---|---|
|Identity(힙에 할당된 메모리 고유값) 중심|Value(값 객체가 가진 단순 값) 중심|
|Heap 객체 기반(메모리에 분산되어 저장)|인라인화 기반 (연속적인 메모리에 직접 할당)|
|동일성 (ID 비교)|동등성 (값 비교)|
|간접 참조 기반 (포인터)|직접 메모리 접근|

값 타입은 JVM 입장에서 참조를 제거한 객체로 마치 기본형 타입처럼 동작한다 (레코드는 이와 달리 참조를 유지한 값 객체로 힙에 할당되며 Identity를 갖는다)

값 타입의 인스턴스에 대한 참조를 갖지 않고 메모리에 직접 할당하기 때문에(인라인화) 객체의 주소(ID)가 없어서 Identityless Object라고도 한다

다만 참조가 없기 때문에 synchronized, this, 상속 등의 기능이 일부 제한되지만 메서드, 생성자, static 메서드 등을 이용하면서 래퍼 클래스와 오토 박싱없이 코드를 작성할 수 있다

값 타입의 주요 특징
- Identityless
- 불변성 (모두 final 필드)
- 인라인화 (JVM이 최적화하여 메모리에 직접 할당)

값 타입은 값만 비교하기 때문에 서로 다른 인스턴스이더라도 값이 같으면 동등하다고 판단한다


## 패턴 매칭: instanceof, switch (구조적 분해, 완전한 패턴 매칭, Guarded 패턴)

[instanceof 예시 코드](./src/test/java/pattern/InstanceOfTest.java)

자바는 **명시적인 타입 검사**에서 **구조적 분해 (Destructuring)** -> **패턴 기반 조건 분기** 방향으로 프로그래밍 모델을 진화시키고 있다

코드의 간결함, 가독성을 대폭 상향시키기 위해 record, sealed class, 가상 스레드, value class 등 신규 타입 추가, 기능 개선과 함께 패턴 매칭을 개선하는 것이다

### instanceof 패턴 매칭

기존에는 instanceof 연산 이후 명시적인 형 변환이 필요했기 때문에 동일한 타입 정보를 두 번 반복해야 하므로 가독성이 저하됐다

```java
Object obj = "Hello, World!";

// 명시적인 형변환 필요
if (obj instanceof String) {
    String str = (String) obj;
}
```

자바 16 버전부터 instanceof 연산자에서 타입 검사와 형 변환을 동시에 처리하는 기능을 지원한다

```java
Object obj = "hello instanceof";

// 타입 검사 후 자동으로 해당 타입으로 변수에 할당해준다
// str은 String 타입으로 자동 형변환된다
if (obj instanceof String str) {
    System.out.println(str);
}
```

### switch 패턴 매칭, yield

[switch 예시 코드](./src/test/java/pattern/SwitchTest.java)

기존 switch 문은 case 레이블에 상수만 허용했기 때문에 복잡한 조건 분기를 처리하기 어려웠다

```java
int value = 10;

// 기존 switch 문
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

    - switch()에서 instanceof + 변수 바인딩이 지원된다
    - break를 생략할 수 있다
    - case 문에 화살표(->)를 사용하여 간결하게 표현할 수 있다 (람다 스타일)
 */

Object value = "hello switch";
Object intValue = 10;

switch (intValue) {
    case String str -> System.out.println("value는 String 타입: " + str);
    case Integer num -> System.out.println("value는 Integer 타입: " + num);
    default -> System.out.println("value는 다른 타입이다: " + value.getClass().getSimpleName());
}
```

case 절에 단순 표현식이 아닌 블록을 사용해야 하는 경우 `return` 대신 `yield` 키워드를 통해 값을 반환할 수 있다

```java
String result = switch (day) {
    case "MONDAY" -> "월요일";
    case "TUESDAY" -> "화요일";
    
    // 블록에서 값을 반환할 때 yield 키워드를 사용한다
    default -> {
        System.out.println("알 수 없는 요일을 처리한다");
        yield "알 수 없는 요일";
    }
};
```

### Record, Sealed Class 패턴 매칭 (구조적 분해와 완전한 패턴 매칭)

레코드의 필드를 switch 문이나 if 문에서 직접 분해하여 사용할 수 있다

**구조적 분해 (Destructuring)**: 객체의 내부 구조를 분해해서 추출하는 기법

```java
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

이를 **완전한 패턴 매칭 (Exhaustive Pattern Matching)**이라고 한다

```java
/*
    sealed 인터페이스를 사용하면 switch 문에서 모든 하위 타입을 완전히 분기할 수 있다
    case 절에 구현체를 빠뜨리면 컴파일 에러가 일으켜 개발자의 실수를 방지한다
 */
switch (shape) {
    case Circle(double radius) -> System.out.println("원 둘레: " + radius);
    case Rectangle(double width, double height) -> System.out.println("직사각형 너비: " + width + ", 높이: " + height);
}
```

### Guarded Pattern

구조적 분해(객체의 내부 구조를 분해해서 추출하는 기법)에 조건을 추가하고 싶을 때 `when` 절을 사용하는 것을 **Guarded Pattern**이라고 한다

이 패턴을 이용하여 특정 조건을 만족하는 경우에만 매칭되도록 제어할 수 있다

`when`절에서 조건을 만족한 케이스만 필터링한다

아래의 경우 Rectangle 레코드의 너비와 높이가 동일한 경우에만 정사각형으로 처리하고, 그렇지 않으면 직사각형으로 처리한다

```java
switch (shape) {
    case Rectangle(double w, double h) when w == h -> System.out.println("정사각형");
    case Rectangle(double w, double h) -> System.out.println("직사각형");
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


## 문법 개선: Unnamed Pattern/Variable/Class

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

## Null-restricted Type

널-제한 타입은 null이 절대 허용되지 않는 참조 타입을 정의할 수 있는 기능이다 ([Value Class](#타입-value-class-primitive-class)와 마찬가지로 프로젝트 발할라의 기능 중 하나)

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
