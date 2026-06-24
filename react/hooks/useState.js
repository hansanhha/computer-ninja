"use strict";
// 리액트 Fiber 흉내
const hooks = [];
let currentHookIndex = 0;
// useState 구현
function useState(initialState) {
    console.log("useState 훅 호출");
    const hookIndex = currentHookIndex;
    if (!hooks[hookIndex]) {
        hooks[hookIndex] = {
            state: initialState,
            queue: [],
        };
    }
    const hook = hooks[hookIndex];
    hook.queue.forEach(update => {
        const action = update.action;
        hook.state =
            typeof action === "function"
                ? action(hook.state)
                : action;
    });
    hook.queue = [];
    const setState = (action) => {
        hook.queue.push({ action });
        render();
    };
    currentHookIndex++;
    return [hook.state, setState];
}
function render() {
    currentHookIndex = 0;
    App();
}
// setCount 훅을 호출할 때마다 render 함수로 인해 App 함수가 호출된다.
// App 함수의 count 상태는 hooks에서 관리되며 setCount로 상태를 업데이트할 때마다 업데이트된 값을 반환받는다.
// 컴포넌트 함수는 매번 실행되며 지역 변수도 매번 새로 만들어진다.
// 즉, 상태는 컴포넌트 함수 안에 있는 게 아니라 리액트 Fiber가 관리한다.
function App() {
    const [count, setCount] = useState(0);
    console.log("count:", count);
    // 주석 해제 후 실행 (아래 조건문 주석)
    // if (count < 5) {
    //     setTimeout(() => {
    //         setCount(prev => prev + 1);
    //     }, 1000);
    // }
    if (count === 0) {
        // setCount(prev => prev + 1);
        // setCount(prev => prev + 1);
        // setCount(prev => prev + 1);
        setCount(count + 1);
        setCount(count + 1);
        setCount(count + 1);
    }
}
// 초기 렌더링
render();
