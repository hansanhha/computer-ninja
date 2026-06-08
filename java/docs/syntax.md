## 문법

- [LTS 버전별 핵심 기능](#lts-버전별-핵심-기능)
  - [8: 함수형 패러다임 도입](#8-함수형-패러다임-도입)
  - [11: 모듈 시스템 도입](#11-모듈-시스템-도입)
  - [17: 타입 추가, 문법 개선](#17-타입-추가-문법-개선)
  - [21: 경량 스레드, 패턴 매칭 고도화](#21-경량-스레드-패턴-매칭-고도화)
  - [25: 문법 개선, 동시성 모델 API](#25-문법-개선-동시성-모델-api)
- [기본 문법](#기본-문법)
- [객체지향 예약어](#객체지향-예약어)
- [제네릭](#제네릭)
  - [제네릭 타입](#제네릭-타입)
  - [타입 매개변수](#타입-매개변수)
  - [매개변수화된 타입](#매개변수화된-타입)
  - [제네릭 제한 (타입 매개변수)](#제네릭-제한-타입-매개변수)
  - [제네릭 제한 (와일드카드)](#제네릭-제한-와일드카드)
    - [Producer Extends](#producer-extends)
    - [Consumer Super](#consumer-super)
    - [와일드카드는 타입 인자로써만 사용할 수 있다 (`T`와 `?`를 써야 하는 타이밍)](#와일드카드는-타입-인자로써만-사용할-수-있다-t와-를-써야-하는-타이밍)
    - [와일드카드 요약](#와일드카드-요약)
  - [`<? extends T>`](#-extends-t)
  - [제네릭 메서드](#제네릭-메서드)
  - [명시적인 타입 호출](#명시적인-타입-호출)
  - [static 메서드에서 제네릭 타입의 타입 매개변수를 사용하지 못하는 이유](#static-메서드에서-제네릭-타입의-타입-매개변수를-사용하지-못하는-이유)
  - [타입 소거](#타입-소거)
  - [ParameterizedType](#parameterizedtype)
- [예외 처리](#예외-처리)
  - [Suppressed Exception](#suppressed-exception)
  - [예외 처리 문법](#예외-처리-문법)
  - [커스텀 예외](#커스텀-예외)
- [함수형 프로그래밍](#함수형-프로그래밍)
  - [함수형 인터페이스](#함수형-인터페이스)
  - [1급 시민과 람다 표현식](#1급-시민과-람다-표현식)
  - [메서드 참조](#메서드-참조)
  - [스트림](#스트림)
    - [Internal Iteration](#internal-iteration)
    - [Stateless, Non-Interfering](#stateless-non-interfering)
    - [Lazy Evaluation](#lazy-evaluation)
  - [Vertical Processing](#vertical-processing)
    - [`flatMap`과 `mapMulti`](#flatmap과-mapmulti)
    - [Stream Gatherers](#stream-gatherers)
  - [`Optional<T>`](#optionalt)
- [모듈](#모듈)
- [record class](#record-class)
  - [record 생성자](#record-생성자)
    - [1. Canonical Constructor (표준 생성자)](#1-canonical-constructor-표준-생성자)
    - [2. Compact Constructor (축약 생성자)](#2-compact-constructor-축약-생성자)
    - [3. Non-canonical Constructor](#3-non-canonical-constructor)
    - [중첩 record](#중첩-record)
  - [record 직렬화/역직렬화](#record-직렬화역직렬화)
  - [Local record](#local-record)
  - [record equals/hashCode](#record-equalshashcode)
- [Sealed-Class](#sealed-class)
  - [Sealed Class/Interface 정의 방법](#sealed-classinterface-정의-방법)
  - [Sealed Class 제약 사항](#sealed-class-제약-사항)
- [Value Class (Primitive Class)](#value-class-primitive-class)
- [패턴 매칭: instanceof, switch (구조적 분해, 완전한 패턴 매칭, Guarded 패턴)](#패턴-매칭-instanceof-switch-구조적-분해-완전한-패턴-매칭-guarded-패턴)
  - [`instanceof` 패턴 매칭](#instanceof-패턴-매칭)
  - [switch 패턴 매칭, `yield`](#switch-패턴-매칭-yield)
  - [record, Sealed Class 패턴 매칭 (구조적 분해와 완전한 패턴 매칭)](#record-sealed-class-패턴-매칭-구조적-분해와-완전한-패턴-매칭)
  - [Guarded Pattern](#guarded-pattern)
- [문자열 지원: Text Block, String Template](#문자열-지원-text-block-string-template)
- [문법 개선: Unnamed Pattern/Variable/Class](#문법-개선-unnamed-patternvariableclass)
  - [Unnamed Pattern (\_ in Pattern Matching)](#unnamed-pattern-_-in-pattern-matching)
  - [Unnamed Variable (\_ for unused local variable)](#unnamed-variable-_-for-unused-local-variable)
  - [Unnamed Classes](#unnamed-classes)
- [Null-restricted Type](#null-restricted-type)


## LTS 버전별 핵심 기능

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
- `synchronized`: 스레드 동기화 시 사용 
- `volatile`: 변수의 값을 항상 메인 메모리에서 읽도록 지정
- `native`: 자바에서 외부 코드와 연동할 때 사용 (JNI 등)
- `transient`: 직렬화에서 제외할 필드 지정
- `strictfp`: 부동소수점 계산을 플랫폼에 관계없이 일관되게 처리
- `assert`: 코드의 특정 조건이 참임을 가정하고 테스트하는 데 사용
- `package`, `import`: 패키지 선언, 의존 패키지 명시
- `const`, `goto`: 지원하지 않는 예약어


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

### 제네릭 타입

타입 선언 시 타입 매개변수를 가질 수 있는 타입을 제네릭 타입이라고 한다

클래스, 인터페이스, 레코드는 제네릭 타입이 될 수 있다. (익명 클래스 제외)

예시
- `FruitBox<T>`
- `Supplier<R>`
- `Function<T, R>`

Enum과 어노테이션은 자바 언어 스펙에서 타입 매개변수 선언을 지원하지 않는다.

```java
// 컴파일 에러 발생
enum Color<T> {}
@interface Login<T> {}
```

메서드와 생성자는 제네릭 타입은 아니지만 타입 매개변수를 둘 수 있다.

### 타입 매개변수
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

### 매개변수화된 타입
- 제네릭 클래스에 실제 데이터 타입이 전달된 상태 (정확히는 해당 인스턴스에 특정 데이터 타입이 전달되어 결정된 상태)
- 실제 지정된 타입은 컴파일 시점에만 유효하다 (`FruitBox<Apple>`은 런타임에 `FruitBox<Object>`가 됨)
- **클래스 제네릭은 객체 생성 시점에 타입이 결정되며 해당 타입 매개변수는 클래스 전체에 유효하다**
- `FruitBox<Apple>`
- `FruitBox<Orange>`
- `Supplier<FruitBox<Apple>>` (`Supplier`와 `Function`은 자바에서 제공하는 함수형 인터페이스임)
- `Function<Integer, Boolean>`

자바는 매개변수화된 타입끼리의 상속 관계를 인정하지 않는다. (무공변)

따라서 `FruitBox<Apple>`은 `FruitBox<Orange>`와 서로 다른 타입이 된다.

Apple과 Orange의 부모 타입인 `FruitBox<Fruit>`도 서로 다른 타입이 된다.

이건 제네릭 타입 간의 타입 관계를 제한하는 자바의 규칙이다.

다만 제네릭 컨테이너가 담을 수 있는 값의 타입까지는 제한하지 않는다.

타입 매개변수를 부모로 선언하면 자식 타입의 인스턴스를 삽입할 수 있다.

`FruitBox<Fruit>`는 Fruit, Apple, Orange 인스턴스를 모두 받는다.

`FruitBox<Fruit>`는 Fruit 타입의 객체를 저장할 수 있는 클래스라는 의미이다.

Fruit의 자식 타입 인스턴스는 컴파일러 입장에서 `Fruit fruit = new Apple();`과 같은 의미이기 때문에 컴파일 에러가 발생하지 않는다.

### 제네릭 제한 (타입 매개변수)
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

### 제네릭 제한 (와일드카드)

자바의 제네릭은 기본적으로 **공변(convariant)**이 아니다.

공변은 A가 B의 하위 타입이면 제네릭 A도 제네릭 B의 하위 타입으로 취급되는 성질을 말한다.

반대로 A가 B의 상위 타입일 때 제네릭 A가 제네릭 B의 상위 타입으로 취급되는 성질을 반공변이라고 한다.

즉, **공변/반공변은 클래스 간의 관계를 제네릭/배열 타입에도 그대로 유지시키는 성질**이다.

자바의 제네릭은 클래스 관계를 제네릭 타입에 적용시키지 않는 무공변(invariant)이다.

반대로 배열은 공변이으로 부모-자식 관계가 배열에서도 적용된다.

```java
class Animal{}
class Dog extends Animal {}
class Cat extends Animal {}
```

```java
// List<Dog> 제네릭 타입
List<Dog> dogs = new ArrayList<>(); 

// 아래의 코드는 컴파일 에러가 발생한다
List<Animal> animals = dogs;

// 제네릭과 달리 배열은 공변이므로 아래의 코드는 정상적으로 컴파일된다
Dog[] dogs = new Dog[10];
Animal[] animals2 = dogs;

// 컴파일은 되지만 런타임에 ArrayStoreException 예외가 발생한다
animals2[0] = new Cat();
```

상위 타입으로 제네릭 타입을 선언해도 자바 제네릭은 상속 관계와 상관없이 서로 다른 타입이라고 취급하기 때문에 컴파일 에러를 발생시킨다.

자바의 제네릭은 타입 안정성을 위해 이러한 규칙을 강제한다.

다만 제네릭 타입은 제네릭 타입끼리만 베타적이지 제네릭 타입이 담을 수 있는 값의 타입(인스턴스 타입)까지는 제한하지 않는다.

그래서 아래와 같이 `List<Animal>` 제네릭 타입에 여러 개의 인스턴스를 삽입할 수 있다. 

```java
List<Animal> animals = new ArrayList<>();

animals.add(new Cat());
animals.add(new Dog());
```

어느 상황에서는 타입 인자로 여러 개의 타입을 받아야 할 때가 있는데, 이 때 와일드카드를 사용한다.

와일드카드는 `?` 키워드와 타입 인자의 범위를 `extends`와 `super` 키워드를 통해 제한한다.

`<T>`나 `<S, U>`처럼 일반적인 타입 매개변수와 달리 특정 '타입'으로 고정시키지 않는다.

따라서 제한된 타입 범위 내에 속하는 타입이라면 어떤 타입이든 허용하기 때문에 1개 이상의 타입을 저장하거나 조회할 수 있다.

```java
// 매개변수화된 타입은 지정된 타입만 허용하므로 다른 타입을 허용하지 않는다.
List<Integer> list = new ArrayList<>();

// 1.2는 Integer 타입이 아니므로 컴파일 에러가 발생한다
list.add(1.2);
```

```java
// 와일드카드를 사용하여 Animal 타입과 그 자식들을 타입 인자로 받는다
public void printAnimals(List<? extends Animal> animals) {
    for (Animal animal : animals) {
        System.out.println(animal);
    }
}
```

와일드카드를 사용할 때는 보통 **'PECS (Producer-Extends, Consumer-Super)'**라는 패턴을 사용한다.

#### Producer Extends

컬렉션의 관점에서 볼 때 사용자가 데이터를 조회하는 것은 자신의 내부 데이터를 외부로 전달하는 것이므로 데이터를 생산하는 것이다.

그리고 컬렉션에 새로운 데이터를 저장할 때는 외부의 데이터를 내부에 데이터에 가져오는 것이므로 소비한다.

즉, 데이터 조회 -> 데이터 생산 -> `extends` 키워드 사용

데이터 저장 -> 데이터 소비 -> `super` 키워드 사용

데이터를 조회할 때 (컬렉션이 보관하고 있는 데이터를 꺼낼 때) `extends`를 사용하는 이유가 뭘까?

`extends` 키워드는 해당 컬렉션에 저장될 요소 중 가장 최상위 클래스로 범위를 제한한다. (upper)

따라서 데이터를 조회할 때 부모 타입으로 안전하게 받을 수 있다.

```java
// Animal과 그 자식으로 범위를 제한한다
public void printAnimals(List<? extends Animal> animals) {
    for (Animal animal : animals) {
        System.out.println(animal);
    }
}

// 아래의 제네릭 타입을 모두 허용한다
List<Dog>
List<Cat>
List<Animal>

// Dog, Cat, Animal일 수 있음
Animal animal = animals.get(0);
```

다만 `extends`를 사용하면 데이터를 삽입할 수 없다.

`extends`는 여러 자식 타입을 허용하기 때문에 해당 컬렉션에 여러 제네릭 타입을 삽입하게 되어 타입 안정성을 해칠 수 있기 때문이다.

```java
// Animal과 그 자식으로 범위를 제한한다
List<? extends Animal> animals

// 컴파일 에러가 발생한다. 해당 컬렉션에는 List<Cat>와 같이 이미 다른 제네릭 타입이 들어있을 수 있기 때문에 타입 안정성을 유지하기 위함이다
animals.add(new Dog());
```

대신 `null`은 모든 참조 타입의 값이므로 삽입할 수 있다.

```java
animals.add(null);
```

#### Consumer Super

데이터를 소비하는 경우 (컬렉션이 데이터를 자신의 요소로 삽입하는 경우)에는 `super` 키워드를 사용한다.

```java
public void addDogs(List<? super Dog> dogs) {
    dogs.add(new Dog());
}

List<Dog> dogs = new ArrayList<>();
List<Animal> animals = new ArrayList<>();

// List<Dog>와 List<Animal>는 모두 List<? super Dog> 범위에 속하므로 컴파일 에러가 발생하지 않는다
// List<Object>도 List<? super Dog>에 포함된다
addDogs(dogs);
addDogs(animals);
```

`extends`와 반대로 데이터를 삽입(데이터 소비)할 수 있지만 데이터를 읽기(데이터 생산)를 할 수 없다.

`Object` 타입으로 조회하는 경우에만 컴파일이 수행된다.

컴파일러가 `List<Object>`일 수 있다고 판단하기 때문이다.

```java
// super 키워드로 데이터를 조회하면 컴파일 에러가 발생한다
Dog dog = dogs.get(0);

Object obj = dogs.get(0);
```

일반적으로 조회 전용 API를 구현할 때는 `<? extends Type>`을, 저장 전용 API를 사용할 때는 `<? super Type>`을 사용한다

```java
// 데이터 조회만 하는 API
public void process(List<? extends User> users)

// 데이터 삽입만 하는 API
public void save(List<? super User> users)
```

**읽고 쓰기를 모두 해야 하는 API라면 와일드카드를 쓰지 않고 정확한 타입을 사용한다**

`extends`는 쓰기가 안되고 `super`는 읽기가 제한되기 때문이다.

#### 와일드카드는 타입 인자로써만 사용할 수 있다 (`T`와 `?`를 써야 하는 타이밍)

와일드카드는 타입이 구체화되지 않고 재사용할 수 없기 때문에 타입 매개변수처럼 클래스 선언할 때 사용할 수 없다

```java
// 타입 매개변수 T
// 타입에 이름을 붙여서 클래스 내에서 재사용할 수 있다
class Pair<T> {

    // 컴파일러는 first와 second가 동일한 타입이라는 걸 알고 있다
    private T first;
    private T second;
}
```

```java
// 와일드카드는 이름 없는 익명 타입이다
// 컴파일러는 내부에 정확히 어떤 타입이 존재하는지, 모두 동일한 타입인지 알 수 없다
List<?>

// 그래서 타입 매개변수와 달리 아래처럼 클래스 레벨에서 와일드카드를 사용하는 것은 불가능하다
// 컴파일 에러
class Box<?> {}
```

오로지 타입 인자 위치에서만 사용할 수 있다.

```java
class Zoo {
    private List<? extends Animal> animals;
}

public List<? extends Animal> getAnimals() {
    return animals;
}
```

타입 매개변수(`<T>`)를 써야할 때: 타입들 사이의 관계를 표현해야 할 때 (타입을 재사용해야 할 때)

타입 인자(`<?>`)를 써야할 때: 타입들 사이의 관계에 무관할 때

이펙티브 자바에서 타입 매개변수 `<T>`가 메서드 시그니처에서 한 번만 등장하면 와일드카드 사용을 권장한다.


#### 와일드카드 요약
- 타입 매개변수(`<T>` 등)는 특정 타입을 정의하여 코드 내에서 재사용한다 (메서드, 클래스 내에서 `T` 재사용 가능)
- 반면 와일드카드는 알 수 없는 타입을 제한하고 코드 내에서 재사용할 수 없으나 PECS(Producer-Extends, Consumer-Super)을 통해 유연성을 높인다
- 타입 간의 관계를 표현하지 않고 재사용할 수 없으므로 클래스 선언 시 타입 매개변수처럼 사용할 수 없다 `class Box<?> {}` (컴파일 에러)

와일드카드 사용법
- `?`: `<Object>`와 동일 (Unbounded)
- `<? extends T>`: `T`와 하위 클래스만 허용 - 상위 제한(upper bound) 또는 공변 (Producer-Extends)
- `<? super T>`: `T`와 상위 클래스만 허용 - 하위 제한(lower bound) 또는 반공변 (Consumer-Super)
- **`<? extends T>`, `<? super T>`는 클래스 타입 매개변수 또는 제네릭 메서드의 타입 매개변수를 재사용하여 타입을 제한한다** (`T`와 달리 타입 매개변수 자체를 고정시키지 않음)
- **`<? extends Type>`, `<? super Type>`은 자체적으로 상한/하한 타입을 정의하여 타입을 제한한다** (`T`와 달리 타입 매개변수 자체를 고정시키지 않음)
- `extends`: 읽기 가능, 쓰기 불가능
- `super`: 읽기 제한적 가능(`Object`), 쓰기 가능
- 읽고 써야되는 경우: 정확한 타입 사용

### `<? extends T>`
- `T`는 고정된 특정 타입 매개변수를 말한다
- `?`는 특정 타입으로 제한되지 않는 타입 매개변수이다
- `T`를 모르고 `<? extends T>`도 정확히 모르기 때문에 대부분의 경우 의미가 불분명하다
- 틀린 문법은 아니지만 상황에 맞도록 `<? extends Type>` 또는 `<T extends Type>`을 사용하는 게 코드를 명확하게 작성할 수 있는 방법이다

### 제네릭 메서드
- 타입에 선언된 타입 매개변수와 독립된 타입 매개변수를 가진 메서드를 말한다 
- 클래스/인터페이스가 제네릭일 필요가 없다
- **메서드 호출 시점에 타입 매개변수가 결정된다**

```java
// 제네릭 메서드의 타입 매개변수는 접근 제이자와 반환 타입에 위치한다
// 제네릭 메서드 타입 매개변수 <T>의 스코프는 메서드에 한정된다
// 클래스와 동일한 이름이라면 제네릭 메서드의 타입 매개변수가 우선순위를 가진다
public static <T> void print(T value) {
    System.out.println(value);
}
```

```java
// Comparable<T>의 하위 타입만 허용
public static <T extends Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) >= 0 ? a : b;
}
```

### 명시적인 타입 호출
- 컴파일러가 타입 추론을 실패할 때 사용한다
- 스트림이나 람다에서 가끔 타입을 명시한다

```java
// print 메서드의 <T> 타입 매개변수를 <String>으로 명시적으로 지정한다
Utils.<String>print("hello");
```

### static 메서드에서 제네릭 타입의 타입 매개변수를 사용하지 못하는 이유

제네릭 타입의 타입 매개변수는 객체가 생성될 때 확정되지만 static 메서드는 클래스 로딩 시점에 존재한다 

따라서 컴파일러는 런타임의 타입 매개변수가 무엇인지 알 수 없으므로 클래스의 제네릭을 사용할 수 없다

```java
class Box<T> {
    T value;
}

// 이 시점에 box1 인스턴스에 한해서 <T>는 <String>으로 확정된다
Box<String> box1 = new Box<>();
```

대신 메서드 제네릭을 사용하면 클래스 제네릭과 무관하게 호출 시점에 타입이 결정되므로 static에서도 제네릭을 선언할 수 있다

```java
Class Utils {
    static <T> T identity(T value) {
        return value;
    }
}
```

### 타입 소거

타입 소거란 런타임에 타입 정보를 제거하는 것을 말한다

타입 매개변수는 컴파일 시점에 컴파일러가 타입 안정성을 유지하기 위해 타입을 체크하기 위해 사용된다. 

런타임에는 하위 호환성을 위해 이를 제거한다.

제네릭은 자바 5 버전에 도입되었기 때문에 제네릭 타입을 JVM이 지원하도록 수정하면 기존 라이브러리와 바이너리가 오류를 일으킬 수 있다.

그래서 자바는 JVM은 거의 그대로 두고 컴파일러를 수정하는 방향을 선택했다.

```java
// 컴파일 전
List<String>
List<Integer>

// 컴파일 후
List
List
```

상한 제한이 있다면 아래와 같이 치환된다

```java
// 컴파일 전
class Box<T extends Number> {
    T value;
}

// 컴파일 후
class Box {
    Number value;
}
```

형 변환이 필요한 곳에는 컴파일러가 자동으로 캐스팅 코드를 추가한다

```java
// 컴파일 전
List<String> list = new ArrayList<>();
String s = list.get(0);

// 컴파일 후
List list = new ArrayList();
String s = (String) list.get(0);
```

런타임에 제네릭 타입 정보가 제거되므로 아래와 같은 제약이 생긴다.

```java
class Utils<T> {

    // 런타임에는 T가 무엇인지 알 수 없기 때문에 클래스를 생성할 수 없다
    <T> T create() {
        return new T();
    }

    // 런타임에 타입 매개변수의 정보가 지원지기 때문에 비교할 수 없다
    if (obj instanceof T) {
    }

    // 배열은 런타임에 타입 정보를 유지하는데 제네릭은 런타임에 타입 정보가 사라지므로 제네릭 배열을 생성할 수 없다
    T[] arr = new T[size];
}
```

이러한 제약을 다음과 같이 우회할 수 있다

```java
class Utils<T> {


    // 제네릭 메서드는 호출 시점에 타입이 결정되므로 컴파일러가 어떤 타입인지 알 수 있다
    // create(User.class) 호출 시 컴파일러는 T가 User, 반환 타입이 User라는 것을 안다
    // 런타임에는 class가 User.class가 되고 리플렉션으로 User 객체를 생성한다
    <T> T create(Class<T> clazz) throws Exception {

        // 해당 클래스에 선언된 생성자 중 파라미터 없는 생성자를 통해 새 인스턴스를 생성한다 (private/protected 포함)
        return clazz.getDeclaredConstructor().newInstance();
    }

    // 제네릭 배열 대신 컬렉션을 사용한다
    List<T> getList(int size) {
        return new ArrayList<>();
    }
}
```

### ParameterizedType

제네릭은 런타임에 소거되지만 '선언 정보'는 클래스 메타데이터에 남아 있다.

이 메타데이터를 읽기 위한 인터페이스가 `ParameterizedType`이다.

ParmaeterizedType을 통해 `List<String>` `Map<String, Integer>` 등 제네릭이 적용된 타입을 런타임에 표현한다.

```java
public class ParameterizedType_sample {
    
    void main() throws NoSuchFieldException {
        Field field = Box.class.getDeclaredField("items");
        Type type = field.getGenericType();
        
        if (type instanceof ParameterizedType pt) {
            Type raw = pt.getRawType();                 // List
            Type[] args = pt.getActualTypeArguments();  // String

            System.out.println(raw); 
            System.out.println(args[0]);
        }
    }
}

class Box {
    List<String> items;
}
```

**참고로 지역 변수의 제네릭 선언 정보는 클래스 메타데이터에 남지 않는다**

JVM은 지역 변수 제네릭 정보를 저장하지 않기 때문에 디버깅으로 확인해야 한다


## 예외 처리

자바의 모든 예외와 에러 클래스는 `Throwable` 클래스를 상속받는다. -> `Throwable`은 모든 예외의 루트 타입

```text
Throwable
├── Error
└── Exception
    └── RuntimeException
```

`throw`절로 던지거나 `catch`로 잡을 객체, JVM에 의해 발생되는 에러는 반드시 `Throwable` 또는 그 하위 타입이어야 한다.

컴파일러는 예외를 크게 두 종류로 구분한다. 

**Checked Exception**: `RuntimeException`과 `Error`의 하위 클래스가 아닌 예외 클래스

하위 계층에서 체크 예외를 던지는 경우 상위 계층은 반드시 예외를 처리하거나 다시 던져야 한다.

일반적으로 복구 가능한 예외만 체크 예외를 사용한다.

```text
Exception
 ├── IOException
 ├── SQLException
 └── ...
```

**Unchecked Exception**: `RuntimeException` 또는 `Error`의 하위 클래스

언체크 예외는 체크 예외와 달리 컴파일러가 예외 처리를 강제하지 않는다.

전체 계층에 예외를 전파하지 않기 때문에 의미 없는 `try-catch`문을 남발하지 않게 되고 특정 예외에 종속되지 않게 된다.

`Error` 클래스는 애플리케이션이 예외를 처리하지 말아야 할 중대한 문제가 발생했을 때 사용한다 (천재지변 등)

```text
RuntimeException
 ├── NullPointerException
 ├── IllegalArgumentException
 ├── IndexOutOfBoundsException
 └── ...

Error
 ├──OutOfMemoryError
 ├──StackOverflowError
 └── ...
```

`Throwable`은 예외 메시지, 스택 트레이스, 원인 정보를 가지고 있다.

```java
public class Throwable {

    private String detailMessage; // 메시지
    private StackTraceElement[] stackTrace = UNASSIGNED_STACK; // 스택 트레이스
    private Throwable cause = this; // 원인
}
```

예외 메시지(`detailMessage`)는 예외가 발생한 상세한 이유를 나타낼 수 있다.

스택 트레이스(`stackTrace`)는 예외 생성 시점의 호출 스택을 저장한다.

```java
// 예외 발생
throw new RuntimeException();

// 스택 트레이스의 호출 스택
main()
 └─ service()
     └─ repository()

// 스택 트레이스를 출력한다
e.printStackTrace();
```

원인(`cause`)은 현재 예외가 어떤 예외로 인해 발생했다는 인과 관계를 표현한다.

그리고 이러한 인과 관계를 연결하여 예외 체인을 만들 수 있다. (**`chained exception`**)

**예외 체인은 상위 계층의 추상화를 유지하면서 하위 계층의 실패 원인을 보존한다.**

하위 예외에서 내부적으로 예외를 래핑하여 던지는 것이다. (**`wrapped exception`**)

예시) 리포지토리에서 예외가 발생했을 때 `IOException` 대신 `UserNotFoundException`을 던져서 비즈니스 예외를 나타내고 구현 세부사항을 외부로 노출시키지 않음

```java
catch (IOException e) {
    throw new UserNotFoundException(e);
}
```

### Suppressed Exception

Suppresed Exception은 원래 전달되어야 할 예외(Primary Exception)을 보존하기 위해 부가적으로 발생한 예외를 기록하는 메커니즘이다.

자바 7 이전에는 아래와 같은 문제가 있었다.

```java
try {
    throw new RuntimeException("작업 실패");
} finally {
    throw new RuntimeEcxeption("정리 실패");
}
```

호출자에게 `try`문의 예외가 전달되지 않고 `finally`문의 예외가 전달되어 원래 문제였던 `RuntimeException("작업 실패")` 예외가 사라진다.

자바는 주요 원인을 유지하기 위해 Primary Exception과 Secondary Exception을 구분하였다.

Throwable 내부에는 예외 처리(리소스 정리 등) 중 발생한 예외 정보를 관리하는 필드를 별도로 두고 있다.

```java
public class Throwable {
    private List<Throwable> suppressedExceptions = SUPPRESSED_SENTINEL;
}
```

`try-catch-resources` 구문을 사용하면 리소스 해제 과정에서 발생한 예외가 자동으로 suppresed exception 처리된다.

직접 추가하려면 `Throwable`이 제공하는 `addSuppressed(Throwable exception)` API를 사용하면 된다.

```java
RuntimeException main = new RuntimeException("main");
RuntimeException close = new RuntimeException("close");

main.addSuppressed(close);

// main
//  └─ suppressed
//       close
```

### 예외 처리 문법

**throw, throws**

`throw`는 예외를 던지는 구문에서 사용하고, `throws`는 메서드가 특정 예외를 던질 수 있음을 나타낼 때 사용한다.

```java
public void method() throws RuntimeException {
    throw new RuntimeException("unauthorized")
}
```

**try-catch**

파일, 소켓, DB 커넥션과 같이 명시적으로 닫아야 하는 자원을 사용하지 않는 코드에서 예외를 처리할 때 사용한다.

```java
try {
    doSomething();
} catch (Exception e) {
    log.error("failed", e);
}
```

**try-catch-finally**

리소스를 정리해야 하거나 예외 발생과 상관없이 반드시 실행되어야 하는 작업이 `finally` 블록을 사용한다.

`finally` 블록은 예외 발생 여부와 무관하게 `try` 블록의 실행이 끝나면 반드시 실행된다.

```java
try {
    doSomething();
} catch (exception) {
    log.error("failed", e);
} finally {
    conn.close();
    // required processing ...
}
```

**try-with-resources**

`try-with-resources`를 사용하면 컴파일러가 리소스 정리 코드를 자동으로 추가하여 `try-catch-fianlly` 구문으로 인한 장황한 코드를 간단하게 줄일 수 있다.

단, `try` 구문에서 사용되는 리소스 객체는 `AutoClosable`을 구현해야 한다.

```java
try (Connection conn = ...
    PreparedStatement stmt = ...
) {
    doSomething();
} catch (exception e) {
    log.error("failed", e);
}
```

### 커스텀 예외

커스텀 예외는 비즈니스/도메인 의미를 담기 위해 만드는 예외다.

```java
if (user == null) {
    throw new RuntimeException("사용자가 없습니다.");
}

// 로그
// RuntimeException: 사용자가 없습니다.
```

로그를 보고 회원 조회 실패인지, 회원 생성 실패인지, 로그인 실패인지 알기 어렵다.

예외를 명확히 하기 위해 아래와 같이 커스텀 예외를 사용할 수 있다.

```java
if (user == null) {
    throw new UserLoginFailedException("사용자가 없습니다.");
}
// UserLoginFailedException: 사용자가 없습니다.
```

커스텀 예외는 대부분 `RuntimeException` 상속을 권장한다. (언체크 예외는 컴파일러가 예외 처리를 강제하지 않음)

```java
public class UserLoginFailedException extends RuntimeException {
    public UserLoginFailedException(String message) {
        super(message);
    }
}
```

예외 클래스를 만들 때는 4개의 생성자를 제공하는 것을 권장한다.

```java
// 기본 생성자
public BusinessException() {}

// 메시지
public BusinessException(String message) {}

// 원인
public BusinessException(Throwable cause) {}

// 메시지 + 원인
public BusinessException(String message, Throwable cause) {}
```

커스텀 예외의 최상위 부모를 정의하고 에러 코드를 포함하면 일관적으로 비즈니스 예외를 관리할 수 있다.

```java
public enum ErrorCode {
    USER_NOT_FOUND,
    OUT_OF_STOCK,
    INVALID_TOKEN
}

public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }    
}

// BusinessException
//  ├── UserNotFoundException
//  ├── OrderNotFoundException
//  ├── OutOfStockException
//  └── PaymentFailedException
```

**주의사항**

원래 예외를 버리지 않고 유지해야 한다. (예외 체인 유지)

```java
catch (SQLException e) {
    // 원래 예외를 버리지 않는다
    // throw new UserNotFoundException("회원 조회 실패");

    throw new UserNotFoundException("회원 조회 실패", e);
}
```


## 함수형 프로그래밍

### 함수형 인터페이스

단 하나의 추상 메서드를 정의한 인터페이스를 함수형 인터페이스라고 하며 `@FunctionalInterface` 어노테이션으로 표현한다.

자바에서 제공하는 함수형 인터페이스
- `Supplier<T>`: () -> `T`
- `Consumer<T>`: `T` -> `void`
- `Function<T, R>`: `T` -> `R`
- `Predicate<T>`: `T` -> `boolean`
- `BiFunction<T, U, R>`: (`T`, `U`) -> `R`
- `Runnalbe`: () -> `void`

### 1급 시민과 람다 표현식

1급 시민(First-Class Citizen, First-Class Value)이란 아래 조건을 만족한 요소를 말한다.
- 변수에 저장할 수 있다
- 함수(메서드)의 인자로 전달할 수 있다
- 함수(메서드)의 반환값으로 사용할 수 있다
- 런타임에 생성할 수 있다

**1급 시민으로 취급되려면 '값'으로 다룰 수 있어야 한다.**

정수(`int`)는 위의 조건을 모두 만족하므로 1급 시민이다.

```java
int a = 10;  // 변수 저장
foo(a);      // 인자 전달
return a;    // 반환
```

자바의 객체(인스턴스)도 1급 시민이다.

```java
User user = new User();  // 변수 저장
foo(user);               // 인자 전달
return user;             // 반환
```

메서드는 1급 시민으로 취급되지 않는다. -> 값으로 다룰 수 없다

```java
int add(int a, int b) {
    return a + b;
}

var x = add; // 변수 저장 불가능
foo(add);    // 인자 전달 불가능
return add;  // 반환 불가능
```

자바에서 메서드를 값으로 다룰 수 없기 때문에 메서드를 구현한 인스턴스를 값으로 사용한다.

```java
public class Calcaultor {
    int add(int a, int b) {
       return a + b;
    }
}

Calculator cal = new Calculator();
var x = cal;    // 인스턴스 저장
foo(cal);       // 인스턴스 전달
return cal;     // 인스턴스 반환
```

**자바의 람다 표현식은 메서드를 하나의 값으로써 다루는 것처럼 보이게 하는 syntatic sugar다.**

메서드 구현부만 작성하면 컴파일러가 메서드의 시그니처(매개변수 목록, 반환타입)을 확인하여 적절한 함수형 인터페이스를 추론한 뒤, 이를 구현하는 코드로 변환해준다.

따라서 람다 표현식을 사용하려면 메서드 시그니처가 함수형 인터페이스의 시그니처와 동일해야 한다.

```java
var x = a -> a * 2;

// a -> a * 2 코드는 아래의 함수형 인터페이스를 구현한 익명 인스턴스로 변환된다
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}

var x  =
    new Function<>() {
        @Override
        public Integer apply(Integer x) {
            return x * 2;
        }
    };

// Function의 apply 메서드 호출
x.apply();
```

자바는 함수형 인터페이스와 자바만의 람다 표현식을 통해 함수형 언어처럼 코드를 작성할 수 있도록 지원하는 것이고, 완전한 함수형 프로그래밍 언어는 아니다. (객체를 통해 흉내내는 정도)

자바스크립트와 같은 함수형 언어는 함수를 1급 시민으로 취급하여 함수 자체를 값으로 사용할 수 있다.

```javascript
function add(a, b) {
    return a + b;
}

const f = add;  // 함수 변수 저장
foo(add);       // 함수 인수 전달
return add;     // 함수 반환
```

**람다식 예시**
- `() -> expression`: 매개변수를 받지 않고 값을 반환하는 람다식 (`Supplier<T>`로 추론됨)
- `x -> expression`: 하나의 매개변수를 받고 값을 반환하는 람다식 (`Function<T, R>`으로 추론됨)
- `(x, y) -> expression`: 두 개의 매개변수를 받고 값을 반환하는 람다식 (`BiFunction<T, U, R>`으로 추론됨)
- `x -> { statements; }`: 하나의 매개변수를 받고 값을 반환하지 않는 람다식 (`Consumer<T>`로 추론됨)
- `() -> { statements; }`: 매개변수를 받지 않고 값을 반환하지 않는 람다식 (`Runnable`로 추론됨)
- `() -> { return statements; }`: 괄호를 감싼 상태에서 값을 반환하려면 return 키워드를 사용해야 한다 (`Supplier<T>`로 추론됨)

### 메서드 참조

람다 표현식을 직접 작성하지 않고 이미 구현된 메서드를 람다 표현식으로 사용하는 것을 메서드 참조라고 한다.

`String::length`, `System.out::println`과 같이 클래스/인스턴스 이름과 `::` 이중 콜론 연산자, 메서드 이름을 통해 기존에 있는 특정 메서드를 골라서 값처럼 사용하는 것이다.

람다 표현식과 같이 메서드 참조에 사용된 메서드의 시그니처와 함수형 인터페이스의 시그니처가 동일해야 한다.

```java
// 메서드 참조
Function<String, Integer> f = String::length;

// 컴파일러에 의해 변환된 코드
Function<String, Integer> f  =
    new Function<>() {
        @Override
        public int apply(String s) {
            return s.length();
        }
    };
```

**메서드 참조 유형**
- `ClassName::staticMethod`: 클래스 정적 메서드 참조
- `ClassName:instanceMethod`: 클래스 인스턴스 메서드 참조
- `instance::method`: 클래스 인스턴스의 메서드 참조
- `ClassName::new`: 생성자 참조

### 스트림

스트림은 함수형으로 데이터를 처리할 수 있는 계산 파이프라인이다.

즉, 람다와 함수형 인터페이스를 이용해 데이터를 선언적으로 처리하는 것이다.

#### Internal Iteration

스트림에서 반복을 제어하여 개발자는 데이터 처리 로직 작성에만 집중할 수 있다. (데이터 처리 로직과 반복 제어 로직 분리)

```java
// 일반적인 반복문
// 반복, 조건 검사, 결과 처리가 함께 있다
for (User user : users) {
    if (user.isVIP()) {
        user.doSomething();
    }
}

// 스트림을 사용하면 메서드 이름과 람다식을 통해 무엇을 하는지 명확히 표현할 수 있다
users.stream()
     .filter(User::isVIP)
     .forEach(User::doSomething)
```

Internal Interation을 통해 반복 제어를 라이브러리에 위임하고 함수형 스타일을 사용함으로써 부작용을 줄이는 방향을 추구한다.

#### Stateless, Non-Interfering

스트림에 전달할 람다는 Stateless, Non-Interfering 연산이어야 한다.

**외부 상태에 의존하거나 원본 컬렉션을 수정하지 않아야 한다.**

즉, 스트림은 외부 상태를 읽거나 수정하지 않고, 원본 데이터도 변경하지 않는 **순수 함수**를 추구한다.

**Stateless (무상태)**는 연산이 외부 상태 또는 이전 연산 결과에 의존하지 않고 독립적으로 처리하는 것을 말한다.

```java
// Stateful, 외부 상태에 의존하는 연산
List<String> names = new ArrayList<>();

users.stream()
     .forEach(user -> names.add(user.getName()));

// Stateless, 외부 상태에 의존하지 않고 스트림에서 독립적으로 컬렉션을 반환한다
users.stream()
     .map(User::getName)
     .toList();
```

람다식에서 외부 상태를 의존하게 되면 다른 곳에서 외부 상태에 접근하다가 부작용이 발생할 수 있다.

또한 스트림은 병렬 처리도 지원하는데 **여러 스레드가 동시에 접근하게 되므로 데이터 유실, 중복, 경쟁 상태가 발생**할 수 있다.

**스트림의 각 원소는 독립적으로 처리되어야 한다.**

```java
users.stream().map(User::getName)

// User1 처리 결과가 User2 처리에 영향을 주지 않는다
// User1 -> name1
// User2 -> name2
// User3 -> name3
```

**Non-Interfering (무간섭)**는 스트림이 처리하는 데이터 소스를 연산 중에 변경하지 않는 것을 말한다.

스트림은 기본적으로 `데이터 소스 읽기` -> `파이프라인 수행` -> `결과 생성` 처리 과정을 가정한다.

중간에 원본 데이터 소스 자체가 바뀌면 읽고 있던 데이터가 변경되어 예측 불가능한 결과가 만들어질 수 있다.

또한 원본 컬렉션을 건드리면 `ConcurrentModificationException` 예외가 발생할 수 있다.

```java
// Interference, 스트림에서 users 컬렉션을 수정한다
users.stream()
     .forEach(user -> users.remove(user))

// Non-Interference, user 컬렉션을 수정하지 않고 독립적으로 연산을 수행한다
users.stream()
     .filter(User::isVIP)
     .toList();
```

#### Lazy Evaluation

Lazy Evaluation (지연 평가)는 결과가 실제로 필요해질 때까지 계산을 미루는 것을 말한다.

반대로 즉시 평가하는 것은 Eager Evalution이라고 한다.

```java
// 즉시 평가
int result = 1 + 2;

// 지연 평가
계산 계획만 저장
    ↓
실제로 필요할 때 계산
```

아래의 스트림 코드를 실행하면 아무것도 실행되지 않는다.

```java
Stream<String> userVIPStream = 
    users.stream()
         .filter(user -> {
             System.out.println("filter");
             return user.isVIP();
         })
         .map(user -> {
             System.out.println("map");
             return user.getName();
         });
```

중간 연산만 호출하면 스트림은 실행 계획 파이프라인만 만들어두고 실행하지 않는다 (`filter` -> `map`)

스트림은 최종 연산이 호출될 때 계산 파이프라인이 실제로 실행된다.

```java
// filter -> map -> toList 실행
userVIPStream.toList();
```

### Vertical Processing

스트림은 파이프라인을 실행할 때 모든 원소를 처리한 뒤 다음 연산으로 이동하지 않는다.

```text
전체 filter
   ↓
전체 map
   ↓
전체 collect
```

그대신 원소 하나씩 전체 파이프라인을 거쳐서 계산을 처리한다.

이걸 Vertical Processing (수직적 처리)이라고 한다.

```text
User1
 ↓ filter
 ↓ map

User2
 ↓ filter
 ↓ map

결과 2개 확보
 ↓
종료
```

```java
List<Integer> result = Stream.of(1, 2, 3, 4)
                              .filter(n -> {
                                System.out.println("filter: " + n);
                                return n % 2 == 0;
                              })
                              .map(n -> {
                                System.out.println("map: " + n);
                                return n * 10;
                              })
                              .toList();
```

위의 코드는 아래와 같이 filter -> map 순으로 데이터가 출력되지 않는다.

```text
filter:1
filter:2
filter:3
filter:4

map:2
map:4
```

실제로는 지연 평가와 파이프라인 최적화로 인해 아래와 같이 출력된다.

```text
filter: 1
filter: 2
map: 2
filter: 3
filter: 4
map: 4
```

#### `flatMap`과 `mapMulti`

`flatMap`: 스트림의 각 요소를 새로운 스트림으로 변환한 뒤, 이 스트림들을 하나의 스트림으로 평평하게 합친다.

요소를 변환할 때마다 `Arrays.stream()`이나 `Stream.of()` 같은 새로운 스트림 객체를 생성하게 된다.

데이터가 수백만 건처럼 아주 많은 경우 스트림 객체를 생성하고 GC하는 과정에서 성능 저하가 발생할 수 있다.

```java
List<String> phrases = List.of("Hello World", "Java Stream");

List<String> words = phrases.stream()

    // 1. Stream("Hello World", "Java Stream") -> Stream("Hello", "World"), Stream("Java", "Stream")
    // 2. Stream("Hello", "World", "Java", "Stream")
    .flatMap(phrase -> Arrays.stream(phrase.split(" ")))
    .toList();

// words
// [Hello, World, Java, Stream]
```

`mapMulti`: `flatMap`과 동일하게 1:N 변환을 수행하지만 새로운 스트림 객체를 만들지 않고, 소비자(Consumer) 패턴을 사용하여 누적기에 데이터를 직접 넣는다.

요소가 수백만 건 이상으로 변환 오버헤드가 성능에 영향을 줄 때, 최적화해야 할 때 사용한다.

```java
List<String> phrases = List.of("Hello World", "Java Stream");

List<String> words = phrases.stream()
    .<String>mapMulti((phrase, consumer) -> {
        for (String word : phrase.split(" ")) {
            consumer.accept(word); // 스트림을 만들지 않고 소비자에 전달
        }
    })
    .toList();
```

#### Stream Gatherers

기존 `map()`, `filter()` 등의 중간 연산은 앞 뒤 데이터가 서로 독립된 상태(stateless)로만 동작했다.

`gather()`는 중간에서 상태를 기억하면서 데이터를 유동적으로 변환할 수 있게 하는 API이다. (LTS 기준으로 25버전부터 사용 가능)

`gather()`는 `Gatherer<T, A, R>`라는 인터페이스를 인자로 받는다.

`Gaterer<T, A, R>` 메서드
- `Supplier<A> initializer()`: 상태를 보관할 객체를 생성한다.
- `Integrator<A, T, R> integrator()`: 스트림의 요소가 하나씩 들어올 때마다 실행되는 로직이다. (현재 들어온 데이터 `T`와 `initializer`에서 만든 상태 보관함 객체 `A`를 통해 로직을 실행하고 결과 `R`을 다음 스트림으로 내보낼지 결정함)
- `BinaryOperator<A> combiner()`: 병렬 스트림을 사용할 때 여러 스레드에서 쪼개져서 관리되던 상태 보관함 객체를 합친다.
- `BiConsumer<A, Downstream<? super R>> finisher`: 스트림의 모든 요소를 처리하고 스트림이 끝나기 직전에 호출되어 상태 보관함 객체에 남아있던 데이터를 다음 스트림으로 전달한다.

스트림의 연산은 모든 원소를 계산한 후 다음 파이프라인으로 전달하는 게 아니라, 각 원소마다 파이프라인을 태운다. [Vertical Processing](#vertical-processing)

따라서 원소를 묶어서 배치 처리를 할 수 없는데, `gather()`는 스트림 배치 처리를 지원하기 위해 도입됐다.

`gather` 동작 방식 (데이터를 3개씩 묶어서 배치 처리를 하는 경우)
- `initialzer`: 빈 리스트 `[]`를 하나 준비한다 (상태 보관함 객체)
- `integrator`: 데이터가 들어올 때마다 리스트에 넣는다
  - `1` 들어옴 -> `[1]`
  - `2` 들어옴 -> `[1, 2]`
  - `3` 들어옴 -> `[1, 2, 3]` -> 리스트에 3개 요소가 들어옴 -> 다음 스트림으로 `[1, 2, 3]` 전달 후 보관함 객체 클리어
- `finisher`: 모든 데이터가 처리되었는데 보관함에 `[7, 8]`이 남음 -> 마지막으로 `[7, 8]` 전달 후 클리어

보관함 객체에서 다음 스트림으로 N개씩 배치 데이터를 보내주기 때문에 전체 파이프라인의 실행이 종료되면 N개의 컬렉션이 생성된다.

```java
List<Integer> numbers = List.of(1 ,2 ,3, 4, 5, 6, 7);

// windowFixed: 데이터를 특정 개수 단위로 쪼개서 대량 배치 처리
List<List<Integer>> batch = numbers.stream()
    .gather(Gatherers.windowFixed(3))
    .toList();

// batch
// [[1, 2, 3], [4, 5, 6], [7]]
```

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

// windowSliding: 윈도우 슬라이딩 기능
List<List<Integer>> sliding = numbers.stream()
    .gather(Gatherers.windowSliding(3))
    .toList();

// sliding
// [[1, 2, 3], [2, 3, 4], [3, 4, 5]]
```

```java
List<String> words = List.of("A", "B", "C");

// 연속적인 상태 누적
// reducde()는 스트림을 끝내고 최종 결과 1개만 주지만 scan은 과정의 스트림을 유지한다
List<String> runningAppends = words.stream()
    .gather(Gatherers.scan(() -> "", (acc, element) -> acc + element))
    .toList();

// runningAppends
// [A, AB, ABC]
```

```java
// 최대 10개 동시 다우놀드
Stream.of(url1, url2, url3)
    .gather(Gatherers.mapConcurrent(10, url -> downloadWebsite(url)))
    .filter(result -> result.isSuccess())
    .toList();
```


### `Optional<T>`

옵셔널은 값이 있을 수도 있고 없을 수도 있음을 타입 시스템으로 표현하는 컨테이너다.

null을 반환하는 API를 명시적이고 안전하게 만들었다.

```java
// 권장되지 않는 패턴 (obj != null과 크게 다르지 않음)
if (optional.isPresent()) {
    User user = optional.get();
}
```

옵셔널이 값을 가지지 않은 경우 함수형 스타일로 null 처리하는 것을 권장한다.

```java
User user = optional.orElse(defaultUser);
User user = optional.orElseGet(() -> createUser());
User user = optional.orElseThrow(UserNotFoundException::new);
```


## 모듈

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


## record class

기존에는 POJO(Plain Old Java Object) 클래스를 만들려면 아래와 같이 장황한 코드를 여러 번 반복 작성해야 해서 IDE나 Lombok의 도움을 받았다.

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

자바는 간결하게 클래스를 정의하고 타입 안정성을 높이기 위해 16 버전에 Record 타입을 정식으로 도입했다.

`record` 키워드를 사용하며 레코드 타입 이름 옆의 헤더(괄호)에 컴포넌트(필드)를 나열하여 정의할 수 있다.

레코드는 컴포넌트만 정의하면 자바 컴파일러가 암묵적으로 필드, 생성자, 게터 등을 생성해준다.

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

레코드를 정의하면 다음과 같은 기능을 자동으로 제공한다.
- private final 필드 (레코드 헤더에 정의된 컴포넌트)
- public 생성자: `public Person(String name, int age)`
- 각 필드에 대한 게터 (메서드 이름은 필드명과 동일함)
- equals, hashCode, toString 메서드

레코드는 불변 객체로 한 번 생성된 이후로 내부 상태가 변하지 않는다. (다만 mutable 타입을 필드로 갖는 경우 내부 요소가 바뀔 수 있음)

생성자에서만 값을 설정할 수 있고, 기본적으로 세터를 지원하지 않기 때문에 멀티스레딩 환경에서 안전하다.

이러한 특성을 활용하여 VO, DTO, 컬렉션 키 등 다양하게 활용할 수 있다.

```java
record Post(String title) {}

// 컬렉션 키
Map<Post, List<Comment>> posts;
```

참고로 **레코드는 그 자체로 final이기 때문에 상속할 수 없고, 다른 클래스를 상속할 수도 없다.**

대신 인터페이스를 인터페이스를 구현할 수 있다.

### record 생성자

레코드는 세 가지의 생성자를 지원하며 각 생성자는 레코드의 접근 제어자보다 더 제한적인 접근 제어자를 가질 수 없다.

#### 1. Canonical Constructor (표준 생성자)

표준 생성자는 레코드 헤더에 정의된 컴포넌트(필드)와 동일한 이름과 타입을 갖는 생성자이다.

암묵적으로 생성되지만 아래와 같이 유효성 검사 등의 목적으로 명시적으로 정의하여 오버라이드할 수 있다.

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

Canonical Constructor과 동일하지만 파라미터 선언 없이 블록만 작성할 수 있다.

Canonical Constructor와 Compact Constructor는 서로 동시에 정의될 수 없고 둘 중 하나만 정의할 수 있다.

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

비표준 생성자는 필드 개수나 이름이 레코드 헤더와 다를 수 있으며, 여러 개를 정의할 수 있다.

다만 반드시 this()를 호출하여 Canonical/Compactor Constructor를 반드시 호출해야 한다.

this() 호출 후 로직을 추가하여 유효성 검사나 기본값 설정 등을 할 수 있다.

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

#### 중첩 record

클래스에서 중첩 클래스를 정의하는 것처럼 레코드 내부에 다른 레코드를 정의할 수 있다.

중첩 레코드를 적절히 사용하면 불변성을 자연스럽게 유지하고 도메인 단위의 모델을 계층적으로 구성할 수 있으며 JSON 직렬화/역직렬화 시에도 유용하다.

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

### record 직렬화/역직렬화

레코드는 Jackson 2.12.0 이상에서 직렬화가 지원된다. (Gson도 사용 가능)

레코드를 JSON으로 직렬화할 때는 레코드의 필드 이름이 JSON 키로 사용되며, 반대로 역직렬화 시 생성자를 기반으로 하여 JSON 키가 레코드의 필드 이름에 매칭된다.

따라서 레코드의 필드 이름과 JSON 키가 일치해야 한다.

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

필드명과 JSON 키를 다르게 관리하고 싶은 경우 `@JsonCreator`와 `@JsonProperty` 어노테이션을 사용하여 서로 매핑할 수 있다.

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

### Local record

레코드는 로컬 클래스처럼 메서드 내부에서 임시적으로 record 타입을 정의할 수 있다.

DTO처럼 사용할 수 있으며 메서드 내부 로직에서만 쓰이므로 캡슐화가 좋으며 스트림 처리에 활용할 수 있다.

로컬 레코드는 public 등 접근 제어자를 지정할 수 없고, 메서드 내부에서만 사용 가능하다.

```java
@ParameterizedTest
@ValueSource(strings = {"java", "kotlin", "python"})
void 레코드는_메서드_내부에_정의할_수_있다(String word) {
    
    // 메서드 내부에 레코드 타입 정의
    record WordLength(String word, int length) {}

    WordLength wordLength = new WordLength(word, word.length());
}
```

### record equals/hashCode

레코드가 자동으로 구현하는 `equals()`는 컴포넌트(필드)의 **모든 값이 같으면** 동일하다고 판단한다.

`hashCode()`는 모든 필드를 기반으로 계산한다. (`Objects.hash` 메서드 사용)


## Sealed-Class

봉인된 클래스(Sealed Class)는 자바 17에 정식 도입된 기능으로 상속 가능한 하위 클래스를 명시적으로 제한할 수 있는 기능이다.

일반적으로 자바에서 더이상의 상속을 허용하지 않으려면 아래와 같이 클래스에 `final` 키워드를 붙인다.

```java
// final 키워드를 사용하여 클래스 상속을 제한할 수 있다
// 다만 모든 클래스의 상속을 제한하게 된다
public final class FinalClass { }
```

무분별한 확장을 방지하면서 final보다 유연하게 상속을 제한하기 위해 Sealed Class 개념이 도입되었다.

### Sealed Class/Interface 정의 방법 
- 상속/확장을 제한할 부모에 sealed 키워드를 붙이고, permits 키워드로 허용할 자식을 명시한다. (명시하지 않으면 컴파일 오류가 발생한다)
- permits 키워드에 명시된 클래스는 반드시 해당 sealed 클래스를 상속해야 한다.
- 또한 해당 클래스는 sealed, non-sealed, final 중 하나의 키워드를 사용하여 자신의 상속 정책을 정의해야 한다.

`sealed`: 상속을 제한적으로 허용한다. (permits 키워드로 명시된 클래스만 sealed 클래스를 상속할 수 있다)

`non-sealed`: 모든 클래스의 상속을 허용한다. (해당 클래스의 하위 클래스는 자유롭게 상속할 수 있다, 일반적인 클래스와 동일하다)

`final`: 해당 클래스의 상속을 완전히 제한한다.

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

참고로 Record 타입은 이미 `final`이므로 `sealed` 또는 `non-sealed`를 사용하여 상속을 이어갈 수 없다.

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

`permits`에 명시된 모든 하위 타입은 같은 패키지 또는 같은 모듈(자바 모듈)에 있어야 한다.

`permits`에 명시된 클래스는 컴파일 시 존재해야 한다.

`permits`에 명시된 클래스는 반드시 `sealed`, `non-sealed`, `final` 중 하나의 키워드를 사용해야 한다.


## Value Class (Primitive Class)

[프로젝트 발할라 공식 발표 영상](https://www.youtube.com/watch?v=IF9l8fYfSnI)

[프로젝트 발할라 공식 발표 영상 2](https://www.youtube.com/watch?v=a3VRwz4zbdw&t=1014s)

[참고하기 좋은 글](https://jaeyeong951.medium.com/project-valhalla-value-class-092a25aec7a6)

값 타입(Value Class)은 기본형(primitive)처럼 값 그 자체를 다루면서도 클래스처럼 메서드와 필드를 가질 수 있는 데이터 타입이다.

기존 객체와 달리 힙 메모리에 별도로 인스턴스가 저장되지 않고 값이 스택이나 배열에 저장된다.

자바가 이런 특수한 타입을 도입하려는 이유가 뭘까?

현재 자바의 타입은 크게 기본형과 참조형(객체형)으로 나뉜다.

`int`, `long` 등과 같은 기본형 타입은 연산이 빠르고 메모리 효율이 좋지만 객체가 아니어서 메서드를 가질 수 없고 제네릭 타입 매개변수로 지정할 수 없다.

`Integer`, `Long` 등과 같은 참조형 타입은 메서드를 가질 수 있고 객체 지향의 장점, 제네릭을 활용할 수 있지만 힙 메모리에 할당되고 참조를 통해 접근해야 한다. (각 객체는 메모리 주소를 기반으로 동일성을 비교한다)

이들은 래퍼 클래스이기 때문에 오토박싱/언박싱 및 유틸 기능 + 참조 유지 + 간접 참조로 인한 성능 오버헤드가 발생한다.

자바의 기존 타입 모델은 다음과 같은 성능 병목을 유발한다
- **불필요한 힙 사용**: 값만 필요한 객체라도 힙을 사용해야 된다.
- **GC 부하 증가**: 짧은 생명 주기를 가진 객체가 대량 생성되면 그만큼 GC에도 부하가 증가한다.
- **메모리 비효율**: 작은 값 객체에도 포인터를 거쳐야 된다.
- **캐시 비효율**: 객체들은 메모리 상에 산재되어 있어 CPU 캐시 최적화가 어렵다.

자바는 값 타입을 도입하여 자바의 객체 관리 모델을 개선하며 데이터 중심 프로그래밍(수치 계산, GPU 프로그래밍 등)으로 진화하고자 하는 것이다.

|객체|값 타입|
|---|---|
|Identity(힙에 할당된 메모리 고유값) 중심|Value(값 객체가 가진 단순 값) 중심|
|Heap 객체 기반(메모리에 분산되어 저장)|인라인화 기반 (연속적인 메모리에 직접 할당)|
|동일성 (ID 비교)|동등성 (값 비교)|
|간접 참조 기반 (포인터)|직접 메모리 접근|

값 타입은 JVM 입장에서 참조를 제거한 객체로 마치 기본형 타입처럼 동작한다. (레코드는 이와 달리 참조를 유지한 값 객체로 힙에 할당되며 Identity를 갖는다)

값 타입의 인스턴스에 대한 참조를 갖지 않고 메모리에 직접 할당하기 때문에(인라인화) 객체의 주소(ID)가 없어서 Identityless Object라고도 한다.

다만 참조가 없기 때문에 `synchronized`, `this`, 상속 등의 기능이 일부 제한되지만 메서드, 생성자, static 메서드 등을 이용하면서 래퍼 클래스와 오토 박싱없이 코드를 작성할 수 있다.

값 타입의 주요 특징
- Identityless
- 불변성 (모두 final 필드)
- 인라인화 (JVM이 최적화하여 메모리에 직접 할당)

값 타입은 값만 비교하기 때문에 서로 다른 인스턴스이더라도 값이 같으면 동등하다고 판단한다.


## 패턴 매칭: instanceof, switch (구조적 분해, 완전한 패턴 매칭, Guarded 패턴)

자바는 **명시적인 타입 검사**에서 **구조적 분해 (Destructuring)** -> **패턴 기반 조건 분기** 방향으로 프로그래밍 모델을 진화시키고 있다.

코드의 간결함과 가독성을 대폭 상향시키기 위해 record, sealed class, 가상 스레드, value class 등 신규 기능을 추가하고 패턴 매칭을 개선하는 것이다.

### `instanceof` 패턴 매칭

기존에는 `instanceof` 연산 이후 명시적인 형 변환이 필요했기 때문에 동일한 타입 정보를 두 번 반복해야 하므로 가독성이 저하됐다.

```java
Object obj = "Hello, World!";

// 명시적인 형변환 필요
if (obj instanceof String) {
    String str = (String) obj;
}
```

자바 16 버전부터 `instanceof` 연산자에서 타입 검사와 형 변환을 동시에 처리하는 기능을 지원한다.

```java
Object obj = "hello instanceof";

// 타입 검사 후 자동으로 해당 타입으로 변수에 할당해준다
// str은 String 타입으로 자동 형변환된다
if (obj instanceof String str) {
    System.out.println(str);
}
```

### switch 패턴 매칭, `yield`

기존 `switch` 문은 `case` 레이블에 상수만 허용했기 때문에 복잡한 조건 분기를 처리하기 어려웠다.

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

개선된 `switch` 문법은 `case` 레이블에 다양한 타입 매칭을 넣을 수 있고 변수 바인딩을 지원한다. (컴파일러가 타입 검사와 형 변환/변수 바인딩을 자동으로 수행함)

또한 break 키워드가 필요 없으며, case 문에 화살표(->)를 사용하여 간결하게 표현할 수 있다.

```java
Object value = "hello switch";
Object intValue = 10;

switch (intValue) {
    case String str -> System.out.println("value는 String 타입: " + str);
    case Integer num -> System.out.println("value는 Integer 타입: " + num);
    default -> System.out.println("value는 다른 타입이다: " + value.getClass().getSimpleName());
}
```

case 절에 단순 표현식이 아닌 블록을 사용하면서 값을 반환해야 하는 경우 `yield` 키워드를 사용한다.

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

### record, Sealed Class 패턴 매칭 (구조적 분해와 완전한 패턴 매칭)

레코드의 필드를 `switch` 문이나 `if` 문에서 직접 분해하여 사용할 수 있다.

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

sealed 클래스를 사용하면 컴파일러가 하위 클래스를 모두 알고 있으므로 switch 문에서 default 절을 생략할 수 있다.

이를 **완전한 패턴 매칭 (Exhaustive Pattern Matching)**이라고 한다.

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

구조적 분해(객체의 내부 구조를 분해해서 추출하는 기법)에 조건을 추가하고 싶을 때 `when` 절을 사용하는 것을 **Guarded Pattern**이라고 한다.

이 패턴을 이용하여 특정 조건을 만족하는 경우에만 매칭되도록 제어할 수 있다.

`when`절에서 조건을 만족한 케이스만 필터링한다.

아래의 경우 Rectangle 레코드의 너비와 높이가 동일한 경우에만 정사각형으로 처리하고, 그렇지 않으면 직사각형으로 처리한다.

```java
switch (shape) {
    case Rectangle(double w, double h) when w == h -> System.out.println("정사각형");
    case Rectangle(double w, double h) -> System.out.println("직사각형");
}
```


## 문자열 지원: Text Block, String Template

텍스트 블록은 자바 15 버전부터 도입된 멀티라인 문자열 리터럴을 표현할 수 있는 기능이다.

삼중 따옴표로 시작하고 끝나며, 줄바꿈과 들여쓰기를 그대로 유지할 수 있고 따옴표, 탭을 포함한다.

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

자바 21버전부터 프리뷰 기능으로 등장한 unnamed 관련 기능들은 코드의 간결성을 높이고, 일회성 사용을 위한 최소한의 문법을 제공한다.

### Unnamed Pattern (_ in Pattern Matching)

패턴 매칭에서 필요없는 값을 무시할 수 있도록 `_` 와일드카드 패턴을 사용할 수 있다.

변수에 바인딩하지 않고도 형 검사를 위해 구조를 분해할 수 있다.

```java
// _ 와일드카드 패턴을 사용하여 변수에 바인딩하지 않고 형 검사만 수행할 수 있다
if (circle instanceof Circle _) {
    System.out.println("원");
}
```

### Unnamed Variable (_ for unused local variable)

unnamed variable은 지역 변수로 선언은 하지만 사용하지 않을 때 `_`를 사용하여 명시적으로 표시할 수 있다.

하나의 블록 내에서 `_`는 한 번만 사용 가능하며 여러 번 쓰면 컴파일 에러가 발생한다.

```java
for (String _ : List.of("a", "b", "c")) {
    // 아무것도 하지 않는다
}


try (var _ = openConnection()) {
    // 연결은 열어야 하는데 변수는 쓰지 않는다
}
```

### Unnamed Classes

```java
// Hello.java
void main() {
    IO.println("Hello unnamed class!");
}

// java Hello.java
// "Hello unnamed class!"
```

파일명과 동일한 이름의 클래스가 컴파일 시 자동 생성된다.

따라서 컴파일러가 내부적으로 아래처럼 처리하는 것과 동일하다.

```java
class Hello {
    void main() {
        System.out.println("Hello, unnamed class!");
    }
}
```

## Null-restricted Type

널-제한 타입은 null이 절대 허용되지 않는 참조 타입을 정의할 수 있는 기능이다.

아래와 같이 문법적 마커 또는 어노테이션을 통해 해당 참조가 절대 null이 아님을 컴파일러 수준에서 명시하고 보장하는 것이다.

```java
// null이면 컴파일 에러 발생
String! name = "hansanhha";

// null이 허용되는 일반 String 타입
String name = null;
```

자바의 모든 참조형 변수는 기본적으로 null을 허용하여 여러 가지 문제를 야기한다.

`NullPointerException`: 자주 발생하는 런타임 예외

nullable/non-null 구분 (`@NonNull`, `@Nullable` 등)

방어적 코딩/Optional 남발 등

Null-restricted 타입은 언어 문법 차원에서 이러한 문제를 차단하고 컴파일러가 더 엄격하게 널 검사를 할 수 있도록 한다.
