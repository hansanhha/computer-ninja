## 시그널과 Kill
- [시그널](#시그널)
- [시그널 처리 단계](#시그널-처리-단계)
    - [1. 시그널 발생(Generation)](#1-시그널-발생generation)
    - [2. 시그널 대기(Pending)](#2-시그널-대기pending)
    - [3. 시그널 전달(Delivery)](#3-시그널-전달delivery)
    - [4. 시그널 처리(Handling)](#4-시그널-처리handling)
- [시그널 처리 방식](#시그널-처리-방식)
- [시그널 종류](#시그널-종류)
  - [SIGNINT](#signint)
  - [SIGTERM](#sigterm)
  - [SIGKILL](#sigkill)
  - [SIGSTOP](#sigstop)
  - [SIGCONT](#sigcont)

## 시그널

시그널(Signal)은 운영체제가 프로세스에게 보내는 비동기 메시지로 커널 또는 다른 프로세스가 특정 사건이 발생했음을 프로세스에게 알리는 인터럽트이다.

어떤 프로세스가 무한 루프를 돌고 있다.

```c
while (1) {
    printf("hello");
}
```

사용자는 터미널에서 `Ctrl + C`를 누르면 프로세스는 멈추게 되는데, 이게 시그널을 사용한 대표적인 예시이다.

프로세스는 직접 키보드 입력을 계속 확인하지 않고 커널을 통해 시그널을 전달받는다.

시그널을 전달받으면 하던 동작을 멈추고 시그널의 종류에 따라 약속된 동작을 수행한다.

```text
Ctrl+C 입력
    ↓
터미널 드라이버
    ↓
커널
    ↓
SIGINT 전달
    ↓
프로세스 종료
```

## 시그널 처리 단계

#### 1. 시그널 발생(Generation)

`Ctrl + C`와 같은 이벤트 발생이나 시스템 콜(`kill`, `raise`)을 통해 커널에게 시그널 전송을 요청한다.

#### 2. 시그널 대기(Pending)

커널은 대상 프로세스의 PCB에 있는 Pending Signal Bitmap(대기 시그널 마스크)에 해당 시그널 비트를 1로 설정하여 시그널 수신 정보를 기록한다.

#### 3. 시그널 전달(Delivery)

프로세스가 커널 모드에서 사용자 모드로 전환되는 시점(시스템 콜 종료, 컨텍스트 스위칭 직후)에 커널이 프로세스에게 대기 중인 시그널을 전달한다.

#### 4. 시그널 처리(Handling)

프로세스는 정의된 방식에 따라 시그널을 처리한다.

## 시그널 처리 방식

프로세스는 전달받은 시그널에 대해 3가지 방식으로 대응할 수 있다.

**기본 동작(Default Action)**: 커널이 정의한 기본 동작 수행

**시그널 무시(Ignore)**: 아무런 행동을 취하지 않음 (`SIGKILL`, `SIGSTOP`은 무시 불가)

**사용자 정의 핸들러(Catch)**: 프로세스 내에 특정 시그널 처리를 위한 시그널 핸들러를 등록하여, 시그널 수신 시 등록된 함수가 실행되도록 함

## 시그널 종류

### SIGNINT

Signal Interrupt

키보드 입력: `Ctrl + C`

프로세스의 실행을 중단한다.

### SIGTERM

Signal Terminate

`kill <PID>`

프로세스에게 종료 요청을 보낸다.

`SIGTERM` 시그널을 전달받은 프로세스는 DB 연결 정리, 로그 기록, 파일 flush 등의 작업을 수행한 후 **정상 종료(Graceful Shutdown)**를 한다.

### SIGKILL

Signal Kill

`Kill -9 <PID>`

이 시그널은 무시할 수 없으며 핸들러를 등록할 수 없고 프로세스를 즉시 종료한다.

프로세스는 종료 처리를 하지 못하고 커널에 의해 곧바로 제거되므로 위험할 수 있다.

### SIGSTOP

Signal Stop

`kill -STOP <PID>`

프로세스 상태가 `RUNNING`에서 `STOPPED`로 변경되어 CPU를 전혀 사용하지 않게 된다.

`SIGKILL`과 마찬가지로 무시할 수 없으며 핸들러를 등록할 수 없다.

### SIGCONT

Signal Continue

`kill -CONT <PID>`

중지된 프로세스의 시랳ㅇ을 재개한다.