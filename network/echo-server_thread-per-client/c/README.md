[서버 코드](./server.c)

[클라이언트 코드](./client.c)

```shell
clang ./server -o server
clang ./client -o client
./server
./client
./cilent
./client
```

## 스레드 per client

클라이언트 하나가 접속하면 스레드를 생성하여 스레드 안에서 recv/send 반복

이후 클라이언트와 연결이 끊어지면 해당 스레드를 종료한다

각 클라이언트가 완전 독립적으로 서버와 통신하며 블로킹 I/O를 그대로 사용할 수 있다

다만 접속자가 많아지면 스레드가 폭발적으로 늘어남에 따라 커널 스레드 스택을 많이 필요로 하며 스레드 컨텍스트 스위칭 비용이 증가한다

-> 10k 클라이언트 이상의 고성능 환경에서는 적합하지 않은 모델

## POSIX Threads (pthreads) 함수

**pthread_create()**

스레드를 생성하는 함수

실행을 시작할 함수를 지정할 수 있다

**pthread_detach()**

스레드가 스스로 종료될 때 리소스를 자동으로 회수하도록 설정하는 함수

클라이언트와 연결이 끊어진 스레드는 바로 detach (join할 필요 없음)

**pthread_exit()**

스레드 종료 시 호출하는 함수

**pthread_join()**

스레드의 종료를 기다리는 함수

join을 하면 메인 스레드가 블로킹되므로 자주 사용하지 않음


## 스레드 안전성

스레드는 프로세스의 메모리를 공유하므로 서버 전역 변수나 공유 자원이 있으면 경쟁 조건이 발생한다

임계 구역
- 연결된 클라이언트 수
- 로그 파일 접근
- 공용 버퍼 사용
- 공용 구조체 접근

스레드 간 동기화를 위해 `pthread_mutex_t`, `pthread_mutex_lock()`, `pthread_mutex_unlock()`을 사용해야 한다

단, 에코 서버는 스레드별로 클라이언트 FD(File Descriptor)를 독립적으로 사용하기 때문에 동기화를 자주 하진 않음

