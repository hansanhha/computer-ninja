#### 인덱스
- [안드로이드 기본](#안드로이드-기본)
- [화면/생명주기](#화면생명주기)
- [UI 시스템](#ui-시스템)
- [데이터 \& 비동기 처리](#데이터--비동기-처리)
- [안드로이드 아키텍처](#안드로이드-아키텍처)
- [시스템 연동, 추가 기능](#시스템-연동-추가-기능)
- [배포 \& 튜닝](#배포--튜닝)
- [최소 목표 앱](#최소-목표-앱)


## 안드로이드 기본

안드로이드 앱 구조 (상태 기반 시스템)
- AndroidManifest.xml
- Application / Activity / Service / BroadcastReceiver
- Context
- Resource System (res/layout, res/values)

개발환경
- 안드로이드 스튜디오 프로젝트 구조 파악 (그레이들)
- 에뮬레이터

App Lifecycle vs Process Lifecycle


## 화면/생명주기

- Activity lifecycle
  - onCreate, onStart, onResume
  - onPause, onStop, onDestroy
- Fragment
- 화면 회전 (configuration change)
- ViewModel (초기 도입)

화면 회전 시 데이터 유지하기

백그라운드 갔다가 복귀 시 상태 유지

Fragment 전환


## UI 시스템

- XML Layout
- ConstraintLayout
- RecyclerView, DiffUtil
- Adapter / ViewHolder 패턴
- Density(dp, sp)

Measure -> Layout -> Draw


## 데이터 & 비동기 처리

- Thread, Handler
- Coroutine
- Retrofit
- JSON Parsing
- Repository 패턴

로컬 데이터
- SharedPreferences
- Room (SQLite ORM)


## 안드로이드 아키텍처

MVVM, MVC/MVP

ViewModel

LiveData / StateFlow

Dependency Injection (Hilt)


## 시스템 연동, 추가 기능

Permission 시스템

Foreground / Background Service

Notification

Deep Link


## 배포 & 튜닝

Proguard / R8

App Bundle

플레이스토어 배포

Crashlytics

ANR / OOM 분석


## 최소 목표 앱

로그인 화면

목록 (RecyclerView)

상세 화면

서버 API 연동

로컬 캐시

화면 회전 대응

에러 처리
