## 타입스크립트
- [타입스크립트](#타입스크립트)
- [정적 타입 시스템](#정적-타입-시스템)
- [타입 종류와 키워드](#타입-종류와-키워드)
- [객체지향](#객체지향)
- [함수 문법](#함수-문법)
  - [함수 선언문](#함수-선언문)
  - [함수 표현식](#함수-표현식)
  - [화살표 함수](#화살표-함수)
  - [반환값 타입 생략](#반환값-타입-생략)
  - [반환값이 없는 함수 `void`](#반환값이-없는-함수-void)
  - [선택적 매개변수](#선택적-매개변수)
  - [기본값 매개변수](#기본값-매개변수)
- [인터페이스](#인터페이스)
  - [인터페이스 vs 타입](#인터페이스-vs-타입)
- [클래스](#클래스)
  - [인터페이스 vs 추상 클래스](#인터페이스-vs-추상-클래스)
- [제네릭](#제네릭)
- [Decorator](#decorator)
- [Advanced Type](#advanced-type)
- [Utility Type](#utility-type)
- [Type Inferrence](#type-inferrence)
- [Type Narrowing](#type-narrowing)
- [Discriminated Union](#discriminated-union)
- [Mapped Types](#mapped-types)
- [Conditional Types](#conditional-types)
- [Template Literal Types](#template-literal-types)
- [`.d.ts`](#dts)
- [타입스크립트 실행](#타입스크립트-실행)



## 정적 타입 시스템

타입스크립트는 자바스크립트 코드에 **타입 시스템(정적 타입 기능)**을 추가한 언어로, 자바스크립트의 런타임 에러를 방지하기 위해 만들어졌다.

타입스크립트의 타입은 컴파일 타임에만 존재하며 타입스크립트의 컴파일러를 통해 자바스크립트 코드로 변환(트랜스파일)하면 타입 정보는 모두 사라진다.

```typescript
let age: number = 30;
let username: string = "Alice";
let isAdmin: boolean = true;
```


## 타입 종류와 키워드

원시 타입: `number`, `string`, `boolean`, `null`, `undefined`, `bigint`, `symbol`

배열 타입: `number[]`, `Array<number>` (`primitive[]`, `Array<primitive>`)

튜플(고정된 길이와 순서를 가진 배열): `const user: [number, string] = [1, "hansanhha"];`

유니온 타입(여러 타입 허용): `let value: string | number;`

타입 앨리어스(타입에 이름 부여)

```typescript
type userId = number;

type User = {
    id: userId;
    name: string;
};

const hansanhha: User {
    id: 1,
    name: "hansanhha",
}
```

리터럴 타입(특정 값만 허용)

```typescript
type Direction =
    | "left"
    | "right"
    | "up"
    | "down";

let dir: Direction = "left";
```

모든 타입 허용: `any`, `unknown`

`any`는 타입 검사를 완전히 포기한다.

`unknown`은 안전하게 사용하도록 검사를 강제한다.

```typescript
// 어떤 값이든 할당할 수 있고 해당 값에 마음대로 접근하거나 메서드를 호출할 수 있다
let value: any = 10;
value = "string"
```

```typescript
// 모든 값을 받을 수는 있지만, 그 값을 사용하지 전에 어떤 타입인지 확인해야 한다
let value: unknown = 10;

// string 타입인 경우에만 메서드 호출
if (typeof value === "string") {
    value.toUpperCase();
}
```

선택적 연산자 (`?`)

객체의 프로퍼티나 함수의 매개변수를 '있어도 되고 없어도 되는' 선택적 상태로 만들 때 사용한다.

프로퍼티 뒤에 `?`를 붙이면 타입스크립트는 내부적으로 해당 타입에 `| undefined`를 자동으로 추가한다.

```typescript
interface UserProfile {
    id: number;
    name: string;
    email?: string;  // email은 string | undefined 타입이 된다
}

const user1: UserProfile = { id: 1, name: "hansanhha", email: "example@example.com"};
const user2: UserProfile = { id: 2, name: "guest" };
```

`infer` 키워드

`infer`는 키워드는 조건부 타입(`T extends U ? X : Y`)의 조건식 안에서 타입을 추론을 하고 그 타입을 임시 변수에 저장하라고 컴파일러에게 명령한다.

런타임에 어떤 타입이 들어올지 미리 알 수 없을 때 타입스크립트가 유연하게 해당 위치의 타입을 변수처럼 사용할 수 있게 해준다.

항상 `extends` 조건절 뒤에서만 사용할 수 있다.

```typescript
type GetReturnType<T> = T extends (...args: any[]) => infer R ? R : never;
```

```typescript
// 1. 테스트용 함수 정의
function getUserName() {
  return "Alice"; // 반환 타입이 string으로 추론된다
}

function getAge() {
  return 30;      // 반환 타입이 number로 추론된다
}

// 2. GetReturnType 사용

// 케이스 A: string을 반환하는 함수를 넣었을 때
type A = GetReturnType<typeof getUserName>; 
/*
  작동 과정:
  1. typeof getUserName은 `() => string`
  2. `() => string`은 함수 모양(`(...args: any[]) => infer R`) 부합한다
  3. 이 함수의 실제 반환 타입은 `string`이므로 `R`은 `string`이 된다
  4. 결과: 최종적으로 `A` 타입은 `string`이 된다
*/

// 케이스 B: number를 반환하는 함수를 넣었을 때
type B = GetReturnType<typeof getAge>; // 결과: `number` 타입이 됨

// 케이스 C: 함수가 아닌 일반 타입을 넣었을 때
type C = GetReturnType<string>;
/*
  작동 과정:
  1. T에 `string`이 들어온다
  2. `string`은 함수 모양(`(...args: any[]) => infer R`)에 부합하지 않는다
  3. 조건이 거짓이므로 `:` 뒤에 있는 `never`가 할당된다
  4. 결과: 최종적으로 `C` 타입은 `never`가 됩니다.
*/
```

`never` 키워드

`never` 키워드는 '절대로 일어날 수 없는 상태' 또는 '아무것도 가질 수 없음'을 나타내는 타입이다.

함수가 항상 예외를 던지거나 무한 루프를 돌아서 절대 반환되지 않을 때 반환 타입으로 쓰인다.

조건부 타입에서 '이 조건에 맞지 않는 타입은 완전히 제외하겠다'는 의미도로 쓰인다.

```typescript
function throwError(message: string): never {
    throw new Error(message);
}
```


**구조적 타이핑**

타입스크립트는 객체의 구조가 동일하면 같은 타입으로 판단한다.

```java
// 자바의 경우 클래스가 다르면 서로 다른 타입이다
class Dog{}
class Cat{}
```

```typescript
interface Animal {
    name: string;
}

const dog = {
    name: "Mango",
}

const animal: Animal = dog;
```


## 객체지향

캡슐화

```typescript
class BankAccount {
    private balance = 0;

    deposit(amount: number) {
        this.balance += amount;
    }

    getBalance() {
        return this.balance;
    }
}
```

상속

```typescript
class Animal {
    move() {
        console.log("move");
    }
}

class Dog extends Animal {
    bark() {
        console.log("bark");
    }
}
```

다형성

```typescript
class Animal {
    speak(): void {}
}

class Dog extends Animal {
    override speak() {
        console.log("bark");
    }
}

class Cat extends Animal {
    override speak() {
        console.log("meow");
    }
}

const animals: Animal[] = [
    new Dog(),
    new Cat(),
];

animals.forEach(a => a.speak());
```

추상화

```typescript
abstract class Animal {
    abstract speak(): void;
}

class Dog extends Animal {
    override speak() {
        console.log("bark");
    }
}
```

## 함수 문법

타입스크립트 함수는 매개변수와 반환값에 각각 타입을 지정할 수 있다.

### 함수 선언문

```typescript
function add(x: number, y: number): number  {
    return x + x;
}
```

### 함수 표현식

```typescript
const add = function(x: nubmer, y: number): number {
    return a + b;
}
```

### 화살표 함수

```typescript
const add = (x: number, y: number): number => {
    return a + b;
}
```

### 반환값 타입 생략

반환값 타입을 명시하지 않아도 타입스크립트 컴파일러가 타입을 추론한다.

```typescript
// 반환 타입을 number로 추론한다
function add(x: number, y: nubmer) {
    return x + y;
}
```

### 반환값이 없는 함수 `void`

함수가 어떤 값도 반환하지 실행을 끝낸다면 `void` 키워드를 사용한다. 

```typescript
function greet(name: string): void {
    console.log(`Hello, ${name}`);
}
```

### 선택적 매개변수

매개변수 이름 뒤에 `?`를 붙이면 호출자가 인자를 전달하지 않아도 된다.

```typescript
function greet(name: string, email?: string): void {
    console.log(`Hello, ${name}`);
}
greet("hansanhha"); // email은 undefined가 된다
```

### 기본값 매개변수

매개변수에 기본값을 지정하면 인자값을 전달받지 못했을 때 매개변수의 값이 기본값으로 설정된다.

```typescript
function setPrice(price: number, tax: number = 0.1): number {
  return price + price * tax;
}
```


## 인터페이스

객체지향의 클래스처럼 타입을 정의할 수 있다.

타입스크립트 코드에서 단순히 타입 체크를 위한 이용되며 컴파일된 자바스크립트에는 인터페이스가 존재하지 않는다.

따라서 인터페이스로 객체를 만들 수 없고, 인터페이스는 메서드 구현을 할 수 없다. (구현은 클래스에서 함)

**인터페이스는 데이터 계약이나 DTO와 같은 데이터 구조, API 응답에 사용한다.**

**클래스는 서비스, 저장소, 도메인 객체처럼 동작과 상태가 필요한 곳에서만 사용한다.**

```typescript
interface User {
    id: number;
    name: string;
    language: string;
    greet(): void;
}

const user: User = {
    id: 1,
    name: "hansanhha",
    language: "typescript",

    greet(): void {
        console.log(`Hello ${this.name}`);
    }
};

// 아래의 코드처럼 인터페이스를 기반으로 하는 인스턴스를 생성할 수 없다
const user = new User(id, "hansanhha", "typescript");
```

```javascript
// 자바스크립트로 변환된 코드
"use strict";
const user = {
    id: 1,
    name: "hansanhha",
    language: "typescript",
    greet() {
        console.log(`Hello ${this.name}`);
    }
};
user.greet();
```

인터페이스 확장

```typescript
interface Vehicle {
    name: string;
    move(): void;
}

interface Flyable {
    fly(): void;
}

// 다중 인터페이스 확장 가능
interface Car extends Vehicle, Flyable {
    brand: string;
}
```

### 인터페이스 vs 타입

인터페이스와 타입 둘 다 객체 타입을 정의할 수 있다.

```typescript
type User = {
    id: number;
}

interface User {
    id: number;
}
```

동일한 이름의 인터페이스를 여러 개 만들면 자동으로 병합되지만 타입은 그러지 못한다.

일반적으로 객체 모델을 정의할 때는 `interface`, 유니온/intersection/유틸리티 타입에는 `type` 사용한다.

```typescript
interface User {
    id: number;
}

interface User {
    name: string;
}

// 병합된 인터페이스
{
    id: number;
    name: string;
}
```


## 클래스

클래스는 실제 객체를 생성하는 템플릿으로 인터페이스와 달리 컴파일 후 자바스크립트 코드가 생성된다.

```typescript
class User {
    // 축약 문법
    constructor(
        public id: number,
        public name: string,
        public language: string
    ) {}

    greet(): void {
        console.log(`Hello ${this.name}`);
    }
}

const user = new User(1, "hansanhha", "typescript");
user.greet(); // 'Hello hansanhha'
```

클래스 멤버에 대한 접근 제한을 지원한다.

`public`: 기본값, 어디서든 접근할 수 있다.

`private`: 클래스 내부에서만 접근할 수 잇다.

`protected`: 자신과 자식 클래스에서만 접근할 수 있다.

`readonly`: 초기화 이후 수정할 수 없다.

``` javascript
// 자바스크립트로 변환된 코드
"use strict";
class User {
    id;
    name;
    language;
    constructor(id, name, language) {
        this.id = id;
        this.name = name;
        this.language = language;
    }
    greet() {
        console.log(`Hello ${this.name}`);
    }
}
const user = new User(1, "hansanhha", "typescript");
user.greet(); // 'Hello hansanhha'
```

클래스 상속

```typescript
class Vehicle {
    constructor(public name: string) {}

    move(): void {
        console.log(`move ${this.name}`);
    }
}

// 자바스크립트/타입스크립트는 단일 상속만 지원한다
class Car extends Vehicle {
    constructor(
        name: string,
        public brand: string
    ) {
        super(name);
    }
}
```

인터페이스 구현

클래스는 인터페이스를 구현할 수 있다.

```typescript
interface Vehicle {
    move(): void;
}

class Car implements Vehicle {
    move(): void {
        console.log("go");
    }
}
```

```javascript
// 자바스크립트로 변환된 코드
"use strict";
class Car {
    move() {
        console.log("go");
    }
}

```

### 인터페이스 vs 추상 클래스

```typescript
interface Flyable {
    fly(): void;
}
```

인터페이스는 타입스크립트 파일 내에서만 존재하여 구현을 가질 수 없다.

계약을 표현하는데 사용한다.

```typescript
abstract class Animal {
    constructor(
        protected name: string
    ) {}

    abstract speak(): void;

    move() {
        console.log("move");
    }
}
```

추상클래스는 인터페이스와 달리 자바스크립트 코드로 변환될 수 있다.

따라서 상태와 구현을 가질 수 있고 상속 기반 설계를 가능하게 한다.

데이터 구조를 정의하는 경우엔 인터페이스, 구현을 공유하는 경우에는 추상 클래스를 사용한다.


## 제네릭

```typescript
function identity<T>(value: T): T {
    return value;
}

const num = identity<number>(123);
const str = identity<string>("hello");
```

```typescript
interface ApiResponse<T> {
    success: boolean;
    data: T;
}

const response: ApiResponse<User> {
    success: true,
    data: {
        id: 1,
        name: "hansanhha",
    },
};
```

## Decorator

데코레이터는 타입스크립트의 어노테이션이라고 보면 된다.

클래스 선언, 메서드, 접근자, 프로퍼티, 매개변수에 추가적인 메타데이터를 첨부하거나 동작을 확장할 수 있는 메타 프로그래밍 기능이다.

```typescript
// 로깅을 담당하는 메서드 데코레이터 정의
function Log<This, Args extends any[], Return>(
    target: (this: This, ...args: Args) => Return,
    context: ClassMethodDecoratorContext<This, (this: This, ...args: Args) => Return>
) {
    const methodName = String(context.name);

    return function(this: This, ...args: Args): Return {
        // Before Advice 로직
        console.log(`[LOG] ${methodName} 메서드 호출됨, 인자: `, args);

        const result = target.call(this, ...args);

        // After Advice 로직
        console.log(`[LOG] ${methodName} 메서드 실행 완료, 결과: `, result);
        return result;
    }
}

class UserService {
    @Log
    createNewUser(name: string, role: string) {
        return { id: 1, name, role };
    }
}

const service = new UserService();
service.createNewUser("hansanhha", "Admin");
```

## Advanced Type

Advanced Type은 기존 타입을 조합하거나 변형하여 고차원의 타입을 구조화하는 방식을 말한다.

```typescript
// 교차 타입(Intersection Type): 여러 타입을 하나로 결합한 방식
interface Timestamp {
    createdAt: Date;
    updatedAt: Date;
}

interface UserProfile {
    id: string;
    name: string;
}

// 두 인터페이스의 모든 속성을 가져야 하는 새로운 타입 선언
type TimestampsUserProfile = UserProfile & Timestamp;

const activeUser: TimestampsUserProfile {
    id: "user_01",
    name: "hansanhha",
    createdAt: new Date(),
    updatedAt: new Date()
};
```

```typescript
// 인덱스 시그니처(Index Signatures): 속성의 이름은 모르지만 타입의 구조를 정의할 때 사용
interface DynamicCache {
    // key는 무조건 string, value는 string 또는 number
    [key: string]: string | number;
}

const configCache: DynamicCache = {
    theme: "dark",
    maxRetry: 5,
}
```

## Utility Type

타입스크립트는 공통적인 타입 변환을 쉽게 수행할 수 있도록 전역 유틸리티 타입을 제공한다.

제네릭을 조합하여 코드 재사용성을 높일 수 있다.

```typescript
interface Todo {
    title: string;
    description: string;
    completed: boolean;
}

// 1. Partial<T>: 모든 프로퍼티를 선택 사항(Optional)로 변경한다
const updateTodo = (todo: Todo, fieldsToUpdate: Partial<Todo>): Todo => {
    return { ...todo, ...fieldsToUpdate };
}

// 2. Readonly<T>: 모든 프로퍼티를 읽기 전용으로 변경하여 수정 방지
const myTodo: Readonly<Todo> = {
    title: "typescript",
    description: "utility type",
    completed: true
};

// 3. Pick<T, K>: 원하는 프로퍼티만 골라서 새로운 타입 생성
type TodoPreview = Pick<Todo, "title" | "completed">;

const preview: TodoPreview = {
    title: "방 청소",
    completed: true
};

// 4. Omit<T, K>: 특정 프로퍼티만 제외하고 새로운 타입 생성
type TodoInfo = Omit<Todo, "completed">;

const info: TodoInfo = {
    title: "2026년 월드컵 응원",
    description: "대한민국 vs 체코"
}
```


## Type Inferrence

개발자가 코드를 작성할 때 명시적으로 타입을 선언하지 않아도, 타입스크립트 컴파일러가 코드를 분석하여 자동으로 타입을 추론한다. (타입 추론)

```typescript
let x = 10;    // 자동으로 number로 추론된다
// x = "hello" // 컴파일 오류

function add(a: number, b: number) {
    return a + b;  // 매개변수가 number이므로 반환 타입이 자동으로 number로 추론된다
}

// (string | number | null)[] 배열로 추론된다
let arr = [0, 1, "hello", null];

// mouseEvent가 MouseEvent 타입임을 브라우저 컨텍스트를 통해 자동으로 추론한다 (Contextual Typing)
window.onmousedown = function(mouseEvent) {
    console.log(mouseEvent.button);
}
```


## Type Narrowing

Type Narrowing(타입 구별)은 `any`나 `unknown`, 유니온 타입 (`string | number`)처럼 광범위한 타입을 가진 변수를 특정 조건문 안에서 더 좁고 명확한 타입으로 좁히는 기법이다.

```typescript
function processInput(input: string | number | Date) {

    // typeof 검사를 통한 Narrowing
    if (typeof input === "string") {
        console.log(input.toUpperCase());  
    }

    // instanceof 검사를 통한 Narrowing
    else if (input instanceof Date) {
        console.log(input.getFullYear());
    }

    // 남은 타입에 대한 자동 추론
    else {
        console.log(input.toFixed(2));
    }
}
```


## Discriminated Union

Discriminated Union(판별 연산자 유니온)은 타입 내에 동일한 이름의 리터럴 프로퍼티(식별자)를 심어두어, 객체 유니온 타입을 안전하게 구별하는 기법이다.

```typescript
// 공통된 리터럴 타입 프로퍼티 'type'을 심어놓는다
// type(Discriminant)은 판별자 역할을 한다
interface SuccessResponse {
    type: "success";
    data: string[];
}

interface FailureResponse {
    type: "failure";
    error: Error;
}

type ApiResponse = SuccessResponse | FailureResponse;

function handleResponse(response: ApiResponse) {
    // 공통 식별자 'type' 값에 대한 패턴 매칭
    switch (response.type) {
        case "success":
            // 성공 비즈니스 로직
            break;
        case "failure":
            // 실패 처리 로직
            break;
    }
}
```


## Mapped Types

기존 타입을 기반으로 새로운 타입의 프로퍼티 셋을 선언하는 문법이다.

`Array.prototype.map`과 유사하게 타입을 가공할 수 있다.

```typescript
type FeatureFlags = {
    darkMode: boolean;
    betaFeatures: boolean;
    aiChat: boolean;
}

// Mapped Type을 활용하여 모든 속성의 값 타입을 boolean -> string으로 바꾼다
// readonly 옵션과 선택적(?) 옵션을 동시에 주입한다
type MetaFlags<T> = {
    readonly [Property in keyof T]?: string;
}

type TransformedFlags = MeataFlags<FeatureFlags>;
/*
{
    readonly darkMode?: string;
    readonly betaFeatures?: string;
    readonly aiChat?: string;
}
*/

const userSettings: TransformedFlags = {
    darkMode: "enabled",
    aiChat: "disabled"
}
```


## Conditional Types

삼항 연산자 조건문처럼 조건에 따라 타입을 동적으로 결정하는 기법이다.

`T extends U ? X : Y` 형태로 사용되며, 런타임이 아닌 컴파일 타임에 분기 처리가 이뤄진다.

```typescript
type StringOrNumberArray<T> = T extends string ? string[] : number[];

type T1 = StringOrNumberArray<string> // string[] 타입
type T2 = StringOrNumberArray<number> // number[] 타입

// 조건부 타입과 infer 키워드를 활용해 함수의 반환 타입을 알아낸다
type GetReturnType<T> = T extends (...args: any[]) => infer R ? R : never;

function getNumber() {
    return 100;
}

// number 타입을 자동으로 추론한다
type NumberType = GetReturnType<typeof getNumber>; 
```

## Template Literal Types

문자열 리터럴 타입을 기반으로 새로운 문자열 타입을 조합해 내는 기법이다.

CSS 툴킷, 라우팅 경로 처리, 다국어 키 조합 오타 방지 타이핑을 지원한다.

```typescript
type Alignment = "left | right | center";
type PaddingSide = "top" | "bottom";

// 템플릿 리터럴 문법을 통해 모든 조합의 문자열 타입을 자동으로 생성한다
type PaddingClassName = `box-padding-${PaddingSide}-${Alignment};`

/*
    PaddingClassName 타입은 아래의 조합을 모두 가진다
    "box-padding-top-left" | "box-padding-top-right" | "box-padding-top-center" |
    "box-padding-bottom-left" | "box-padding-bottom-right" | "box-padding-bottom-center"
*/

const validClass: PaddingClassName = "box-padding-top-center";
// const InvalidClass: PaddingClassName = "box-padding-left"; // 컴파일 오류
```


## `.d.ts`

`.d.ts` 파일은 타입 정보만 담고 있는 선언 파일(Declaration File)이다.

실행 가능한 코드는 포함하지 않고 오직 순수한 타입 정보만 명시하여 컴파일러에게 알려주는 설명서 역할을 한다.

`.ts` 파일은 컴파일되면 자바스크립트로 바뀌지만 일반적으로 `.d.ts` 파일은 타입 검사 목적으로만 쓰이고 컴파일 시 사라진다. 

`declaration` 컴파일 옵션을 활성화하면 타입스크립트 코드를 자바스크립트 코드로 컴파일할 때 `.d.ts` 파일을 생성한다.

용도
- 자바스크립트로 만들어진 라이브러리를 타입스크립트 환경에서 쓸 수 있도록 타입 정보 제공
- 프로젝트 전역에서 사용하는 객체(`window`)의 타입 정의

타입스크립트는 기본적으로 `document` 등의 객체에 대한 타입을 정의한 `.d.ts` 파일을 제공한다.

`npm`을 통해 다운로드 받는 `@types/`가 붙는 모듈들이 타입 선언을 한 모듈이다.

```text
{
  "devDependencies": {
    // ....
    "@types/babel__core": "^7.20.5",
    "@types/node": "^24.12.3",
    "@types/react": "^19.2.14",
    "@types/react-dom": "^19.2.3",
  }
}
```

## 타입스크립트 실행

자바스크립트 런타임(웹 브라우저, Node.js)는 타입스크립트를 직접 실행하지 못한다.

타입스크립트로 작성된 코드를 자바스크립트로 변환하는 컴파일러를 이용한다.

```typescript
$ npm install -g typescript
$ tsc app.ts
```

```text
app.ts
  ↓
TypeScript Compiler (tsc)
  ↓
app.js
```