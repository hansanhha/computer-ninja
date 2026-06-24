## 리액트 핵심
- [리액트 핵심](#리액트-핵심)
- [컴포넌트](#컴포넌트)
- [JSX (Javascript XML)](#jsx-javascript-xml)
- [상태](#상태)
- [Props](#props)
- [상태, 훅, Props 작명 컨벤션](#상태-훅-props-작명-컨벤션)
- [리렌더링과 커밋](#리렌더링과-커밋)
- [상태 비동기 배치 업데이트](#상태-비동기-배치-업데이트)


리액트는 웹 페이지 전체를 매번 그리는 대신 변경된 부분만 최소한으로 그리기 위해 만들어졌다.

브라우저는 DOM 트리를 통해 화면을 그리는데, 리액트는 브라우저의 메모리에 가상 DOM을 하나 만든다.

그리고 데이터가 바뀌면 새로운 가상 DOM을 만들고 이전 가상 DOM과 비교하여 바뀐 부분만 브라우저의 실제 DOM 트리에 접근하여 수정한다.

리액트는 화면을 그리는 엔진일 뿐이므로 개발자는 라우팅(화면 이동), 상태 관리, 스타일, 빌드 도구는 별도로 사용해야 한다.

핵심 컴포넌트를 알아보자.


## 컴포넌트

화면 요소인 버튼, 입력창 등을 미리 만들어 하나의 조각으로 관리하는데 이를 컴포넌트라고 한다.

jsx 파일에서 JSX를 반환하는 함수를 통해 컴포넌트를 만들 수 있다. 

이들을 재사용하고 조립하여 화면을 구성한다.

```javascript
// Profile.jsx

// 1. 파일 내부에서만 사용하는 컴포넌트
function Avatar() {
  return <img src="user.png" alt="프로필" className="w-10" />;
}

// 2. 파일 내부에서만 사용하는 컴포넌트
function UserInfo() {
  return (
    <div>
      <h2>hansanhha</h2>
    </div>
  );
}

// 3. 밖으로 내보낼 메인 컴포넌트 (export default는 파일당 단 하나만 가능함)
export default function Profile() {
  return (
    <div className="card-container">
      <Avatar />
      <UserInfo />
    </div>
  );
}
```

컴포넌트 관련 규칙
- 컴포넌트 함수의 첫 글자는 반드시 대문자여야 리액트가 인식할 수 있다.
- 다른 컴포넌트를 품고 있는(다른 컴포넌트 함수를 호출하는) 컴포넌트가 부모, 그 안으로 들어가서 조립되는 컴포넌트(다른 컴포넌트 함수에 의해 호출되는 함수)가 자식이다
- `Profile()`: 부모 컴포넌트
- `Avatar()`, `UserInfo()`: 자식 컴포넌트 


## JSX (Javascript XML)

자바스크립트 안에서 HTML과 유사한 마크업을 작성할 수 있도록 해주는 자바스크립트 확장 문법이다.

HTML과 자바스크립트 파일을 따로 관리해야 하지만, JSX를 쓰면 하나의 컴포넌트 파일 안에서 UI 디자인과 데이터 처리를 동시에 할 수 있다.

HTML처럼 보이지만 브라우저가 바로 읽을 수 있는 문법이 아니고 빌드 과정에서 바벨같은 컴파일러가 JSX를 일반 자바스크립트 함수 호출 형태로 변환한다.

변환된 함수들은 브라우저 메모리에 가상 DOM 객체를 생성한다.

JSX 작성 규칙
- 반드시 모든 요소를 감싸는 최상위 부모 태그가 있어야 한다. 부모 태그를 남기지 않으려면 빈 태그 `<></>`(리액트 프래그먼트)를 사용한다.
- `<input />`, `<br />`처럼 닫는 태그를 작성해야 한다.
- 자바스크립트 예약어와 충돌을 방지하기 위해 카멜 케이스를 사용한다. (HTML의 `class`는 `className`으로, `onClick`은 `onClick`으로 작성)
- JSX 내부에 자바스크립트 변수나 연산식을 사용하려면 중괄호로 감싼다 (`<h1>Hello {name}</h1>`)


## 상태

컴포넌트 내부에서 바뀔 수 있는 동적 데이터를 상태라고 한다.

상태가 바뀔 때마다 화면을 자동으로 다시 그리게 하는 리렌더링을 트리거한다.

일반 변수: 값이 바뀌어도 리액트가 감지하지 못하므로 화면이 그대로 있는다.

상태: 값이 바뀌면 리액트가 감지하고 변경된 부분만 화면을 새롭게 업데이트한다.

리액트에서 제공하는 함수를 `Hook`이라고 하는데, `useState` 훅을 사용하여 상태를 생성하고 관리한다.

```javascript
import { useState } from 'react';

function Counter() {

    // [상태값, 상태 변경 함수] = useState(초기값)
    const [count, setCount] = useState(0);

    return (
        <div>
            <p>카운트: {count}</p>
            // 버튼 클릭 시 상태 변경 함수 호출 -> 리렌더링
            <button onClick={() => setCount(count+1)}>증가</button>
        </div>
    );
}
```

**상태에 대한 핵심 규칙 3가지**

불변성 유지
- 배열이나 객체 타입의 상태를 변경할 때는 기존 데이터를 수정하면 리액트가 감지하지 못한다.
- 기존 객체를 복사하여 새로운 객체로 교체해야 리액트가 감지할 수 있다.

[비동기적 배치 업데이트](#상태-비동기-배치-업데이트)
- 리액트는 성능 최적화를 위해 상태 변경 요청을 모았다가 한 번에 처리한다

하향식 데이터 흐름
- 상태는 부모 컴포넌트에서 자식 컴포넌트로만 전달될 수 있다.
- 자식이 부모의 상태를 직접 수정할 수 없다.


## Props

부모 컴포넌트의 상태를 자식 컴포넌트에게 전달하는 보관함을 Props(프로퍼티)라고 한다.

부모가 자식들에게 서로 다른 상태를 넘겨주어 같은 자식 컴포넌트라도 다르게 보이게 만들 수 있다.

자식 컴포넌트 함수의 첫 번째 파라미터는 항상 프로퍼티를 전달하는 객체로 고정되어 있다. (파라미터 식별자 이름은 변경할 수 있음)

```javascript
// 부모 컴포넌트의 props를 전달받는다
function Username(props) {
    return (
        <div>
            <div>hello {props.name}</div>
        </div>
    );
}

export default function App() {
    return (
        <div>
            <Username name="hansanhha" />
            <Username name="guest" />
        </div>
    );
}
```

부모가 자신의 상태를 바꿀 수 있는 함수를 자식에게 전달하면, 자식이 부모의 상태를 바꿀 수 있다.

```javascript
import { useState } from 'react';

// 부모의 상태를 변경하는 함수 사용
function ChildButton(props) {
    return <button onClick={props.onIncrease}>버튼 클릭</button>
}

export default function Parent() {
    const [count, setCount] = useState(0);

    return (
        <div>
            <div>부모 상태값: {count}</div>
            // `onIncrease` 함수 전달
            <ChildButton  onIncrease={() => setCount(count + 1)} />
        </div>
    );
}
```

일반적으로 파라미터를 `props`로 받기 보다는 구조 분해 할당 문법을 사용하여 자식 컴포넌트에서 받으려고 하는 prop을 고른다.

객체 구조 분해 할당은 프로퍼티 이름을 기준으로 매핑되므로 부모 컴포넌트에서 전달하는 프로퍼티 이름과 일치하지 않으면 `undefined`가 할당된다.

```javascript
function Product({name, amount }) {
    return (
        <div>
            <div>{name}</div>
            <div>{amount}</div>
        </div>
    );
}
```

## 상태, 훅, Props 작명 컨벤션

리액트 훅을 쓸 때
- `[something, setSomething] = use~();`
- `const [userList, setUserList] = useState([]);`
- `const [isLoading, setIsLoading] = useState(false);`

컴포넌트 함수 내에서 이벤트(클릭, 변경 등)를 직접 처리하는 함수를 정의할 때
- `handle` + 행동

Props로 함수를 넘길 때
- `on` + 행동


```javascript
function Parent() {
    const [data, setData] = useState("");

    // 
    const handleDelete = () => {
        setData("");
    };

    return <ChildButton onDelete={handleDelete} />;
}

function ChildButton({ onDelete }) {
  return <button onClick={onDelete}>삭제</button>;
}
```

## 리렌더링과 커밋

렌더링: 함수가 다시 실행되어 새로운 가상 DOM을 생성하는 것

커밋: 실제 DOM에 반영하는 것

리렌더링은 컴포넌트의 props나 상태 또는 컨텍스트가 변경되면 해당 컴포넌트의 함수(또는 클래스의 `render`)가 다시 실행되어 리액트가 새로운 가상 DOM을 만들고, 이전 가상 DOM과 비교(reconciliation)하여 변경된 부분만 브라우저의 실제 DOM에 적용한다.

재조정(reconciliation): 리액트가 이전 요소와 새 요소의 타입과 키를 비교해 변경 범위를 좁히고 필요한 경우만 실제 DOM을 업데이트하는 기법

타입
- DOM 요소면 문자열 `div`, `span`, `input`
- 컴포넌트면 함수나 클래스 값 `MyButton`, `Profile`
- 프래그먼트면 `React.Fragment`

키
- 키는 주로 리스트 반복에서 사용된다.
- 모든 리스트 아이템의 `id`값들이 이전 렌더와 다음 렌더에서 동등한지 판단한다.
- 타입이 같아도 키가 다르면 언마운트 후 재마운트가 발생한다.

```javascript
// 타입: TodoItem
// 키: item.id
items.map(item => (
    <TodoItem key={item.id} item={item} />
))
```

리렌더링을 트리거하는 것들
- 상태 변경: `useState`의 상태 업데이트
- props 변경: 부모가 전달하는 값이 바뀌면 자식이 리렌더링된다.
- 강제 업데이트: `forceUpdate()` 또는 `flushSync`로 강제 반영하는 방법
- 부모 리렌더: 부모가 리렌더링되면 기본적으로 자식도 렌더링 함수가 호출된다. (props 변화 여부, `React.memo` 여부에 따라 실제 DOM 업데이트 발생 여부가 달라짐)

부모 상태 변경 -> 자식 렌더 함수를 호출할 수 있음(안할 수도 있음)

자식 상태 변경 -> 부모 렌더 함수 호출 X


## 상태 비동기 배치 업데이트

리액트는 성능을 높이기 위해 여러 상태 업데이트를 하나의 렌더로 합치는 비동기적 배치 처리를 한다. (단, 실제 DOM이 항상 비동기적으로 업데이트되지는 않음)

리액트 17까지는 이벤트 핸들러 내부에서만 배치가 적용되었으나, 리액트 18부터 자동 배치가 도입되어 프로미스, 타이머, 네이티브 이벤트 등 비동기 구간에서도 여러 `setState` 호출을 하나로 묶어 단일 렌더로 처리한다.

```javascript
// 연속으로 3번 호출해도 count의 값은 1만 증가한다
// 같은 이벤트 안에서 상태가 배치되기 때문 -> count 값에 대해 동일한 업데이트가 세 번 예약된 것 뿐이다
setCount(count + 1);
setCount(count + 1);
setCount(count + 1);

// 함수형식으로 작성하면 이전 상태를 기반으로 값을 업데이트할 수 있다
setCount(prevCount => prevCount + 1);
```

```javascript
// 이벤트/프로미스/타이머에서 여러 개의 상태 변경을 발생시키면 한 번의 렌더만 발생한다 (리액트 18+)
// 각각의 서로 다른 이벤트/프로미스/타이머라면 별도 배치로 동작한다
onClick = () => {
    setCount(c => c + 1);
    setOther(v => v + 10);
}
```

```javascript
import { startTransition } from 'react';

someHandler = () => {
    setQuery(q); // 즉시 반영

    // 우선순위가 낮은 업데이트로 표시한다
    // 긴 작업을 비동기로 처리할 때 사용
    startTransition(() => {
        setLargeList(filtered); 
    });
}
```