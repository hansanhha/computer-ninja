#### 인덱스
- [Activity](#activity)
- [생명주기](#생명주기)


## Activity

안드로이드 Activity는 사용자 인터페이스(UI)를 제공하는 핵심 요소이다

Activity는 앱의 한 화면을 대표하여 사용자가 앱과 상호작용하는 인터페이스 역할을 한다

로그인 화면, 메인 메뉴, 설정 페이지 등 각 화면이 하나의 Activity로 구현될 수 있다

(최근에는 단일 Activity 아키텍처를 사용하며, 단일 Activity는 Fragment 또는 Jetpack Compose 대상으로 구현된 화면 컨테이너 역할을 함)

Activity는 안드로이드 시스템에 의해 관리되며 앱의 생명주기를 통해 생성, 시작, 일시 중지, 중지, 파괴 등의 상태 변화를 거친다

-> 시스템의 리소스(메모리, 배터리 등)를 효율적으로 사용하기 위한 메커니즘

주요 역할
- UI 레이아웃 렌더링
- 사용자 입력(터치, 키 입력 등) 처리
- 다른 Activity나 서비스와 상호작용
- 시스템 이벤트(화면 회전, 배터리 저하 등)에 대응

Activity는 앱의 진입점으로 작용하며 여러 Activity가 스택 형태로 관리되어 이전 화면으로 돌아갈 수 있다


## 생명주기

Activity 생명주기는 Activity가 생성되어 화면에 보일 때부터 파괴될 때까지의 상태 변화 과정을 의미한다

시스템 자원을 효율적으로 관리하고 앱의 안정성을 보장하기 위해 이러한 메커니즘이 설계되었다

상태 변화는 시스템 이벤트(다른 앱 실행, 화면 회전, 메모리 부족 등)에 의해 발생하며, 각 변화 시점에 콜백 메서드가 호출된다

개발자는 이러한 콜백 메서드를 오버라이드하여 리소스 초기화, 데이터 저장/정리 등의 작업을 수행할 수 있다

생명주기 상태
- Created: Activity가 생성된 상태
- Started: Activity가 사용자에게 보이기 시작한 상태
- Resumed (Running): Activity가 포그라운드에 있으며, 사용자와 상호작용 가능한 상태
- Paused: Activity가 부분적으로 가려진 상태 (다이얼로그나 다른 Activity가 위에 뜬 상태)
- Stopped: Activity가 완전히 보이지 않는 상태 (백그라운드로 이동)
- Destroyed: Activity가 파괴된 상태

생명주기 상태에 따라 아래와 같이 Activity의 콜백 메서드가 호출된다

![lifecycle](./images/lifecycle.png)

[출처](https://developer.android.com/guide/components/activities/activity-lifecycle?hl=ko)

### `onCreate()`

Activity가 처음 생성될 때 호출되는 메서드이다

생명주기의 시작점으로 단 한번만 호출되며 시스템이 Activity를 초기화한다

주요 작업
- UI 레이아웃 설정 `setContentView()`
- 변수 초기화
- View 바인딩
- 이전 상태 복원 `savedInstanceState`

### `onStart()`

Activity가 사용자에게 보이기 시작할 때 호출된다 (`onCreate()` 후)

이 시점에는 Activity가 화면에 나타나지만 아직 사용자와 상호작용을 할 수 없다

주요 작업
- UI 요소 렌더링
- UI 애니메이션 시작
- 리소스 로딩 (무거준 작업 X)

### `onResume()`

Activity가 포그라운드로 돌아와 사용자와 상호작용 가능할 때 호출된다 (`onStart()` 또는 `onPause()` 후 실행 재개 시)

앱이 "실행 중"인 상태

주요 작업
- 카메라, 센서 활성화
- 타이머, 애니메이션 재개
- 사용자 입력 리스너 등록

### `onPause()`

Activity가 포그라운드를 잃을 때 호출된다 (다른 Activity가 시작되거나 다이얼로그가 뜰 때)

이 시점까지 Activity는 여전히 보일 수 있지만 상호작용이 제한된다

주요 작업
- 현재 상태 저장 (DB 또는 SharedPreferences)
- 리소스 일시 정지 (타이머 멈춤, 센서 해제 등)
- 빠르게 끝날 수 있는 작업만 실행

### `onStop()`

Activity가 완전히 보이지 않을 때 호출된다 (백그라운드로 이동하거나 다른 앱을 실행해서 덮힐 때)

이 상태에서 시스템의 메모리가 부족하면 Activity를 강제로 종료할 수 있다

주요 작업
- 리소스 해제 (네트워크 연결 종료, 파일 저장)
- 배터리 소모를 줄이는 작업

### `onDestroy()`

Activity가 파괴될 때 호출된다

앱 종료, 메모리 부족 또는 `finish()` 호출로 인해 발생한다

주요 작업
- 모든 리소스 정리 (메모리 누수 방지)
- 리스너 해제
- 최종 데이터 저장

### `onRestart()`

Activity가 중지(`onStop`)된 후 다시 시작될 때 호출된다

백그라운드에서 포그라운드로 복귀 시 `onStart()` 전에 호출된다

주요 작업
- `onStop()`에서 중지된 리소스 재초기화
- 상태 복원


## 생명주기 흐름

앱 시작: `onCreate()` -> `onStart()` -> `onResume()`

다른 Activity 호출 (현재 Activity 일시중지): `onPause()` -> (새 Activity 시작) -> `onStop()`

이전 Activity로 복귀: `onRestart()` -> `onStart()` -> `onResume()`

앱 종료: `onPause()` -> `onStop()` -> `onDestroy()`


## 상태 변화 대응

화면 회전(Configuration Change) 시 `onDestroy()`나 `onCreate()`가 다시 호출된다

상태를 유지하려면 `ViewModel`이나 `onSaveInstanceState()`를 사용해야 한다