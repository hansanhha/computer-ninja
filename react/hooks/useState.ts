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