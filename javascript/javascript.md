## 자바스크립트
- [자바스크립트](#자바스크립트)
- [언어 특징](#언어-특징)
- [런타임과 자바스크립트 엔진](#런타임과-자바스크립트-엔진)
  - [브라우저 런타임 vs Node.js 런타임](#브라우저-런타임-vs-nodejs-런타임)
- [렌더링 엔진](#렌더링-엔진)
- [웹 어셈블리 (WASM)](#웹-어셈블리-wasm)
- [ES, ECMAScript (자바스크립트 표준)](#es-ecmascript-자바스크립트-표준)
- [변수 선언](#변수-선언)
- [호이스팅](#호이스팅)
- [데이터 타입](#데이터-타입)
  - [primitive 타입](#primitive-타입)
  - [object 타입](#object-타입)
- [연산자](#연산자)
- [조건문](#조건문)
- [반복문](#반복문)
- [함수](#함수)
- [객체](#객체)
  - [생성자 함수](#생성자-함수)
  - [`class` 키워드](#class-키워드)
  - [프로토타입](#프로토타입)
  - [정적 메서드](#정적-메서드)
- [배열](#배열)
- [구조 분해 할당](#구조-분해-할당)
- [Rest, Spread 문법](#rest-spread-문법)
- [가변 인자](#가변-인자)
- [템플릿 리터럴](#템플릿-리터럴)
- [함수형 프로그래밍](#함수형-프로그래밍)
  - [순수함수와 사이드 이펙트](#순수함수와-사이드-이펙트)
  - [람다 표현식](#람다-표현식)
  - [고차 함수](#고차-함수)
  - [클로저](#클로저)
    - [커링](#커링)
    - [메모이제이션](#메모이제이션)
  - [불변성](#불변성)
  - [함수 합성](#함수-합성)
  - [함수 체이닝](#함수-체이닝)
  - [`map()`](#map)
  - [`filter()`](#filter)
  - [`reduce()`](#reduce)
  - [`find()`](#find)
  - [`some()`](#some)
  - [`every()`](#every)
  - [`sort()`](#sort)
  - [`flat()`](#flat)
  - [`flatMap()`](#flatmap)
- [콜백 함수](#콜백-함수)
- [`Promise`](#promise)
  - [프로미스 체이닝](#프로미스-체이닝)
  - [`Promise` 상태](#promise-상태)
  - [`fetch` API](#fetch-api)
  - [`Promise.resolve()`, `Promise.reject()`](#promiseresolve-promisereject)
- [`async`, `await`](#async-await)
- [모듈(ESM)](#모듈esm)
  - [CommonJS](#commonjs)
  - [AMD](#amd)


## 언어 특징

동적 타입 언어: 런타임에 변수의 타입을 변경할 수 있다.

약타입 언어: 타입이 달라도 자동 형변환을 수행한다.

함수형 언어: 함수를 일급 객체로 취급하여 값으로써 사용할 수 있다.

프로토타입 기반 객체지향: 객체가 다른 객체의 원형을 복사하는 방식으로 객체를 확장한다. (**자바스크립트에는 클래스 개념이 없다**)

싱글 스레드: 자바스크립트 엔진은 하나의 콜 스택을 가진다.

이벤트 기반 비동기 처리: 이벤트 루프, 콜백 큐 등을 통해 비동기 작업을 수행한다.

인터프리터 + JIT (Just-in-Time) 컴파일러 기반: 초창기에는 인터프리터 방식이었으나, 현재는 자바스크립트 코드를 실행하기 전 컴파일하여 고속으로 처리한다.

런타임과 자바스크립트 엔진: 자바스크립트를 실행할 수 있는 환경과 핵심 엔진(V8)이 분리되어 있다.


## 런타임과 자바스크립트 엔진

자바스크립트 코드를 실행할 수 있는 환경(런타임)
1. 브라우저
2. Node.js, Deno, Bun 등

브라우저는 두 가지 방법으로 자바스크립트를 실행할 수 있다.

```html
<!DOCTYPE html>
<html>
<body>

<!-- 1. html 파일 내에 script 태그 사용 -->
<script>
    console.log("Hello JavaScript");
</script>

</body>
</html>
```

```html
<!-- 2. html 파일에서 js 파일 import -->
<script src="app.js"></script>
```

**Node.js**는 브라우저 밖에서도 자바스크립트를 실행할 수 있게 만든 런타임이다.

내부적으로 구글의 V8 엔진을 사용한다.

Node.js: 런타임

V8: 자바스크립트 엔진 (ECMAScript 구현체)

V8 엔진은 C++로 작성되었으며 구글 크롬 브라우저와 웹 어셈블리 엔진으로도 사용된다.

또한 웹 브라우저, 서버뿐만 아니라 Electron 같은 데스크탑 앱, 임베디드 C++ 프로그램 등에서 구동할 수 있다.

### 브라우저 런타임 vs Node.js 런타임

브라우저에서 자바스크립트의 목적은 화면 조작이다. 그래서 DOM 조작, 브라우저와 관련된 API를 제공한다.

```text
window
document
localStorage
sessionStorage
alert
history
location
navigator
```

Node.js의 목적은 서버 프로그램 실행이다.

그래서 DOM 조작 API 대신 아래와 같은 API를 제공한다.

```text
fs
path
http
net
os
process
```


## 렌더링 엔진

브라우저의 렌더링 엔진은 HTML, CSS, 자바스크립트를 전달받아 화면에 픽셀을 그려주는 역할을 한다.

블링크(Blink)
- 구글의 크로미움 기반 렌더링 엔진
- 애플의 웹키트로부터 분리(Fork)해서 만들었다

웹키트(WebKit)
- 애플의 렌더링 엔진
- 사파리, iOS, iPadOS 환경의 모든 브라우저에서 웹키트 사용을 강제한다
  
게코(Gecko)/퀀텀(Quantum)
- 파이어폭스 렌더링 엔진
- 오픈소스


## 웹 어셈블리 (WASM)

웹 어셈블리는 자바스크립트 외에 C/C++, Rust, Go 같은 다른 프로그래밍 언어들이 네이티브 앱처럼 빠르게 실행될 수 있도록 만든 기술이다.

현대 웹 브라우저는 하나의 운영체제 수준에 준하는 방향으로 발전하고 있으나 자바스크립트는 태생적으로 성능 한계(동적 타입, GC, 싱글 스레드 등)를 가지고 있다.

그래서 자바스크립트로부터 벗어나 웹 브라우저를 빠르게 실행하기 위해 C++이나 Rust처럼 처음부터 컴파일되어 컴퓨터 친화적이며 빠른 속도를 가진 언어를 지원하고자 웹 어셈블리가 만들어졌다.

웹 어셈블리는 직접 코딩하는 언어가 아니라 바이너리 파일에 가깝다.

개발자가 성능에 중요한 무거운 로직을 C++ 같은 언어로 작성하고 컴파일하면 `.wasm` 바이너리 파일로 변환한다.

브라우저의 자바스크립트 엔진(V8)의 내부에 있는 가상 머신이 `.wasm` 파일을 다운로드받아 파싱이나 복잡한 해석 과정없이 즉시 실행한다.

아쉽게도 웹 어셈블리는 자바스크립트를 완벽하게 대체할 수 없다.

DOM을 조작하거나 이벤트를 처리하고 서버와 통신하는 작업은 자바스크립트로 처리하고, 초고속 3D 그래픽 렌더링, 대용량 파일 압축과 같은 연산을 웹 어셈블리가 담당한다.

구현 예시
- 피그마: 화면으로 보이는 버튼이나 메뉴는 자바스크립트로 그리고, 화면 중앙에서 수천 개의 벡터 그래픽을 C++로 만들고 웹 어셈블리고 실행한다.
- 구글 어스: 웹 어셈블리를 이용하여 플러그인 없이 브라우저로 3D 지구를 볼 수 있다.



## ES, ECMAScript (자바스크립트 표준)

자바스크립트는 넷스케이프의 브랜든 아이크가 10일만에 만든 언어임에도 불구하고 정말 빠르게 성장했다. (브랜든 아이크는 이후 모질라 재단을 만들고 파이어폭스를 만듦)

당시에 넷스케이프 외에 마이크로소프트 등 여러 기업들이 자바스크립트를 변경하여 쓰다가 생태계가 어지러워지자 하나의 표준안을 만든게 바로 ES(ECMAScript)이다.

ES5, ES6처럼 ES 뒤에 붙는 숫자는 ES의 버전을 의미한다.

주요 ES6 문법
- `let`, `const`
- 화살표 함수 `=>`
- `class` 문법 도입
- `filter`, `map` 등


## 변수 선언

```javascript
var x = 10;
let name = "hansanhha";
const language = "javascript";
```

`var`: 변수 재선언 가능, 함수 스코프, 호이스팅 버그 발생 가능

`let`: `var`의 버그 해결, 재할당 가능

`const`: 불변

var의 악마같은 3가지 특징을 살펴보자

```javascript
// 같은 이름의 변수를 선언해도 에러가 나지 않고 덮어쓴다
var a
var a 
```

```javascript
// 함수 스코프만 인정하고 나머지 if, for문의 코드 블럭은 무시한다
if (true) {
  var secret = "Hello";
}
console.log(secret);  // Hello

for (var i = 0; i < 10; i++) {}
console.log(i); // 10
```

```javascript
// 호이스팅이 일어날 때 var로 선언한 변수는 에러 대신 undefined를 반환한다
// let을 사용하면 호이스팅은 일어나지만 undefined 대신 참조 에러를 내뱉는다.
console.log(user); // undefined
var user = "hansanhha";
```

## 호이스팅

인터프리터가 코드를 읽을 때 변수와 함수의 선언과 할당을 분리하고 선언을 컨텍스트 내의 최상단으로 끌어올리는 현상

`name` 변수를 선언과 할당하는 코드가 있다.

```javascript
var name = "hansanhha";
```

자바스크립트 엔진은 선언부만 뽑아서 메모리에 먼저 등록하고 할당은 제자리에 남겨둔다.

```javascript
// (주의) var 키워드는 호이스팅될 때 undefined를 할당한다
var name;
name = hansanhha;
```

`function` 키워드로 만든 함수 선언문은 함수의 선언과 바디 전체가 통째로 호이스팅된다

```javascript
greet();

function greet() {
  console.log("Hello");
}
```

자바스크립트 엔진은 두 번의 단계를 거쳐 동작한다.

소스코드 스캔 단계: 코드를 위에서 아래로 순차적으로 읽으면서 `var`, `let`, `const`, `function` 같은 선언문을 찾은 뒤 메모리 공간(실행 컨텍스트)에 등록한다. (호이스팅)

소스코드 실행 단계: 스캔 단계가 끝나면 다시 처음부터 코드를 실제로 실행한다.

컴파일 단계에서 `function` 키워드를 사용한 함수들의 위치를 전부 기억하기 때문에 코드의 순서와 상관없이 서로를 자유롭게 참조할 수 있게 된다.

```javascript
function isEven(n) {
  if (n === 0) return true;
  return isOdd(n - 1); // 밑에 있는 isOdd() 호출
}

function isOdd(n) {
  if (n === 0) return false;
  return isEven(n - 1); // 위에 있는 isEven() 호출
}
```


## 데이터 타입

자바스크립트는 동적 타입 언어이기 때문에 자바처럼 소스 코드에서 타입 선언을 하지 않는다.

### primitive 타입
- `number`
- `string`
- `boolean`
- `symbol`
- `bigint`
- `null`
- `undefined`

`null`: '값이 비어있음을 의도적으로 명시한' 상태

`undefined`: '변수만 선언하고 값이 정의되지 않은' 상태

```javascript
var a
console.log(a) // undefined

a = null
console.log(a) // null
```

`null`과 `undefined`을 비교할 때는 어떤 연산자를 사용하느냐에 따라 결과가 달라진다.

```javascript
// 타입을 무시하고 값의 의미만 비교하므로 true
null == undefined

// 타입까지 엄격하게 비교하므로 false
null === undefined
```

`typeof null`을 실행하면 'object'가 반환된다.

자바스크립트 초기 버전의 설계 오류이지만 기존 웹 사이트들의 호환성을 위해 수정하지 않고 그대로 유지되고 있다.

```javascript
typeof null // 'object'
```

### object 타입
- `object`
- `array`
- `function`


## 연산자

비교 연산자
- `==`: 값만 비교
- `===`: 값 + 타입 비교

```javascript
1 == "1"  // true
1 === "1" // false
```


## 조건문

```javascript
if (name === undefined) {
    console.log("name required");
}
```

```javascript
// 삼항 연산자
const message = name === undefined ? "name required" : name;
```


## 반복문

`for`문

```javascript
for (let i = 0; i < 10; i++) {
    console.log(i);
}
```

`for...of`문

배열이나 객체의 값을 순회한다.

```javascript
const arr = [1, 2, 3];

for(const value of arr) {
    console.log(value);
}
```

`forEach`

```javascript
arr.forEach(v => console.log(v));
```


## 함수

함수 선언

```javascript
function add(a, b) {
    return a + b;
}
```

함수 표현식

자바스크립트는 함수를 일급 객체로 취급하기 때문에 변수에 할당할 수 있다.

```javascript
const add = function(a ,b) {
    return a + b;
}
```

화살표 함수

`function` 키워드를 생략하고 람다식으로 작성하는 방식이다.

```javascript
const add = (a, b) => {
    return a + b;
}

// 축약
const add = (a, b) => a + b;
```


## 객체

객체는 키와 값의 쌍을 모은 컨테이너이다.

객체가 가지고 있는 각각의 키-값 쌍을 프로퍼티(속성)라고 하며 프로퍼티 이름(key)을 통해 값에 접근할 수 있다.

프로퍼티 값에 함수가 들어가면 그 프로퍼티를 메서드라고 한다.

마지막 프로퍼티에 컴마는 생략할 수 있으며, 넣어도 문법 오류가 발생하지 않는다.

자바스크립트 객체의 키를 쌍따옴표(`"`)로 감싸지 않지만 JSON에서는 감싼다.

```javascript
// 객체 정의
const product = {
    name: "keyboard",
    amount: 1000,
}
```

```javascript
// 객체 프로퍼티 접근
product.name
product["name"]
```

```javascript
// 객체 프로퍼티 추가
// 런타임에 현재 인스턴스의 프로퍼티를 추가할 수 있다
product.category = "device";
```

객체 안에 프로퍼티 이름(key)이 존재하는지 확인하는 방법

```javascript
// in 연산자
// 상속받은 프로퍼티까지 모두 검사한다
"category" in product;  // true
"status" in product;    // false
"toString" in product;  // true ('toString'은 상속받는 프로퍼티임)

// hasOwnProperty 메서드 
// 자신이 정의한 프로퍼티만 검사한다
product.hasOwnProperty("toString") // false (상속받은 프로퍼티는 false)
```

자바스크립트는 함수와 메서드를 모두 지원한다.

함수: 특정 객체에 종속되지 않고 독립적으로 호출할 수 있는 코드 블럭

메서드: 객체를 통해서만 호출할 수 있는 코드 블럭

```javascript
function greet() {
    console.log("Hello");
}
greet(); // 함수 호출

const user = {
    name: "hansanhha",

    greet: function() {
        console.log(`hello ${this.name}`);
    }
}
user.greet(); // 메서드 호출
```

### 생성자 함수

일반적인 객체지향 프로그래밍 언어에서는 클래스를 정의하고 그 안에 생성자를 만들고 `new` 키워드로 인스턴스를 생성한다.

이와 달리 자바스크립트는 객체를 만드는 함수인 생성자 함수를 별도로 정의하고 `new` 키워드로 인스턴스를 생성한다.

관례적으로 생성자 함수는 첫 글자를 대문자로 시작한다.

```javascript
// car 객체를 만드는 생성자 함수
function Car(name, amount) {
    this.name = name;
    this.amount = amount;
    this.drive = () => {
        console.log(`${this.name}가 주행한다.`);
    }
}

const car1 = new Car("소나타", 1000);
const car2 = new Car("아반떼", 2000);

car1 === car2 // false
```

`new` 키워드를 붙이면 빈 객체가 하나 만들어지고, 함수 내부의 `this`가 빈 객체를 가리킨다.

`this.name = name` 같은 코드를 통해 빈 객체에 프로퍼티들이 추가되며 완성된 객체가 자동으로 반환되어 변수에 저장된다.

모든 객체는 함수를 통해서 생성된다. 빈 `{}`으로 객체를 만드는 것도 사실 `Object` 함수를 호출한다.

함수가 아닌 대상에 `new` 키워드를 붙여서 객체를 생성할 수 없다.

```javascript
var empty = {};
var empty = new Object();
```

### `class` 키워드

`class` 키워드는 생성자 함수의 syntatic sugar로 ES6+에 추가되었다.

동작하는 방식은 기존 생성자 함수와 동일하다.

```javascript
class Car {
    constructor(name, amount) {
        this.name = name;
        this.amount = amount;
    }

    drive() {
        console.log(`${this.name}가 주행한다.`);
    }
}

const car1 = new Car("소나타", 1000);
const car2 = new Car("아반떼", 2000);

car1 === car2 // false
```

### 프로토타입

생성자 함수는 함수의 첫 글자를 대문자로 만들며 객체 생성을 담당하는 역할을 관례적으로 부여한 함수이다.

사실 자바스크립트의 모든 함수는 객체를 만들 수 있다.

함수를 선언하면 자동적으로 Prototype Object라고 하는 객체가 만들어지고 이를 `prototype` 키 값으로 참조하게 된다.

`prototype` 객체에는 함수의 생성자를 가리키는 `constructor`와 부모 프로토타입을 참조하는 `__proto___` 프로퍼티가 있다.

```javascript
function Product() {

    // 함수를 선언하면 자동으로 프로토타입 객체가 만들어지며 prototype 키로 참조하게 된다
    prototype: {
        constructor: [Function: Product]
        __proto__: [Object: null prototype] {}
    }
}
```

`prototype` 프로퍼티는 해당 함수가 생성하는 객체의 원형 상태를 유지하기 위해 존재한다. 

다른 일반적인 객체와 마찬가지로 `prototype` 프로퍼티도 수정할 수 있는데, 이 프로퍼티를 수정하면 생성된 모든 객체에 영향을 끼친다.

마치 클래스의 필드를 수정한 느낌이다.

```javascript
Product.prototype // {}
Product.prototype.name = "keyboard"
Product.prototype // { name: 'keyboard' }

let p = new Product();
p.name; // "keyboard"
```

모든 객체에 영향을 끼칠 수 있는 것은 함수를 통해 만들어진 객체는 함수의 `prototype`을 참조하는 `__proto__` 프로퍼티 덕분이다.

함수에 `prototype` 프로퍼티가 생성된 것처럼  `__proto__`는 함수를 통해 만들어진 모든 객체에 자동적으로 추가된다. 

대신 객체에 대한 프로퍼티 객체는 생성되지 않고 `prototype` 프로퍼티도 추가되지 않는다.

객체의 `__proto__` 프로퍼티가 함수의 `prototype`를 참조하여 함수 원형에 접근하게 되는 것이다.

```javascript
let p = new Product();
p.__proto__ // { name: 'keyboard' }
```

아래와 같이 부모의 부모를 찾아갈 수도 있다.

```javascript
p.__proto__.__proto__  // [Object: null prototype] {}
```

`p.name`을 호출하면 자바스크립트 엔진은 먼저 `p` 객체의 프로퍼티를 탐색한다.

`p` 객체는 `name` 프로퍼티가 없으므로 `__proto__`를 통해 부모의 `prototype` 객체를 탐색한다.

부모의 `prototype` 객체에도 없다면 부모 `__proto__`를 통해 상위 프로토타입에 접근하여 프로퍼티가 있는지 탐색한다.

모든 객체의 최상위 프로토타입인 `Object`의 `prototype` 에도 없다면 `undefined`를 반환하다.

```javascript
Object.prototype.toString    // [Function: toString]
Object.prototype.constructor // [Function: Object]
Object.prototype.__proto__   // null
```

객체에 다른 값을 할당하는 순간, 해당 객체에 프로퍼티가 할당되어 `__proto__`를 통해 상위 프로토타입에 접근하지 않게 된다.

```javascript
let p = new Product();
p  // product {}

p.name = "mouse";
p  // product { name: 'mouse' }
```

함수에 `this` 키워드를 이용하여 객체의 생성과 프로퍼티 초기화를 동시에 할 수 있다.

```javascript
function Product(name) {
    this.name = name;
}

let p = new Product("keyboard");
p  // product { name: 'mouse' }
```

### 정적 메서드


## 배열

배열 초기화

```javascript
const arr = [];
const arr = [1 ,2 ,3];
const arr = new Array(1, 2, 3);

// 길이만 5인 배열을 초기화
// [ <5 empty items> ] 
const arr = new Array(5);

// [0, 0, 0, 0, 0]
const arr = new Array(5).fill(0);
```

인덱싱

```javascript
arr[0]

// 인덱스 범위를 초과하면 undefined가 출력된다
arr[100] 

// 음수 인덱스를 지원하지 않는다
arr[-1]

// at 함수를 사용한다
arr.at(-1) 
```

슬라이싱

배열의 일부를 복사한다.

```javascript
// start 포함, end 미포함, 음수 지원
arr.slice(1, 3)
```

## 구조 분해 할당

객체 디스트럭처링

**프로퍼티 이름을 기반**으로 변수의 값이 할당된다.

```javascript
const product = {
    name: "keyboard",
    amount: 1000
};

// const name = "keyboard"
// const amount = 1000
const { name, age } = product;

// 이름 변경
// const productName = "keyboard"
// const productAmount = 1000
const {
    name: productName,
    amount: productAmount
} = product;

// Rest 패턴
const {
    password,
    ...userInfo
} = user;

// 함수 파라미터에서 구조 분해 할당
function printProduct({name, amount}) {
    console.log(name);
    console.log(amount);
}

printProduct({
    name: "keyboard",
    amount: 1000
});
```

배열 디스트럭처링

**순서를 기반**으로 변수의 값이 할당된다.

```javascript
const arr = [10, 20];

// const a = arr[0]
// const b = arr[1] 
const [a, b] = arr;

const [data, setData] = useState([]);

// 값 변경
[a, b] = [b, a];
```

공통

```javascript
// 일부만 추출
// a = 10
const [a] = [10, 20, 30];

// 특정 값 스킵
// a = 1
// c = 3
const [a, , c] = [1, 2, 3]

// 기본값 지정
// a = 1
// b = 2
// c = 100
const [a, b, c=100] = [1, 2];

// 나머지 요소를 하나의 배열로 만들기 (Rest 패턴, Rest는 반드시 마지막에 와야 함)
// a = 10
// rest = [20, 30, 40]
const [a, ...rest] = [10, 20, 30, 40];
```

## Rest, Spread 문법

Rest 패턴은 나머지 요소를 하나의 배열로 만든다.

반대로 Spread 문법은 배열을 펼친다.

따라서 Spread 문법을 사용하면 `...object`가 어느 위치에 오더라도 문법 오류가 발생하지 않는다.

```javascript
// 객체 spread
const product = {
    name: "keyboard"
};

// { name: 'keyboard', amount: 1000 }
const detail = {
    ...product,
    amount: 1000
}
```

```javascript
// 배열 spread
const arr1 = [1, 2];

// [1, 2, 3, 4]
const arr2 = [...arr1, 3, 4];
```

```javascript
// Rest
// a = 10
// rest = [20, 30, 40]
const [a, ...rest] = [10, 20, 30, 40];
```


## 가변 인자

```javascript
function sum(...nums) {
    return nums.reduce((a, b) => a + b);
}
```


## 템플릿 리터럴

```javascript
const name = "hansanhha";

console.log(`hello ${name}`);
```


## 함수형 프로그래밍

함수형 프로그래밍의 핵심은 데이터를 변환하는 것이다.

```javascript
const numbers = [1, 2, 3];

// 절차지향 방식
const result = [];
for (let i = 0; i < numbers.length; i++>) {
    result.push(numbers[i] * 2);
}

// 함수형 방식
const result = numbers.map(x => x * 2);
```

핵심 개념인 순수 함수, 사이드 이펙트, 불변성, 람다식, 고차 함수, 함수 체이닝에 대해 알아보자.

### 순수함수와 사이드 이펙트

순수 함수란 동일 입력에 대해 동일한 결과를 반환하고, 외부 상태를 변경하지 않는 함수이다.

함수형 프로그래밍은 함수 내부에 입력된 값만 가지고 계산하여 항상 같은 결과를 반환해야 하는 순수 함수를 지향한다.

외부 상태를 변경하거나 외부 세계와 상호작용하여 함수 외부 환경에 영향을 주는 모든 행위를 사이드 이펙트(부작용)이라고 한다.

```javascript
// 함수 바깥의 변수(total)를 사용하지 않고 주어진 파라미터만 가지고 계산한다
// add(1, 1)을 몇 번 호출하든 동일한 값을 반환한다

let total = 0;

function add(a, b) {
    return a + b;
}
```

```javascript
// 외부 상태를 읽는 순수하지 않은 함수
// taxRate의 값에 따라 calculate(100) 호출의 값이 달라지게 되어 입력만으로 결과를 알 수 없다.

let taxRate = 0.1;

function calculate(price) {
    return price * (1 + taxRate);
}
```

```javascript
// 외부 상태를 변경하는 순수하지 않은 함수
let count = 0;

function increase() {
    count++;
}
```

로깅(`console.log`)이나 네트워크 요청(`fetch`, `axios`), 파일 시스템 작업도 모두 사이드 이펙트에 해당한다.

함수형 프로그래밍이라고 해서 이러한 사이드 이펙트를 절대 쓰지 말라는 것이 아니다.

**사이드 이펙트가 일어나는 부분을 최대한 통제하고 격리하여 예측 가능한 코드를 만드는 것이 핵심이다.**

### 람다 표현식

람다식은 이름 없는 함수를 말한다.

자바스크립트는 함수를 1급 객체로 취급하여 함수를 하나의 값으로써 사용할 수 있는데 이 때 문법을 간결하게 축약하여 표현할 수 있는 기법이다.

```javascript
// 기존 함수
function add(a, b) {
    return a + b;
}

// 람다식
(a , b) => {
    return a + b;
}

// 람다 축약
(a, b) => a + b
```

### 고차 함수

함수를 인자로 받거나 함수를 반환하는 함수를 고차 함수라고 한다.

```javascript
// 함수를 인자로 받는 함수
function execute(fn) {
    fn();
}

execute(() => {
    console.log("hello higher order function");
})

// hello higher order function
```

```javascript
// 함수를 반환하는 함수
function createAddFunc(x) {
    return y => x + y;
}

// 화살표 함수
const createAddFunc = x => y => x + y;

const add10 = createAddFunc(10);
add10(5) // 15
```

### 클로저

함수가 다른 함수를 반환할 때 반환되는 함수가 만들어진 주변 환경을 기억하는 현상

클로저를 이용하면 상태를 안전하게 숨기고 관리할 수 있다. (상태 은닉과 캡슐화)

```javascript
function createCounter() {
    let count = 0; // 외부에서 수정할 수 없는 함수 지역변수

    return () => {
        count += 1;   // 클로저: 함수가 만들어진 환경의 변수 count를 캡처한다
        return count;
    }
}

const counter = createCounter();

// 함수 호출 이외에 count 변수에 접근할 수 없다.
counter(); // 1
counter(); // 2
counter(); // 3
```

`createCounter` 함수의 실행이 끝나면 콜 스택이 제거되므로 원래는 `count` 변수가 메모리에서 사라져야 한다.

위의 익명 함수처럼 반환되는 함수가 자신을 생성한 함수 내부의 변수를 참조하는 경우 그 데이터들은 힙 영역으로 이동한다.

콜 스택은 제거되지만 데이터들은 유지되며 클로저에 의해서만 참조될 수 있으므로 상태를 캡슐화할 수 있다.

즉, 전역 변수를 쓰지 않고도 함수 안에 상태를 격리하게 된다.

클로저를 활용하는 대표적인 기법으로 커링과 메모이제이션이 있다.

#### 커링

여러 개의 인자를 받는 함수를 하나의 인자만 받는 함수들의 체인으로 변환하는 기법

```javascript
// b를 받아 a와 곱하는 함수를 반환한다
// 이 때 a는 익명 함수에서 캡처된다 (클로저)
const multiply = a => b => a * b;

function multiply(a) {
    return b => a * b;
}

// a = 2를 기억하는 클로저 함수를 생성한다
const multiplyBy2 = multiply(2);
multiplyBy2(5); // 10
```

#### 메모이제이션

이전에 계산한 값을 저장해두었다가 동일한 계산 요청이 오면 다시 계산하지 않고 저장된 값을 꺼내서 주는 기법

```javascript
// 피보나치 수열
// fibonacci(40)을 구하려면 fibonacci(39)와 fibonacci(38)이 필요하다
// 그런데 fibonacci(30)를 구하는 과정에서도 fibonacci(38)이 또 필요하다 -> 똑같은 계산을 여러 번 반복하기 때문에 성능에 영향을 주게 된다
function fibonacci(n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

fibonacci(40);
```

```javascript
// 클로저를 활용하여 한 번 계산한 값을 저장해둔다
// fibonacciMemo(40)이 실행되는 동안 계산된 모든 중간 결과(fib(38), fib(37) 등)가 cache 객체에 저장된다
// 다음 순회에서 똑같은 숫자를 만나면 재귀 연산을 생략하고 cache[n]으로 값을 가져온다
function createMemoizedFibonacci() {
    const cache = {};

    return function fib(n) {
        if (n in cache) {
            return cache[n];
        }

        if (n <= 1) {
            cache[n] = n;
        } else {
            cache[n] = fib(n - 1) + fib(n - 2);
        }

        return cache[n];
    }
}

const fibonacciMemo = createMemoizedFibonacci();
fibonacciMemo(40);
```

**메모이제이션은 반드시 순수 함수에서만 사용해야 한다**

입력값 `n`이 같을 때 출력값이 항상 같은 함수여야만 이전 결과를 기억해서 재사용할 수 있기 때문이다.

함수 내부에서 외부 변수를 참조하거나 사이드 이펙트가 존재한다면 메모이제이션을 적용했을 때 버그가 발생한다.


### 불변성

함수형 프로그래밍은 원본을 수정하는 대신, 새로 만들어 원본을 건들이지 않는 불변성을 추구한다.

```javascript
// 객체는 가변이므로 직접 변경할 수 있다
const user = {
    name: "hansanhha"
};

const guest = user;
guest.name = "guest";

// user.name 'guest'
// guest.name 'guest'
```

```javascript
// 기존 객체의 값만 가져와 새로운 객체를 만든다
const user = {
    name: "hansanhha"
};

const guest = {
    ...user,
}
guest.name = "guest"

// user.name 'hansanhha'
// guest.name 'guest'
```

```javascript
// 가변
arr.push(4);

// 원본 배열을 수정하는 대신 새로운 배열을 만든다
const newArr = [...arr, 4];
```

### 함수 합성

함수 여러 개를 레고처럼 조립하는 기법

작은 함수를 조합해서 큰 기능을 만들어낸다.

```javascript
const add = (x, y) => x + y;
const multiplyBy2 = x => x * 2;

// 40
// add 함수 -> add 함수 반환 값을 multiplyBy2 함수의 입력값으로 전달
const result = multiplyBy2(add(10, 10));
```

```javascript
// 파라미터 x에 대해 g 함수 실행 -> g 함수 반환 값을 f 함수의 입력값으로 전달하는 함수 반환
const compose = (f, g) => (x, y) => f(g(x, y));

// 위의 add, multiplyBy2 함수를 람다식으로 전달
const calculate = compose(
    x => x * 2,
    (x, y) => x + y
);

// 40
calculate(10, 10);
```

### 함수 체이닝

동일한 객체에서 여러 개의 함수를 점 `.`으로 연결하여 호출하는 기법

함수 합성의 일종이지만 함수 호출이 분리되어 있어 코드를 읽기 편안하다.

```javascript
const result = [1, 2, 3, 4, 5]
                .filter(x => x % 2 === 0)
                .map(x => x * 10)
                .reduce(
                    (sum, x) => sum + x,
                    0
                );
```

```text
[1,2,3,4,5]

↓ filter

[2,4]

↓ map

[20,40]

↓ reduce

60
```


### `map()`

원본 배열의 각 요소를 변환하여 새 배열을 반환하는 함수

배열 변환

```javascript
// numbers [1, 2, 3];
// result [2, 4, 6];
const numbers = [1, 2, 3];
const result = numbers.map(x => x * 2);
```

객체 배열 변환

```javascript
const users = [
    { name: "hansanhha" },
    { name: "guest" },
];

// ['hansanhha', 'guest']
const names = users.map(user => user.name);
```

### `filter()`

조건에 맞는 요소만 추출하는 함수

```javascript
const numbers = [1, 2, 3, 4, 5];

// [2, 4]
const result = numbers.filter(x => x % 2 === 0);
```

### `reduce()`

배열을 하나의 값으로 축약하는 함수

함수의 첫 번째 파라미터: 값을 계산하는 람다식 (람다식의 첫 번째 파라미터는 중간 계산을 담아두는 **누적기(accumulator)**이고 두 번째 파라미터가 배열에서 현재 처리 중인 요소임)

함수의 두 번째 파라미터: 초기값

```javascript
const numbers = [1, 2, 3, 4];

// acc: 중간 계산 보관 (누적기)
// v: 배열에서 현재 처리 중인 요소
// sum: 10
const sum = numbers.reduce((acc, v) => acc + v, 0);
```

```javascript
// 최대값 구하기
// max: 4
const numbers = [1, 2, 3, 4];
const max = numbers.reduce((a, b) => Math.max(a, b));
```

### `find()`

조건에 맞는 요소 중 첫 번째 요소만 반환하는 함수 (filter 함수의 short-circuit 버전)

만족하는 값이 없으면 `undefined`를 반환한다.

```javascript
const users = [
    { id: 1},
    { id: 2}
]

// user: { id: 2}
const user = users.find(u => u.id === 2);
```

### `some()`

조건에 맞는 요소가 하나라도 있으면 `true`를 반환하는 함수

```javascript
const numbers = [1, 2, 3];

// true
numbers.some(x => x > 2);
```

### `every()`

조건을 전부 만족하는 경우에만 `true`를 반환하는 함수

```javascript
const numbers = [1, 2, 3];

// true
numbers.every(x => x > 0);
```

### `sort()`

배열의 요소를 전부 문자열로 변환한 뒤 유니코드 순서로 정렬하는 함수

비교 함수를 매개변수로 제공하지 않으면 '숫자의 값'으로 정렬하지 않는다.

```javascript
const numbers = [1 , 30, 4, 21, 10000];

// [ 1, 10000, 21, 30, 4 ]
// 문자열 "10000"은 "21"이나 "4"보다 첫 글자 '1'의 유니코드 값이 작기 때문에 맨 앞으로 온다
numbers.sort();
```

숫자나 객체를 올바르게 정렬하려면 비교 함수를 파라미터로 전달해야 한다.

```javascript
// [ 1, 4, 21, 30, 10000 ]
numbers.sort((a, b) => a - b);
```

자바스크립트 엔진은 배열에서 두 요소 `a`와 `b`에 대한 비교 함수를 실행할 때 반환되는 값의 양수/음수 여부에 따라 위치를 바꾼다.
- 음수일 때: `a`를 `b`보다 앞에 둔다.
- 0일 때: 순서를 바꾸지 않는다.
- 양수일 때: `a`를 `b`의 뒤에 둔다.

V8 엔진은 내부적으로 팀소트(Timsort)이라는 하이브리드 정렬 알고리즘을 사용한다.

배열을 쪼갠 다음 작은 단위에는 삽입 정렬을 사용하고, 결과를 합칠 때는 병합 정렬 방식을 사용하는 알고리즘이다.

값이 같은 요소들이 있다면 정렬 후에도 해당 요소들의 원래 상대적인 순서를 유지하는 안정 정렬을 보장한다.

**`sort` 함수는 새로운 배열을 만들지 않고 원본 배열 자체를 직접 수정하여 정렬한다.**

### `flat()`

중첩 배열을 하나의 배열로 평탄화하는 함수

```javascript
const numbers = [
    [1, 2],
    [3 ,4]
];

// numbers: [[1, 2], [3, 4]]
// flatted: [1, 2, 3, 4]
const flatted = numbers.flat();
```

### `flatMap()`

`flat()` + `map()`

```javascript
const numbers = [
    [1, 2],
    [3 ,4]
];

// numbers: [[1, 2], [3, 4]]
// flattedMap: [2, 4, 6, 8]
const flattedMap = numbers.flatMap(
    nums => nums.map(x => x * 2)
)
```

## 콜백 함수

콜백 함수는 다른 함수에게 전달되어 나중에 호출되는 함수를 말한다.

보통 네트워크 요청이나 DB 조회와 같이 I/O 작업을 비동기로 처리하기 위해 함수를 호출할 때 콜백 함수도 같이 전달하여 함수의 작업이 끝나면 그 결과를 바탕으로 콜백 함수에서 후처리 작업을 한다.

```javascript
// 사용자가 회원가입을 하면 알림을 보내는 함수를 실행한다
const saveUser(user, notifyCallback) {
    db.saveUser(user);
    notifyCallback();
}

// sendEmail 콜백 함수 전달
saveUser(new User("hansanhha"), sendEmail);
```

위처럼 간단하게 콜백 함수를 사용하면 바람직하지만 콜백 함수를 중첩하면 콜백 함수 내에서 다시 함수를 호출하고, 콜백 함수를 부를 수도 있다.

```javascript
// DB 작업을 한 후 callback 함수를 호출한다고 가정
function getUser(userId, callback) {
    callback({ id: userId, name: "Kim" });
}

function getOrders(userId, callback) {
    callback([{ orderId: 101 },{ orderId: 102 }]);
}

function getProduct(orderId, callback) {
    callback({productId: 5001,name: "MacBook"});
}

function getDelivery(productId, callback) {
    callback({status: "배송중"});
}

// 콜백 지옥
getUser(1, (user) => {
    console.log("user =", user);
    getOrders(user.id, (orders) => {
        console.log("orders =", orders);
        getProduct(orders[0].orderId, (product) => {
            console.log("product =", product);
            getDelivery(product.productId, (delivery) => {
                console.log("delivery =", delivery);
            });
        });
    });
});
```


## `Promise`

콜백 함수 대신 `Promise`를 사용하면 비동기 작업 흐름을 쉽게 제어할 수 있다.

```javascript
Object.getOwnPropertyNames(Promise.prototype);
[ 'constructor', 'then', 'catch', 'finally' ]
```

`then` 프로퍼티: 실행 함수가 성공적으로 완료됐을 때 실행할 콜백 함수 등록 -> 새로운 프로미스 반환

`catch` 프로퍼티: 실행 함수가 실패했을 때 실행할 콜백 함수 등록 -> 새로운 프로미스 반환

`finally` 프로퍼티: 실행 함수의 실행 성공 여부와 관계없이 실행할 콜백 함수 등록 -> 새로운 프로미스 반환

프로미스를 사용하여 비동기 작업을 처리하려면 프로미스 객체를 생성할 때 실행 함수를 전달해야 한다. (실행 함수를 `executor`라고 함)

실행 함수의 바디에는 비동기 처리할 코드를 작성하고, 파라미터에는 프로미스의 콜백 함수를 선언한다.

첫 번째 파라미터는 비동기 작업이 성공했음을 프로미스에게 알려주고, 두 번째 파라미터는 작업이 실패했음을 알려주는 용도로 사용된다.

아래의 코드는 서버로부터 데이터를 조회했을 때 데이터가 있으면 프로미스의 성공 콜백 함수(`resolve`)를 호출하고, 없으면 실패 콜백 함수(`reject`)를 호출한다.

```javascript
const fetchData = new Promise((resolve, reject) => {
    const data = fetch();

    if (data) resolve(data);
    else reject();
});
```

`resolve`를 호출하면 `resolve`는 전달받은 인자값을 `then`에 등록된 콜백 함수를 호출하며 전달한다.

`reject`를 호출하면 동일한 메커니즘으로 `catch`를 호출한다.

```javascript
fetchData
    .then((data) => {
        // 비즈니스 로직
    })
    .catch((error) => {
        // 예외 처리 로직
    })
    .finally(() => {
       // 반드시 호출되는 로직 
    });
```

### 프로미스 체이닝

`then` 메서드는 콜백 함수를 전달받은 뒤 새로운 프로미스를 생성하여 반환한다.

이 때 `then` 메서드에서 값을 반환하면 반환값이 새로 생성할 프로미스에 포함된다.

반환된 프로미스에서 다시 `then` 메서드를 호출하고 프로미스 객체를 만드는 과정을 반복하여 **프로미스 체이닝**을 구성할 수 있다.

```javascript
fetchData
    .then((data) => {
        // 비즈니스 로직
    })
    .then((data) => {
        // 비즈니스 로직 (이전 then 메서드 반환값을 파라미터로 전달받음)
    })
    .then((data) => {
        // 비즈니스 로직 (이전 then 메서드 반환값을 파라미터로 전달받음)
    })
    .catch((error) => {
        // 예외 처리 로직 (프로미스 executor 또는 이전 then 메서드 예외를 파라미터로 전달받음)
    })
    .finally(() => {
       // 반드시 호출되는 로직 
    });
```

`then` 메서드에서 콜백 함수를 실행하다가 예외가 발생하면 `catch` 메서드가 호출되어 예외 처리 로직이 실행된다.

`catch` 메서드 다음에 `then` 메서드가 있으면 순차적으로 이어서 호출된다.

```javascript
// catch 메서드에서 예외 처리 후 비즈니스 로직 재개
fetchData
    .then((data) => {
        // 비즈니스 로직
    })
    .catch((error) => {
        // 예외 처리 로직 (프로미스 executor 또는 이전 then 메서드 예외를 파라미터로 전달받음)
    })
    .then((data) => {
        // 비즈니스 로직
    })
    .then((data) => {
        // 비즈니스 로직
    })
    .finally(() => {
       // 반드시 호출되는 로직 
    });
```

### `Promise` 상태

프로미스 객체가 생성되면 곧바로 실행 함수를 실행하는데, 실행 함수에 포함된 비동기 작업 결과에 결과에 따라 3가지 상태로 구분된다.

가장 먼저 `Pending` 상태가 되며 그 다음 `Fulfilled` 또는 `Rejected` 상태로 단 한 번만 상태가 변경된다.

`Pending`(대기): 비동기 작업이 처리되고 있는 상태

`Fulfilled`(이행, 성공): 비동기 작업이 성공적으로 완료된 상태 (`then` 실행)

`Rejected`(거부, 실패): 비동기 작업이 실패한 상태 (`catch` 실행)

`Setteled`: 프로미스의 처리가 완료된 상태 (성공 또는 실패)

### `fetch` API

`fetch` 함수는 서버에 HTTP 요청을 보내는 프로미스 객체를 반환한다.

`fetch`를 사용하면 매번 직접 프로미스 객체를 반환하는 함수를 작성할 필요가 없다.

```javascript
const users = fetch("/users")
```

### `Promise.resolve()`, `Promise.reject()`

프로미스는 `resolve`와 `reject`를 정적 메서드로 제공한다.

이를 활용하면 비동기 작업을 하지 않더라도 프로미스 객체를 생성할 수 있다.

`Promise.resolve()`: 성공(Fulfilled) 상태의 프로미스를 생성한다.

`Promise.reject()`: 실패(Rejected) 상태의 프로미스를 생성한다.

```javascript
Promise.resolve('성공')
       .then(result => {console.log(result)});

// 위 코드는 아래 코드와 동일하다
new Promise((resolve, reject) => {
    resolve("성공");
})
.then(result => {console.log(result)});
```

```javascript
Promise.reject("실패")
       .catch(error => {console.log(error);});

// 위 코드는 아래 코드와 동일하다
new Promise((resolve, reject) => {
    reject("실패");
})
.catch(error => {console.log(error)});
```

## `async`, `await`

`async`와 `await`는 프로미스(비동기 코드)를 동기식 코드처럼 작성할 수 있게 해주는 syntatic sugar이다.

`async`: `async` 키워드가 붙은 함수는 무조건 프로미스를 반환한다. 함수 내부에서 일반 값을 반환하더라도 자바스크립트 엔진은 `Promise.resolve(값)`으로 감싸서 반환한다.

`await`: 반드시 `async` 함수 내부에서만 사용할 수 있다. 프로미스의 처리가 완료(Setteld)될 때까지 함수의 실행을 일시 중지하고 프로미스가 해결되면 결과 값을 받아온 다음, 실행을 재개한다.

```javascript
const delay = () => new Promise(resolve => setTimeout(resolve, 1000)).then(() => console.log("delay 프로미스 then 호출"));

async function myAsyncFunc() {
    console.log("3. async myAsyncFunc 함수 내부 시작");
    console.log("4. await delay 함수 호출");
    await delay();
    console.log("5. await delay 완료 후 실행");
}

console.log("1. 메인 코드 시작");
console.log("2. async myAsyncFunc 함수 호출");
myAsyncFunc();
console.log("4. 메인 코드 끝 (async 함수 호출 후 바로 실행)");
```

**실행 흐름**
1. 메인 코드 시작
2. `myAsyncFunc` 호출
3. `await delay()`를 만나면 프로미스가 생성되고 `myAsyncFunc`의 실행은 그 즉시 일시 정지되며 제어권이 메인 코드로 돌아간다. (자바스크립트 엔진은 `myAsyncFunc`의 실행 컨텍스트를 메모리에 그대로 저장함)
4. 메인 코드 실행 재개
5. `await`의 프로미스가 완료되면 함수의 실행을 재개하라는 명령이 마이크로태스크 큐에 들어간다.
6. 이벤트 루프에 의해 큐에 쌓인 함수 실행이 재개되어 `myAsyncFunc` 함수의 상태가 복구되며 중지된 시점의 다음 줄부터 실행을 이어간다.

프로미스 체이닝에서는 `.catch`를 붙였지만 `async/await` 구조에서는 일반적인 동기 코드처럼 `try...catch`문을 사용할 수 있다.

`await` 프로미스가 `reject`되면 그 에러가 `catch` 블록을 실행시킨다.

```javascript
async function fetchData() {
    try {
        const response = await fetch(...);
        const data = await response.json();
        return data;
    } catch (error) {
        console.log(error);
    }
}
```


## 모듈(ESM)

모듈은 파일을 기능별로 쪼개어 독립된 파일로 관리하는 방식을 말한다.

모듈 규칙
- 모듈 내부의 코드는 자동으로 `'use strict'`가 켜진 채로 실행된다.
- 모듈 내부에서 선언된 변수는 전역 변수가 아니다. `export`를 해야 외부에서 볼 수 있다.
- 동일한 모듈을 여러 파일에서 여러 번 `import` 하더라도 해당 모듈 파일은 딱 한 번만 실행되고, 나머지는 캐시로 공유된다.
- `import`문은 파일의 맨 위에만 위치해야 한다. 자바스크립트 엔진은 컴파일 단계에서 모듈을 미리 파악하여 최적화를 수행한다.

`import`, `export`: 자바스크립트 표준(ECMAScript Modules, ESM)

모듈을 내보내는 방식은 크게 두 가지로 나뉜다.

**Named Export**

하나의 `.js` 파일에서 여러 개의 변수, 함수, 클래스를 내보낼 때 사용하는 방식

`import`하는 `.js` 파일은 내보낼 때 이름과 맞춰야 한다.

```javascript
export const PI = 3.141592;
export function add(a, b) {
    return a + b;
}
```

```javascript
// 중괄호 안에 가져올 요소의 이름을 명시한다
import { PI, add } from './math.js';
```

**Default Export**

하나의 `.js` 파일에서 단 하나의 값을 내보낼 때 사용하는 방식

가져올 때 이름을 마음대로 바꿀 수 있다.

```javascript
const product = { name: 'keyboard', amount: 1000 };
export default product;
```

```javascript
// 중괄호 없이 가져오며 이름을 바꿀 수 있다
import Keyboard from './product.js';
```

`import`문은 아래와 같이 다양하게 활용할 수 있다.

```javascript
// 가져오는 이름이 현재 파일의 변수명과 충돌할 때 사용한다
import { add as sum } from './math.js';
```

```javascript
// 모듈 안의 모든 내용을 하나의 객체로 묶어서 가져온다
import * as MathAPI from './math.js';
console.log(MathAPI.PI); // 3.141592
```

```javascript
//  필요한 경우에만 모듈을 로드할 수 있다(Code Splitting)
if (condition) {
  const module = await import('./math.js');
}
```

### CommonJS

과거 자바스크립트는 파일 분할 개념이 없어 모든 스킓트가 전역 공간을 공유했다.

ESM이 도입되기 전 브라우저와 서버 환경에서 자바스크립트 코드를 모듈화하기 위해 CommonJS와 AMD라는 모듈 시스템이 사용되었다.

CommonJS는 Node.js가 기본으로 채택한 방식으로 동기적으로 모듈을 로드한다. (파일을 다 읽을 때까지 다음 코드 실행이 멈춤)

로컬 디스크에서 파일을 바로 읽어올 수 있는 서버 사이드 환경에서 주로 사용되었다.

```javascript
// math.js (내보내기)
function add(a, b) { return a + b; }
module.exports = { add };

// main.js (가져오기)
const { add } = require('./math.js');
console.log(add(1, 2));
```

### AMD

AMD(Asynchronous Module Definition)은 네트워크를 통해 자바스크립트 파일을 다운로드해야 하는 브라우저 환경을 위해 고안된 방식이다.

CommonJS처럼 동기적으로 파일을 읽으면 브라우저 실행이 멈추기 때문에 비동기적으로 모듈을 로드하도록 설계되었다.

```javascript
// math.js (내보내기)
define([], function() {
  return {
    add: function(a, b) { return a + b; }
  };
});

// main.js (가져오기)
require(['./math'], function(math) {
    console.log(math.add(1, 2));
});
```