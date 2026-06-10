const delay = () => new Promise(resolve => setTimeout(resolve, 1000)).then(() => console.log("delay 프로미스 then 호출"));

async function myAsyncFunc() {
    console.log("3. async myAsyncFunc 함수 내부 시작");
    await delay();
    console.log("5. 1초 뒤 await 완료 후 실행");
}

console.log("1. 메인 코드 시작");
console.log("2. async myAsyncFunc 함수 호출");
myAsyncFunc();
console.log("4. 메인 코드 끝 (async 함수 호출 후 바로 실행)");
