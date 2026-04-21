#### 인덱스
- [전체 흐름](#전체-흐름)
- [초기화 단계](#초기화-단계)
- [구성 단계](#구성-단계)
- [실행 단계](#실행-단계)
  

## 전체 흐름

```text
[ 초기화 단계 ]
    ↓
buildSrc 빌드 (존재하면)
    ↓
settings.gradle.kts 실행
    ↓
프로젝트 구조 확정
    ↓
[ 구성 단계 ]
    ↓
플러그인 해석
    ↓
모든 프로젝트 build.gradle.kts 구성
    ↓
Task 그래프 생성
    ↓
[ 실행 단계 ]
    ↓
필요한 Task만 실행
```

## 초기화 단계

빌드에 대한 메타 정보를 정의하는 단계 - 몇 개의 프로젝트인지, 플러그인과 라이브러리 의존성을 어디서 가져올지 등

빌드 구조만 결정된 상태이며 build.gradle은 실행되지 않고 task가 없는 단계이다

```text
1. 그레이들 시작

2. buildSrc 빌드 (존재하면)
- 공통 코드, 커스텀 플러그인 컴파일
- 결과를 클래스패스에 추가함

3. settings.gradle.kts 실행
- pluginManagement 설정
- dependencyResolutionManagement 설정
- include(...)로 프로젝트 구조 정의
- includeBuild(...) 등록 (composite build)

4. 프로젝트 구조 확정
- 루트 프로젝트
- 서브 프로젝트 목록 생성
```

**buildSrc**
- 자동으로 포함되는 독립 빌드
- `settings.gradle.kts` 이전에 실행됨
- 공통 코드 중앙화 목적

**`includeBuild(...)`**
- 명시적으로 포함하는 독립 빌드
- `settings.gradle.kts` 평가 시점에 지정한 디렉토리를 하나의 독립된 빌드로 연결함
- 해당 빌드를 실행하는 게 아니라 "빌드 그래프"에 참여시킬 준비를 함 -> 필요한 경우 포함 빌드의 `settings.gradle.kts`를 읽어서 빌드 구조를 파악함
- 로컬 플러그인 제공 목적

buildSrc나 `includeBuild(...)`로 등록된 빌드는 메인 빌드와 독립된 별도의 그레이들 빌드이다

즉, 하나의 프로젝트 내에 메인 빌드 이외에 여러 빌드가 공존하는 구조이다

따라서 해당 디렉토리에는 자기만의 `settings.gradle.kts` 파일을 가지고, 이를 기반으로 각각 초기화, 구성, 실행 단계를 거쳐 실행된 결과물을 메인 빌드의 클래스패스에 추가한다

메인 빌드는 buildSrc에서 추가한 실행 결과물을 쓸 수 있지만 buildSrc의 task를 직접 실행할 수는 없다

반대로 포함 빌드(`includeBuild(...)`)의 task는 메인 빌드에서 사용할 수 있다

includeBuild는 Composite Build로 연결된 동등한 빌드로 메인 빌드와 포함 빌드를 더해 하나의 빌드 그래프를 만들기 때문이다


## 구성 단계

어떻게 빌드할지를 정의하는 단계

```text
1. build.gradle.kts의 plugins{} 실행
- 그레이들이 플러그인 블럭을 먼저 파싱해서 플러그인 적용 단계를 먼저 수행함

2. 모든 프로젝트의 build.gradle.kts 평가
- 루트 프로젝트
- 서브 프로젝트 (순서 보장 X, 그래프 기반으로 평가)

3. task 등록 (tasks.register 등)
- tasks.register: Lazy configuration
- tasks.create: Eager configuration (성능 저하 유발)

4. task 의존성 그래프  생성
- 어떤 task가 어떤 task에 의존하는지 계산
```

초기화 단계에서 `includeBuild(...)`로 메인 빌드에 연결한 포함 빌드의 플러그인이나 라이브러리를 `build.gradle.kts`에서 사용한다면

해당 포함 빌드의 `build.gradle.kts`를 평가한 후 메인 빌드에 반영함

만약 포함 빌드가 실제로 사용되지 않으면 구성이 되지 않는다

## 실행 단계

```text
1. 요청된 task 확인 (build, test 등)

2. 해당 task의 의존성만 선택

3. 선택된 task 실행 (task 그래프 실행)
- ./gradlew build
- compile -> test -> jar -> build
```

