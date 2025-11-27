#### 인덱스
- [.github 디렉토리](#github-디렉토리)
- [깃헙 액션의 주요 컴포넌트](#깃헙-액션의-주요-컴포넌트)
  - [워크플로우](#워크플로우)
  - [이벤트](#이벤트)
  - [Job](#job)
- [Variables](#variables)
- [Secrets](#secrets)
- [`GITHUB_TOKEN`](#github_token)
- [주요 액션](#주요-액션)
- [워크플로우 작성](#워크플로우-작성)
- [컨테이너 레지스트리](#컨테이너-레지스트리)
- [패키지 발행 (도커 이미지, JAR 등)](#패키지-발행-도커-이미지-jar-등)
- [유용한 VS Code 익스텐션](#유용한-vs-code-익스텐션)
- [가격 정책](#가격-정책)
- [참고 문서 및 이미지 출처](#참고-문서-및-이미지-출처)


## .github 디렉토리

`.github` 디렉토리는 깃허브에서 제공하는 기능을 이용하기 위한 메타 설정을 모아둔 디렉토리로 리포지토리의 최상단에 위치해야 한다

**규약 기반 디렉토리(configuration-by-covention)**로 깃허브가 특정 파일 구조를 인식해 기능을 자동 활성화할 수 있도록 한다

주요 목록
- 깃헙 액션 워크플로우
- 이슈/PR 템플릿
- 자동화 설정
- 리뷰 권한 설정
- Dependabot 및 보안 설정

일반적으로 아래와 같은 구조를 가진다

```text
.github/
├── workflows/
│   ├── ci.yml
│   ├── deploy.yml
│   └── lint.yml
├── actions/
│   ├── update-homebrew-tap/
│   │   └── action.yml
│   └── send-notification/
│       └── action.yml
├── ISSUE_TEMPLATE/
│   ├── issue.md
│   └── config.yml
├── PULL_REQUEST_TEMPLATE.md
├── CODEOWNERS
├── dependabot.yml
└── FUNDING.yml
```

`.github/workflows/`: 깃헙 액션은 이 디렉토리에 있는 `.yml` 파일만 워크플로우 파일로 인식한다

`.github/actions/`: 워크플로우에서 사용할 액션을 보관하는 디렉토리로 각 액션의 메인 동작을 `action.yml` 파일에 정의한다

`.github/ISSUE_TEMPLATES/`: 이슈 기본 양식 디렉토리

`.github/ISSUE_TEMPLATES/config.yml`: 템플릿 동작 설정 파일

`.github/PULL_REQUEST_TEMPLATE.md`: PR 작성 템플릿 파일

`.github/CODEOWNERS`: 특정 파일/디렉토리 리뷰어 강제 (PR 생성 시 자동 리뷰어 배정)

`.github/dependabot.yml`: 의존성 자동 업데이트 봇 설정 파일

`.github/FUNDING.yml`: 오픈소스 후원 링크

Organazation 전체에 특정 설정을 적용하고 싶으면 리포지토리 이름 자체를 `.github`으로 짓고 그 안에 설정 파일을 두면 된다

단, 워크플로우는 기본적으로 전파되지 않으며 `workflow_call` 방식으로 불러올 수 있다

```text
.github (repository)
└── .github/
    ├── workflows/
    ├── ISSUE_TEMPLATE/
    └── PULL_REQUEST_TEMPLATE.md
```


## 깃헙 액션의 주요 컴포넌트

깃헙 액션: 빌드, 테스트, 배포 파이프라인을 자동화할 수 있는 **CI (Continuous Integration)** & **CD (Continuous Delivery)** 플랫폼

리포지토리에 어떤 **이벤트**가 **일어났을 때** 그에 해당하는 특정 **워크플로우**를 실행한다

e.g., 풀 리퀘스트마다 빌드, 테스트를 수행 워크플로우 또는 머지된 풀 리퀘스트를 프로덕션에 배포하는 워크플로우

워크플로우는 기본적으로 깃허브에서 제공하는 리눅스/윈도우/맥 가상 머신에서 실행되는데, 사용자가 온프레미스, 클라우드에 직접 셀프-호스트 러너를 구성함으로써 실행될 수도 있다

요약
- 워크플로우: CI/CD 로직이 담긴 하나의 파일
- 이벤트: 리포지토리에 발생한 일(풀 리퀘스트 오픈, 이슈 생성 등)
- 러너: 깃헙-러너(리눅스/윈도우/맥 가상머신), 셀프-호스트 러너

### 워크플로우

<img src="./images/overview-actions-simple.webp" alt="github actions simple overview" style="width: 70%">

워크플로우는 리포지토리 최상단의 `.github/workflows` 디렉토리에 정의된 **yaml** 파일이다 

1개 이상의 Job을 포함하며 이벤트, 스케줄, REST API, 수동 트리거를 통해 실행될 수 있다

리포지토리에 여러 워크플로우를 만들어 다양한 작업을 수행할 수 있다 - 또한 워크플로우는 다른 워크플로우를 사용할 수도 있다

순차적/병렬적으로 정의된 Job을 실행하며 각각 가상머신 러너 또는 컨테이너 안에서 실행된다 -> 기본적으로 Job 간에는 실행 환경이 격리된다

Job은 개발자가 직접 작성한 **쉘 스크립트**나 **액션 (재사용할 수 있는 익스텐션인)**을 실행하는 하나 이상의 Step을 포함하며, 각 Step은 순차적으로 실행된다

**워크플로우 작성 순서**
- 워크플로우 템플릿 선택 (리포지토리의 Actions 탭)
- 워크플로우 실행 조건 선택
- 워크플로우 실행 환경 선택 (깃헙/셀프 호스트 러너 또는 컨테이너)
- 워크플로우 동작 정의


### 이벤트

**리포지토리에서 발생한 특정 활동**을 이벤트라고 하며 워크플로우 실행을 촉발하는 트리거의 한 종류이다 -> 이벤트는 워크플로우 단위로 설정됨(Job 단위 아님)

push 발생, PR 생성, 이슈 생성 등이 이벤트에 속한다

PR 생성, PR 머지 등 하나의 이벤트에서 여러 가지의 이벤트가 발생할 수 있는 이벤트는 **"Activity Type"**이라고 하는 세부 카테고리로 나뉘어 세부적으로 이벤트 만족 조건을 지정할 수 있다

깃허브는 워크플로우가 실행될 때 **"Webhook Event Payload"**라고 하는 이벤트에 대한 JSON 데이터를 제공하는데, 이를 통해 발생한 이벤트의 기록을 볼 수 있다

또한 이벤트 유형에 따라 깃허브 환경 변수(`GITHUB_SHA`, `GITHUB_REF`)에 담기는 정보가 다르므로 공식 문서를 참고하자

요약
- 이벤트: 리포지토리에서 발생한 일
- Activity Type: 이벤트 세부 동작
- Webhook Event Payload: 이벤트 상세 데이터
- 환경 변수: 깃헙 액션 실행 시 자동으로 채워지는 환경 변수 (이벤트마다 포함되는 정보가 상이함)

[깃헙 액션의 모든 이벤트](https://docs.github.com/en/actions/reference/workflows-and-actions/events-that-trigger-workflows)

#### push 이벤트

**발생 조건**: 특정 브랜치 또는 태그에 커밋 push 시

**유즈케이스**: 브랜치별 자동 테스트/빌드, 태그 기반 배포

**Activity Type**: 없음

**주요 세부 옵션**
- branches: 타겟 브랜치 지정
- tags: 타겟 태그 지정
- paths, paths-ignore: 타겟 경로 또는 무시할 경로 지정

```yaml
on:
  push:
    branches: [main, develop] # main, develop 브랜치에 push 시 이벤트 발생
    tags: ['v*']              # v로 시작하는 태그에 push 시 이벤트 발생
    paths: ['src/**']         # src/** 경로에 psuh 시 이벤트 발생
```

#### pull_request 이벤트

**발생 조건**: 풀 리퀘스트 생성/업데이트/재오픈/머지 전 등의 상태 변화 시

**유즈케이스**: PR 오픈/수정 시 CI 파이프라인 자동 실행, PR 변경사항 확인 후 라벨 자동 부착 또는 리뷰어 자동 할당

**Activity Type**: opened, reopened, synchronize(PR 소스 브랜치에 추가 push 발생), closed

**주요 세부 옵션**
- types: Activity Type 지정
- branches: 타겟 브랜치 지정
- paths, paths-ignore: 타겟 경로 또는 무시할 경로 지정

```yaml
on:
  pull_request:
    types: [opened, synchronize, reopened]
    branches: [main, develop]
    paths: ['src/**']
```


#### issues 이벤트

**발생 조건**: 이슈 생성/수정/닫힘 발생 시

**유즈케이스**: 이슈 템플릿 자동 검증, 자동 라벨링 또는 리뷰어 자동 할당, 이슈 오픈 시 슬랙/디스코드 알림

**Activity Type**: opened, closed, edited 등

**주요 세부 옵션**
- types: Activity Type 지정

```yaml
on:
  issues:
    types: [opened, edited, closed, labeled]
```

#### workflow_dispatch 이벤트

**발생 조건**: GitHub API, GitHub CLI, GitHub UI을 통한 수동 발생, 해당 워크플로우 파일이 무조건 기본 브랜치에 있어야 된다

**유즈케이스**: 운영/스테이징 배포, 긴급 패치 배포, 임시 관리 작업(데이터 마이그레이션 수동 실행, 캐시 초기화 스크립트 실행 등)

**Activity Type**: 없음

**주요 세부 옵션**
- inputs: 사용자 입력값(배포 버전, 환경명, 강제 재빌드 여부 등)

```yaml
on:
  workflow_dispatch:
    inputs:
      version:
        description: 'Release version'
        required: true
      environment:
        description: 'deploy target'
        required: true
        default: 'production'
```

#### workflow_run 이벤트

**발생 조건**: 특정 워크플로우 실행 완료 후

**유즈케이스**: CI -> CD 파이프라인 체인 형태 구성, 이전 워크플로우의 아티팩트를 받아서 후속 처리

**Activity Type**
- requested
- completed

**주요 세부 옵션**
- types: Activity Type 지정
- workflows: 어떤 워크플로우가 끝났을 떄 동작할지 지정

```yaml
on:
  workflow_run:
    workflows: ["CI"]
    types: [completed]
```

#### schedule 이벤트

**발생 조건**: 크론 표현식 기반 정시/정기 실행

**유즈케이스**: Dependabot 업데이트 검사, CodeQL 주기적 스캔, 보고서 생성 등

**Activity Type**: 없음

**주요 세부 옵션**
- cron: UTC 기준이므로 한국 기준 시차 고려 필요 (+9h)

```yaml
on:
  schedule:
    - cron: "0 0 * * *"     # 매일 00:00 UTC
    - cron: "0 */6 * * *"   # 6시간마다 실행
```

### Job

Job은 가상 머신(러너)에서 실행되는 **Step 목록들을 포함**하는 워크플로우의 유닛이다

워크플로우에 포함된 각 Job은 서로 다른 가상 머신(러너)에서 실행되기 때문에 환경이 격리되며 파일 시스템을 공유하지 않는다 (필요하면 `actions/upload-artifact & download-artifact`으로 전달해야 됨)

**Step**은 개발자가 직접 작성한 **쉘 스크립트**이거나 **액션**을 실행할 수 있다

같은 Job 내부의 각 Step은 동일한 러너에서 순차적으로 실행되므로 서로에게 의존하며 데이터를 공유할 수 있다

기본적으로 Job은 어떤 의존성도 갖지 않아서 병렬적으로 실행되지만, 다른 Job을 의존하는 경우 의존 Job의 실행이 끝난 후 해당 Job이 실행된다

또한 Job이 다양한 운영체제 또는 서로 다른 언어 레벨에서 실행되어야 한다면 **matrix** 기능을 통해 동시에 여러 번 실행할 수 있다

**액션**은 워크플로우 내에서 특정 작업을 수행하는 **미리 정의된 재사용 가능한 Job 목록** 또는 **코드**로 직접 작성하거나 깃허브 마켓 플레이스에서 가져와 사용할 수 있다


## Variables

Variables는 워크플로우 실행 시 필요한 값을 평문으로 저장하고 사용하는 기능이다

애플리케이션 버전, 공통 경로, 배포 환경 이름 등 보안 민감 정보가 아닌 일반 구성 값을 저장할 때 주로 사용한다

Variables 저장 위치 및 스코프
- 리포지토리 Variables: 특정 리포지토리에서만 사용 가능한 Variables
- Environment Variables: 릴리즈/환경 별로 Variables 값 분리
- Organization Variables: 동일 조직 내 여러 리포지토리에서 공유하는 Variables

```yaml
name: Greeting on variable day

on:
  workflow_dispatch

env: # 워크플로우 환경변수
  DAY_OF_WEEK: Monday

jobs:
  greeting_job:
    runs-on: ubuntu-latest
    env: # Job 환경변수
      Greeting: Hello
    steps:
      - name: "Say Hello Mona it's Monday"
        run: echo "$Greeting $First_Name. Today is $DAY_OF_WEEK!"
        env: # Step 환경변수
          First_Name: Mona

```


## Secrets

Secrets는 워크플로우에서 필요한 민감한 값(비밀번호, 토큰, API 키 등)을 안전하게 저장하고 사용하는 기능이다

Secrets 저장 위치 및 스코프
- 리포지토리 Secrets: 특정 리포지토리에서만 사용 가능한 Secrets
- Environment Secrets: 릴리즈/환경 별로 Secrets 값 분리
- Organization Secrets: 동일 조직 내 여러 리포지토리에서 공유하는 Secrets

사용자가 UI 또는 REST API를 통해 Secrets를 등록하는 시점에 [Libsodium sealed boxes](https://libsodium.gitbook.io/doc/public-key_cryptography/ sealed_boxes) 이라고 하는 클라이언트 사이드 암호화를 진행한 뒤 깃허브 서버에 저장한다

성공적으로 등록을 마치면 깃허브가 이를 복호화하고 워크플로우 런타임에 주입하며, 워크플로우 로그에는 마스킹 처리하여 표시된다

Job 종료 시 VM이 폐기되므로 해당 Secrets 값은 자연스럽게 사라진다 (깃헙 호스트 러너 기준)

```yaml
jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - name: Login to Docker Hub
        run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin
```


## `GITHUB_TOKEN`

`GITHUB_TOKEN`은 깃헙 액션이 워크플로우 실행 시 자동으로 생성/폐기해주는 임시 인증 토큰이다

계정에서 보유하는 Personal Access Token(PAT)와 달리 워크플로우 내에서 사용한다

이 토큰은 리포지토리 스코프 권한이 부여되며, 단 한 번의 실행 범위에서만 유효하다

참고로 명시적으로 토큰 전달을 하지 않더라도 액션에서 `github.token` 컨텍스트로 `GITHUB_TOKEN`에 접근할 수 있기 때문에 액션을 실행하기 위한 최소한의 권한만 설정하는 것이 안전하다

```yaml
# 입력값으로 GITHUB_TOKEN 전달
env:
  GH_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```

```yaml
# REST API 호출 시 GITHUB_TOKEN 사용
steps:
  - name: Create issue using REST API
    run: |
      curl --request POST \
      --url https://api.github.com/repos/${{ github.repository }}/issues \
      --header 'authorization: Bearer ${{ secrets.GITHUB_TOKEN }}' \
      --header 'content-type: application/json' \
      --data '{
        "title": "Automated issue for commit: ${{ github.sha }}",
        "body": "This issue was automatically created by the GitHub Action workflow **${{ github.workflow }}**. \n\n The commit hash was: _${{ github.sha }}_."
        }' \
      --fail
```


## 주요 액션

### 리포지토리 체크아웃

리포지토리 체크아웃을 먼저 해야 Job(러너)에서 리포지토리에 접근할 수 있다

```yaml
- uses: actions/checkout@v4
```

### 프로그래밍 언어 설정

```yaml
# 자바 설정
- uses: actions/setup-java@v4
  with:
    distribution: 'temurin'
    java-version: '25'

# actions/setup-node, actions/setup-python, actions/setup-go
```

### 빌드 캐시 저장

```yaml
- uses: actions/cache@v4
  with:
    path: ./gradle/caches
    key: ${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*') }}
```

### 아티팩트 업/다운로드

```yaml
# 업로드
- uses: actions/upload-artifact@v4
  with:
    name: build
    path: build/libs/*.jar

# 다운로드
- uses: actions/download-artifact@v4
  with:
    name: build
    path: build/libs/*.jar
```

### 도커 관련 액션

```yaml
# GHCR 로그인
- uses: docker/login-action@v3
  with:
    registry: ghcr.io
    username: ${{ github.actor }}
    password: ${{ secrets.GITHUB_TOKEN }}
```

```yaml
# 도커 이미지 빌드 및 푸시
- uses: docker/build-push-action@v6
  with:
    push: true
    tags: ghcr.io/org/app:latest
```

### 클라우드 관련

```yaml
# AWS CLI 인증
- uses: aws-actions/configure-aws-credentials@v4
  with:
    role-to-assume: arn:aws:iam::123:role/deploy
    aws-region: ap-northeast-2

# ECR 로그인
aws-actions/amazon-ecr-login

# S3 업로드
aws-actions/amazon-s3-sync
```

### 린트/포매팅 관련

super-linter/super-linter

github/super-linter

reviewdog/action-*


## 워크플로우 작성

**워크플로우 작성 순서**
- 워크플로우 템플릿 선택 (생략)
- 워크플로우 실행 조건 선택
- 워크플로우 실행 환경 선택
- 워크플로우 동작 정의

### 1. 워크플로우 실행 조건 선택

워크플로우 실행 조건 선택은 템플릿 선택 이후 워크플로우의 실행을 유도하는 트리거링 방법, Job 실행 조건 명시, 동시 제어 등을 정의하는 단계이다

워크플로우 실행 트리거를 이벤트라고 하며, 다음과 같은 요소들이 이벤트가 된다
- 워크플로우가 위치한 리포지토리에서 발생한 이벤트
- 깃허브 바깥에서 발생한 `repository_dispatch` 이벤트 (REST API 등)
- 스케줄
- 수동 실행

`on` 키를 사용하여 어떤 이벤트가 해당 워크플로우를 트리거할지 선택할 수 있다 [이벤트 목록](https://docs.github.com/en/actions/reference/workflows-and-actions/events-that-trigger-workflows)

```yaml
# 단일 이벤트 지정
on: push

# 다중 이벤트 지정
# 둘 중 하나만 만족해도 워크플로우가 실행된다
# 동시에 모두 만족하는 경우 여러 개의 워크플로우가 실행된다
on: [push, fork]
```

다중 이벤트와 [Activity Type](#이벤트)을 지정하여 트리거링을 더 상세히 제어할 수 있다 

이 때 세부 옵션을 지정하지 않더라도 모든 이벤트는 배열 대신 콜론(`:`)을 사용하여 나열한다

[이벤트 필터링](https://docs.github.com/en/actions/how-tos/write-workflows/choose-when-workflows-run/trigger-a-workflow#using-filters)

```yaml
# 다음의 이벤트 조건이 만족된 경우에만 워크플로우 실행을 트리거한다
# 1. label이 생성, 삭제된 경우
# 2. main 브랜치 또는 release 하위 브랜치에 push한 경우
# 3. 깃허브 페이지 브랜치에 push한 경우
on:
  label:          # 이벤트
    types:        # Activity Type
      - created
      - deleted
  push:           # 이벤트
    branches:     # 필터링 (main 브랜치만 포함)
      - main
      - 'releases/**'
  page_build:     # 이벤트
```

#### Job 실행 조건 명시

Job 레벨에서 `if`, `environment` 키를 사용하여 더 상세한 실행 조건을 명시할 수 있다

```yaml
# 라벨 이름이 bug일 때
jobs:
  run_if_label_matches:
    if: github.event.label.name == 'bug'  # 조건식 사용
```

```yaml
# 풀 리퀘스트 이벤트인 경우
jobs:
  state_event_type:
    - name: if_pr
      if: github.event.pull_request  # 깃허브 이벤트 컨텍스트 접근
```

```yaml
# 'production' 환경일 때
jobs:
  publish:
    environment: production   # environment 사용
```

#### 토큰으로 인한 트리거링 관련 주의사항

`GITHUB_TOKEN`으로 트리거된 이벤트는 재귀 실행을 방지하기 위해 새로운 워크플로우 실행을 만들지 않는다 (단, `workflow_dispatch`와 `repository_dispatch`는 제외)

만약 워크플로우 실행 내에서 새로운 워크플로우 실행을 트리거하고 싶다면 Github App 액세스 토큰이나 개인 액세스 토큰을 사용해야 한다 -> 다만 의도치 않거나 재귀적인 워크플로우 실행을 일으켜 깃헙 액션 비용을 과도하게 발생시킬 수 있음을 유의해야 한다

### 2. 워크플로우 실행 환경 선택

각 Job의 실행 환경은 `jobs.<job_id>.runs-on` 키를 통해 지정할 수 있다

깃헙-호스트 러너를 사용하면 각 Job은 `runs-on`에 명시된 가상 머신 이미지의 인스턴스에서 실행된다

```yaml
jobs:
  test:
    runs-on: ubuntu-latest  # 리눅스 VM에서 실행
```

[public 리포지토리 깃헙 호스트 러너 목록](https://docs.github.com/en/actions/how-tos/write-workflows/choose-where-workflows-run/choose-the-runner-for-a-job#standard-github-hosted-runners-for-public-repositories)

[private 리포지토리 깃헙 호스트 러너 목록](https://docs.github.com/en/actions/how-tos/write-workflows/choose-where-workflows-run/choose-the-runner-for-a-job#standard-github-hosted-runners-for--private-repositories)

셀프 호스트 러너를 사용하는 경우 아래와 같이 배열의 맨 처음에 `self-hosted` 라벨을 명시하고 뒤이어 추가적인 라벨을 덧붙인다

```yaml
jobs:
  test:
    runs-on: [self-hosted, linux]
```

### 워크플로우 동작 정의

#### Job ID 지정 (필수)

워크플로우의 각 Job은 고유한 ID를 가져야 한다 (문자 또는 `_`로만 시작 가능, `-`, `_`, 문자/숫자 포함 가능)

```yaml
jobs:
  test:   # job_id: test
  build:  # job_id: build 
```

#### Job 이름 지정

Job ID 대신 이름이 Github UI에 표시된다

```yaml
jobs:
  test:
    name: test source code  
```

#### Job 의존성 지정

 Job 간 실행 순서를 지정하고 싶을 때 사용하는 옵션으로 먼저 성공적으로 처리되어야 하는 Job ID 목록을 지정한다

의존하고 있는 Job이 실패하거나 스킵되면 기본적으로 해당 Job의 실행도 스킵된다 (`always()`를 사용하면 항상 실행 보장 가능)

아래의 경우 `job1`이 성공적으로 마치면 `job2`가 실행되며, `job3`는 `job1`과 `job2`의 성공 완료를 기다린 뒤 실행된다

```yaml
# 실행 순서: job1 -> job2 -> job3 
jobs:
  job1:
  job2:
    needs: job1
  job3:
    # if: ${{ always() }} # job1과 job2 성공 완료와 상관없이 항상 실행
    needs: [job1, job2]
```

#### matrix 전략 

단일 Job을 각각 변수에 따라 여러 개의 Job으로 실행하는 전략이다

소스 코드를 여러 운영체제 또는 언어 버전별로 테스트하고 싶은 경우 주로 사용한다

```yaml
jobs:
  example_matrix:                           # Job ID
    strategy:                               # Job 실행 전략
      matrix:                               # matrix 사용
        version: [10, 12, 14]               # 버전 지정
        os: [ubuntu-latest, windows-latest] # 운영체제 지정
```

각 Job 실행은 matrix에 지정된 변수의 조합만큼 만들어진다 (총 6개의 Job 실행)
- `{version: 10, os: ubuntu-latest}`
- `{version: 10, os: windows-latest}`
- `{version: 12, os: ubuntu-latest}`
- `{version: 12, os: windows-latest}`
- `{version: 14, os: ubuntu-latest}`
- `{version: 14, os: windows-latest}`

[matrix 상세 옵션](https://docs.github.com/en/actions/how-tos/write-workflows/choose-what-workflows-do/run-job-variations)

#### 액션 사용

쉘 스크립트 대신 미리 코드가 작성된 액션을 사용하여 Job의 동작을 정의할 수 있다

액션은 동일 리포지토리, 다른 리포지토리에서 가져오거나 도커 허브의 컨테이너를 참조할 수 있다
- 동일 리포지토리 액션 참조: `{owner}/{repo}@{ref}` 또는 `./path/to/dir` (`./`는 기본 워킹 디렉토리에 대한 상대 참조 - `github.workspace`, `$GITHUB_WORKSPACE`)
- 다른 리포지토리 액션 참조: `{owner}/{repo}@{ref}`
- 도커 허브 컨테이너 참조: `docker://{image}:{tag}`

또한 태그, SHA, 브랜치를 통해 상세한 버전을 선택할 수 있다
- 태그 지정: `actions/javascript-action@v1.0.1`
- SHA 지정: `actions/javascript-action@a824008085750b8e136effc585c3cd6082bd575f`
- 브랜치 지정: `actions/javascript-action@main`

```yaml
jobs:
  example-job:
    steps:
        uses: actions/setup-node@v4 # 다른 리포지토리 액션 참조
  
  example-job-2:
    steps:
        uses: ./.github/actions/hello-world-action # 동일 리포지토리 액션 참조
  
  example-job-3:
    steps:
        uses: docker://alpine:3.8 # 도커 허브의 컨테이너 참조
```

또한 `inputs`, `outputs` 키를 통해 액션을 수행하는 데 필요한 입/출력 값을 지정할 수 있다

사용하는 액션의 리포지토리 최상위 디렉토리의 `action.yml`를 확인하면 정의된 `inputs`, `outputs`를 확인할 수 있다

```yaml
name: "Example"
description: "Receives file and generates output"
inputs:
  file-path: # input id
    description: "Path to test script"
    required: true
    default: "test-file.js"
outputs:
  results-file: # output id
    description: "Path to results file"
```

#### 스크립트, 쉘 커맨드 실행

`run` 키워드로 스크립트나 쉘 커맨드를 실행할 수 있다

```yaml
# `npm` 명령 실행
jobs:
  example-job:
    runs-on: ubuntu-latest
    steps:
      - run: npm install -g bats
```

```yaml
# 1. 리포지토리 체크아웃
# 2. 쉘 스크립트 실행 권한 부여
# 3. 쉘 스크립트 실행
jobs:
  example-job:
    runs-on: ubuntu-latest
    defaults:
      run:
        working-directory: ./scripts
    steps:
      - name: Check out the repository to the runner
        uses: actions/checkout@v5
      - name: Make the script files executable
        run: chmod +x my-script.sh my-other-script.sh
      - name: Run the scripts
        run: |
          ./my-script.sh
          ./my-other-script.sh
```

기본 쉘과 워킹 디렉토리를 지정하는 방법

```yaml
# 워크플로우 전체에 적용된다
defaults:
  run:
    shell: bash # 기본 쉘
    working-directory: ./scripts # 기본 워킹 디렉토리

# 해당 Job 내에서만 적용되며, 워크플로우 기본 값을 오버라이드한다
jobs:
  defaults:
    run:
```


## 컨테이너 레지스트리

컨테이너 레지스트리란 컨테이너 이미지를 관리하는 공간으로 깃허브에서는**GitHub Container Registry(GHCR)** 이라는 이름으로 제공된다

도커 허브와 유사한 컨테이너 이미지 저장소이지만 깃허브의 리포지토리, 워크플로우와 통합되어 있고 상세한 보안/권한 제어를 할 수 있다

```text
ghcr.io/<owner>/<image_name>:<tag>

ghcr.io/hansanhha/app:latest
ghcr.io/hansanhha/app:1.0.3
```

깃헙 액션 워크플로우 내에서 `GITHUB_TOKEN`을 사용하여 컨테이너 레지스트리에 인증할 수 있다 (`GITHUB_TOKEN`을 사용하면 한 번만 패키지 발행 가능)

PAT(Personal Access Token) classic도 이용할 수 있으나 보안상 권장되지 않는다

깃헙 호스트 러너는 기본적으로 `docker` 명령을 제공하므로 곧바로 도커 이미지 빌드/푸시를 할 수 있다

```yaml
name: Build and Push

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4

      - name: Log in to GHCR
        uses: docker/login-action@v3
        with:
          registry: ghcr.io
          username: ${{ github.actor }}
          password: ${{ secrets.GITHUB_TOKEN }}

      - name: Build image
        run: docker build -t ghcr.io/${{ github.repository_owner }}/my-api:latest .

      - name: Push image
        run: docker push ghcr.io/${{ github.repository_owner }}/my-api:latest
```


## 패키지 발행 (도커 이미지, JAR 등)

[공식 문서 참고](https://docs.github.com/en/actions/tutorials/publish-packages/publish-docker-images#about-image-configuration)


## 유용한 VS Code 익스텐션

로컬에서 셀프 호스트 러너 실행: [GitHub Local Actions](https://marketplace.visualstudio.com/items?itemName=SanjulaGanepola.github-local-actions) (도커 엔진 및 `nektos/act` CLI 도구 설치 필요)

로컬에서 리포지토리 깃헙 액션 관리: [GitHub Actions](https://marketplace.visualstudio.com/items?itemName=GitHub.vscode-github-actions)


## 가격 정책

무료: 셀프 호스트 러너 사용 또는 표준 깃허브 호스트 러너를 사용하는 퍼블릭 리포지토리

유료: 깃허브 플랜에 따라 일정량의 깃헙 액션 사용 시간(분 단위), 아티팩트/캐시 스토리지를 무료로 제공하고 추가 사용량만큼에 대한 지불 [무료 사용량 한도 참고](https://docs.github.com/en/billing/concepts/product-billing/github-actions#free-use-of-github-actions)

대형 러너는 무료 할당량에 포함되지 않으며 사용 시 무조건 과금된다

결제 수단을 등록하지 않았거나 지불 제한이 0으로 설정되어 있으면 무료 할당량 초과 시 실행이 차단된다

## 참고 문서 및 이미지 출처

https://docs.github.com/en/actions/get-started/understand-github-actions

