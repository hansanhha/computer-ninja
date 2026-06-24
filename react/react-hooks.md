## 리액트 훅
- [`useState`](#usestate)
- [`useEffect`](#useeffect)
  - [사용법](#사용법)
  - [`useEffect` 내부에서 `async/await`를 사용하는 방법](#useeffect-내부에서-asyncawait를-사용하는-방법)
- [`useRef`](#useref)
  - [사용법](#사용법-1)



## `useState`

`useState` 훅은 컴포넌트가 기억해야 하는 데이터를 관리한다.

리액트 컴포넌트는 함수에 불과하므로 함수 실행이 종료되면 지역변수도 함께 사라진다.

```typescript
function Counter() {
    let count: number = 0

    return (
        <>
            <h1>{count}</h1>
            <button onClick={() => count++}>increase</button>
        </>
    )
}
```

```text
Counter 함수 실행
↓
count 생성
↓
함수 실행 종료
↓
count 제거 (Counter 함수가 가진 렉시컬 환경을 참조하는 하위 렉시컬 환경이 없는 경우)
```

하지만 UI는 사용자의 입력에 따라 상태가 계속 유지될 수 있어야 한다.

**리액트는 함수의 실행이 종료되더라도 유지되어야 하는 상태를 컴포넌트 외부의 'Fiber'에 저장한다.**

```typescript
import { useState } from 'react'

function Counter() {
    const [count, setCount] = useState(0);

    return (
        <>
            <h1>{count}</h1>
            <button onClick={() => setCount((count) => count + 1)}>increase</button>
        </>
    )
}

export default Counter
```

동작 과정을 살펴보자.

최초 렌더링

```text
Counter() 최초 실행
    ↓
useState(0) (count = 0)
    ↓
Fiber에 0 저장
    ↓
[count, setCount] 반환

Fiber
 └─ Hook
     └─ state = 0
```

버튼 클릭 시

```text
버튼 클릭
    ↓
setCount(1)
    ↓
업데이트 큐에 등록
    ↓
리액트 스케줄러 감지
    ↓
상태 업데이트 함수 실행
    ↓
count = 1 설정
    ↓
컴포넌트 함수 재실행
    ↓
가상 DOM 생성
    ↓
변경점 비교(Diff) 
    ↓
실제 DOM에 반영
```

`useState` 훅을 간단하게 구현하여 이 훅의 동작 방식을 살펴보자.

```typescript
// setCount(10)
// setCount(prev => prev + 1) 둘 다 허용
type SetStateAction<T> =
    | T
    | ((prevState: T) => T);

interface Update<T> {
    action: SetStateAction<T>;
}

interface Hook<T> {
    state: T;
    queue: Update<T>[];
}

// 리액트 Fiber 흉내
const hooks: Hook<unknown>[] = [];
let currentHookIndex = 0;

// useState 구현
function useState<T>(initialState: T): [T, (action: SetStateAction<T>) => void] {
    const hookIndex = currentHookIndex;

    if (!hooks[hookIndex]) {
        hooks[hookIndex] = {
            state: initialState,
            queue: [],
        };
    }

    const hook = hooks[hookIndex] as Hook<T>;

    hook.queue.forEach(update => {
        const action = update.action;

        hook.state = 
            typeof action === "function"
            ? (action as (prevState: T) => T)(hook.state)
            : action;
    });

    hook.queue = [];

    const setState = (action: SetStateAction<T>): void => {
        hook.queue.push({action});
        render();
    }

    currentHookIndex++;
    return [hook.state, setState];
}

function render(): void {
    currentHookIndex = 0;
    App();
}

// setCount 훅을 호출할 때마다 render 함수로 인해 App 함수가 호출된다.
// App 함수의 count 상태는 hooks에서 관리되며 setCount로 상태를 업데이트할 때마다 업데이트된 값을 반환받는다.
// 컴포넌트 함수는 매번 실행되며 지역 변수도 매번 새로 만들어진다.
// 즉, 상태는 컴포넌트 함수 안에 있는 게 아니라 리액트 Fiber가 관리한다.
function App(): void {
    const [count, setCount] = useState<number>(0);
    const [name] = useState<string>("hansanhha");

    console.log("count:", count, "name:", name);

    // 주석 해제 후 실행 (아래 조건문 주석)
    // if (count < 5) {
    //     setTimeout(() => {
    //         setCount(prev => prev + 1);
    //     }, 1000);
    // }

    if (count === 0) {
        setCount(prev => prev + 1);
        setCount(prev => prev + 1);
        setCount(prev => prev + 1);
    }
}

// 초기 렌더링
render();
```

`useState`의 핵심은 다음과 같다.

상태는 컴포넌트 함수 안에 저장되지 않고 리액트의 Fiber에 의해 관리된다. (클로저 개념이 아님)

`useState`는 Fiber에 저장되어 있는 상태를 찾아 반환한다.

`setState()`는 상태를 직접 변경하는 게 아니라 '업데이트'를 등록한다.

리액트는 등록된 업데이트를 처리한 후 컴포넌트 함수를 다시 실행한다. -> 업데이트된 상태 값이 반환되고, 이를 기반으로 가상 DOM을 생성한다.


## `useEffect`

`useEffect`는 컴포넌트가 렌더링될 때마다 특정 작업(Side Effect)을 수행하도록 설정할 수 있는 훅이다.

여기서 사이드 이펙트란 컴포넌트가 화면에 그려지는 렌더링 외에 외부 세계와 상호작용하는 모든 작업을 말한다.

```text
컴포넌트 마운트: 처음 화면에 나타날 때 실행

컴포넌트 업데이트: 특정 데이터(State, Props)가 바뀔 때 실행

컴포넌트 언마운트: 화면에서 사라질 때 실행 (정리 작업)
```

리액트의 함수형 컴포넌트는 UI 렌더링 역할만 수행한다.

렌더링 이외의 데이터 페칭, 타이머 실행 등 외부 시스템과 동기화 해야 하는 나머지 작업들은 `useEffect` 내부에서 처리한다.

```text
리액트 내부 상태 (useEffect)
      ↕
브라우저, 서버, 타이머, 웹소켓, 이벤트, localStorage
```

리액트는 `useEffect`에 넘겨진 콜백 함수를 렌더링 도중에 실행하지 않고 메모리에 잠시 키핑해둔다.

리액트가 컴포넌트 함수를 실행해 가상 DOM을 만들고 실제 DOM에 커밋하여 브라우저가 실제로 화면을 그리고 나면, 키핑해둔 `useEffect` 콜백 함수를 실행한다.

즉, 렌더링 과정을 모두 마친 후 부수적인 작업을 마치기 때문에 사용자는 빠른 UI를 경험할 수 있다.

### 사용법

```typescript
useEffect(
    () => {}, // Effect Callback 
    [] // Dependency Array
); 
```

`useEffect`는 총 두 개의 파라미터를 받는다.

첫 번째 파라미터
- 이펙트 콜백 함수
- 컴포넌트가 렌더링된 후 실행할 실제 작업을 담고 있는 함수
- 이 함수는 아무것도 반환하지 않거나 Cleanup 함수만 반환해야 한다.
- `async` 함수는 `Promise`를 반환하므로 첫 번째 인자로 직접 들어올 수 없다.

두 번째 파라미터
- 의존성 배열
- 리액트는 전달한 배열 안에 있는 값이 바뀔 때만 첫 번째 인자의 함수를 다시 실행한다.

두 번째 인자를 어떻게 전달하느냐에 따라 `useEffect` 실행 타이밍이 결정되는데 크게 3가지로 나뉜다.

**배열을 아예 생략한 경우** (`undefined`)

`useEffect(() => { ... })`

컴포넌트가 처음 나타날 때(마운트)는 물론, 컴포넌트 내부의 어떤 상태나 프롭스가 바뀌어 리렌더링될 때마다 매번 실행된다.

**빈 배열을 넣은 경우** (`[]`)

`useEffect(() => { ... }, [])`

컴포넌트가 화면에 처음 나타날 때만 실행된다.

보통 컴포넌트 초기화, 최초 API 호출, `window` 객체에 글로벌 이벤트 리스너를 단 한 번만 등록할 때 사용한다.

**배열에 특정 값을 넣은 경우** (`[value1, value2]`)

`useEffect(() => { ... }, [count, name])`

처음 마운트될 때 실행되고, 그 이후 리렌더링될 때 배열 안에 담아둔 변수 `count` 또는 `name` 중 하나라도 값이 변경되면 이펙트 콜백 함수가 실행된다.

리액트는 얕은 비교 알고리즘 `Object.is()`를 사용하여 이전 렌더링 때의 값들과 하나씩 비교한다.

특정 데이터의 변화에 종속된 작업(유저 아이디가 바뀔 때마다 해당 유저 프로필을 다시 조회한다던지)을 처리할 때 사용한다.

**컴포넌트가 마운트될 때 데이터 페칭**

```typescript
import { useState, useEffect } from 'react';

// API 응답 데이터 타입 정의    
interface User {
    id: number;
    name: string;
    email: string;
}

export function UserProfile {
    const [user, setUser] = useState<User | null>(null);

    useEffect(() => {
        fetch(`https://api.server.com/users/${id}`)
        .then((res) => res.json())
        .then((data: User) => setUser(data))
        .catch((err) => console.error(err));
    }, []);

    if (!user) return <div>로딩 중</div>;

    return <div>Hello {user.name}</div>;
}
```

**특정 값이 바뀔 때 실행**

```typescript
import { useState, useEffect, ChangeEvent } from 'react';

export function SearchComponent() {
    const [query, setQuery] = useState<string>('');

    useEffect(() => {
        if (query === '') return;

        fetch(`https://api.server.com/search?q=${query}`);
    }, [query]);

    const handleInputChange = (e :ChangeEvent<HTMLInputElement>) => {
        setQuery(e.target.value);
    }

    return (
        <input type="text" value={query} onChange={handleInputChange} place="검색어를 입력하세요"/>
    );
}
```

**타이머 사용**

`useEffect` 함수는 정리 함수만 반환하거나 아무것도 반환하지 않아야 한다.

```typescript
import { useState, useEffect } from 'react';

export function TimerComponent() {
    const [count, setCount] = useState<number>(0);

    useEffect(() => {
        const timer: number = window.setInterval(() => {
            setCount((prev) => prev + 1);
        }, 1000);

        return (): void => {
            clearInterval(timer);
        }
    }, []);

    return <div>경과 시간: {count}</div>초;
}
```

### `useEffect` 내부에서 `async/await`를 사용하는 방법

`useEffect(async () => {...})` 의 형태로 `async/await`를 `useEffect` 자체에 붙이면 `Promise`를 반환하게 된다. 

리액트는 Cleanup 함수인 줄 알고 `Promise`를 실행하려다가 에러를 발생시킨다.

이를 해결하기 위해 내부에 별도의 `async` 함수를 선언하고 즉시 호출하는 패턴을 사용한다.

```typescript
import { useState, useEffect } from 'react';

interface Post {
    id: number;
    title: string;
    body: string;
}

export function PostDataFetcher() {
    const [post, setPost] = useState<Post | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {

        // useEffect 내부에 별도의 비동기 함수를 호출한다
        const fetchPostData = async (): Promise<void> => {
            try {
                setLoading(true);
                const response = await fetch('https://api.server.com/posts/1');

                if !(response.ok) {
                    throw new Error('failed fetching data');
                }

                const data: Post = await response.json();
                setPost(data);
            } catch (err) {
                if (err instanceof Error) {
                    setError(err.message);
                } else {
                    setError('unknown error');
                } finally {
                    setLoading(false);
                }
            }
        }

        // 선언한 비동기 함수를 즉시 호출한다
        fetchPostData();

    }, []); // 마운트 시점에 한 번만 실행한다

    if (loading) return <div>로딩 중</div>;
    if (error) return <div>에러 발생: {error}</div>;
    if (!post) reutrn null;

    return (
        <article>
            <h1>{post.title}</h1>
            <p>{post.body}</p>
        </article>
    )
}
```

컴포넌트 마운트부터 `useEffect` 내부에 선언된 `async/await` 함수의 전체 실행 과정

최초 렌더링

```text
1. PostDataFetcher() 컴포넌트 함수 실행
2. 상태 초기화 (post=null, loading=true, error=null)
3. 리액트 메모리에 useEffect 콜백 함수 등록
4. 조건문 if (loading) 부합 -> return <div>로딩 중</div> 반환
```

브라우저 페인팅 및 `useEffect` 실행

```text
1. 리액트가 실제 DOM에 반영하고 브라우저가 화면 그리기
2. 화면 렌더링 후 예약되어 있던 useEffect 콜백 함수 실행
3. 내부에서 fetchPostData 비동기 함수 즉시 실행
4. 함수 내부의 `setLoading(true)` 호출
```

`await` 대기

```text
1. await fetch(...) 호출
2. await는 네트워크 요청이 끝날 때까지 비동기적으로 기다리므로, 자바스크립트는 fetchPostData 함수의 실행을 일시정지한다.
3. 이전 단계에서 `setLoading(true)`를 호출하였던 상태 변경을 적용하여 리렌더링을 시도한다. (loading이 원래 true였으므로 UI 변화는 일어나지 않음)
```

네트워크 요청 완료 및 상태 변경

```text
1. await fetch() 데이터 다운로드 완료
2. setPost(data)와 setLoading(false)가 차례대로 호출된다.
3. fetchPostData 함수가 종료된다.
```

화면 렌더링

```text
1. 상태가 바뀌었으므로 리액트가 리렌더링을 시작한다.
2. PostDataFetch() 함수가 다시 실행된다.
3. lading이 false이고 post에 데이터가 있으므로 최하단의 <artcle> 태그 내용이 반환된다.
```

위의 `fetchPostData`를 아래와 같이 즉시 함수 실행(Immediately Invoked Function Expression, IIFE) 스타일로 작성할 수도 있다.

```typescript
useEffect(() => {

    // 정의와 동시에 ()로 즉시 실행한다
    (async () => {
        try {
            const res = await fetch('https://...');
            const data = await res.json();
        } catch (err) {
            setError(err);
        }
    })();
}, []);
```


## `useRef`

`useRef`는 리액트 컴포넌트 안에서 가질 수 있는 값 보관함이다.

여기에 포함된 값은 바뀌어도 리렌더링되지 않으며, 컴포넌트가 리렌더링되어도 보관함이 갖고 있는 값들도 유지된다.

`useRef` 훅은 주로 두 가지 목적으로 사용된다.

**리렌더링을 유발하지 않는 변수 관리**
- `useState`: 값이 바뀌면 컴포넌트가 다시 렌더링된다. 화면에 바로 보여야 하는 값에 사용한다.
- `useRef`: 값이 바뀌어도 컴포넌트가 렌더링되지 않는다. 화면에 보여줄 필요는 없지만 컴포넌트가 지워지기 전까지 기억해야 하는 값을 관리할 때 사용한다. (이전 상태 값, 타이머 ID, 스크롤 위치 등)

**실제 DOM 요소에 직접 접근하기 위해**
- 리액트는 기본적으로 브라우저의 DOM을 직접 조작하지 않고 가상 DOM을 통해 화면을 그린다.
- 특정 상황(포커스 주기, 스크롤 조작, 요소 크기 측정 등)에서는 실제 HTML 요소에 직접 접근해야 하는데, 이 때 `useRef`를 요소와 연결한다.

### 사용법

`useState`처럼 하나의 파라미터(초기값)만 인자로 받는다.

```typescript
// useRef의 반환값은 하나의 프로퍼티(current)만 가진 객체를 반환한다.
// 인자값으로 전달한 initialValue가 refContainer.current에 저장된다.
// 인자를 비워두면 초기값으로 `undefined`가 지정된다.
const refContainer = useRef(initialValue);
```

**컴포넌트가 마운트되자마자 입력창 포커스**

```typescript
import { useEffect, useRef } from 'react';

export function AutoFocusInput() {
    const inputRef = useRef<HTLMInputElement>(null);

    useEffect(() => {
        // 컴포넌트 마운트 시 inputRef.current가 존재하는지 확인 후 포커스를 지정한다
        if (inputRef.current) {
            inputRef.current.focus();
        }
    }, []);

    return (
        <div>
            <label htmlFor="username">아이디: </label>
            // ref 속성을 통해 HTML 요소와 연결한다
            <input ref={inputRef} id="username" type="text" />
        </div>
    )
}
```

**리렌더링 없이 값 유지하기**

`useRef` 내부의 값은 `.current` 프로퍼티를 통해 접근할 수 있다.

값이 바뀌어도 화면이 다시 그려지지 않기 때문에 성능 최적화에 유리하다.

```typescript
import { useState, useRef } from 'react';

export function Timer() {
    const [seconds, setSeconds] = useState<number>(0);

    // 브라우저의 타이머 ID를 기억할 Ref 생성
    // 화면에 렌더링할 필요가 없으므로 useRef를 사용한다
    const timerIdRef = useRef<number | null>(null);

    const startTimer = () => {
        // 이미 타이머가 돌고 있다면 중복 방지
        if (timerIdRef.current !== null) return;

        timerIdRef.current = window.setInterval(() => {
            setSeconds((prev) => prev + 1)
        }, 1000);
    };

    const stopTimer = () => {
        if (timerIdRef.current !== null) {
            clearInterval(timerIdRef.current);

            // 타이머가 멈추면 대상을 비워두기 (리렌더링 X)
            timerIdRef.current = null;
        }
    };

    return (
        <div>
            <h1>시간: {seconds}초</h1>
            <button onClick={startTimer}>시작</button>
            <button onClick={stopTimer}>중지</button>
        </div>
    );
}
```