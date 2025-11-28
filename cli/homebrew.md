#### 인덱스
- [홈브류](#홈브류)
- [홈브류 설치](#홈브류-설치)
- [명령어](#명령어)
- [다른 패키지 매니저](#다른-패키지-매니저)


## 홈브류

홈브류: 맥/리눅스용(wsl2 포함) 오픈소스 패키지 관리자

홈브류는 소프트웨어를 포뮬러와 캐스크라는 형태로 관리한다

**포뮬러(Formulae)**
- 라이브러리/CLI 도구
- 패키지 설치 방법을 정의한 루비 스크립트를 읽고 다운로드 및 컴파일을 진행하여 설치한다
- bottle 이라고 하는 미리 컴파일된 바이너리 설치 및 사용
- 설치 경로: `/opt/homebrew/Celler`
  
**캐스크(Cask)**
- GUI 애플리케이션 또는 대규모 바이너리 파일 (`homebrew/cask` 리포지토리)
- `.dmg`, `.pkg` 설치 파일 다운로드
- 설치 경로: `/Applications` 또는 사용자 지정 경로

**코어**: 홈브류에서 기본적으로 제공하는 포뮬러 탭 (`homebrew/core` 리포지토리)

**탭(Tap)**: 홈브류에 외부 저장소를 **연결**하는 기능 (캐스크, 코어는 기본적으로 연결됨)


## 홈브류 설치

```shell
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```


## 명령어

**패키지 설치 및 삭제**
- `brew install [--cask] <package name>`
- `brew uninstall [--cask] <package name>`

**패키지 검색**
- `brew search [--cask] <package name>`: 패키지 검색
- `brew info [--cask] <package name>`: 패키지 상세 정보 조회
- `brew deps [--tree ]<package>`: 지정한 패키지의 의존성 나열

**설치된 패키지 검색**
- `brew list`: 설치된 포뮬러와 캐스크 목록 출력
- `brew outdated`: 설치된 패키지 중 오래된 버전의 패키지 나열
- `brew leaves`: 사용자가 직접 설치한 패키지 나열

**패키지 버전 업그레이드**
- `brew upgrade [package]`: 패키지 이름 생략 시 모든 패키지 버전 업그레이드
- `brew upgrade --dry-run`: 어떤 패키지가 업그레이드될지 시뮬레이션

**홈브류 청소**
- `brew cleanup`
- 홈브류가 관리하는 디렉토리에서 오래된 패키지 버전과 캐시를 삭제하여 공간을 정리한다

**탭 추가/삭제**
- `brew tap <repository url>`
- `brew untap <repository url>`
- 홈브류에서 외부 저장소(tap)를 추가하거나 제거한다
- 탭을 추가하면 로컬의 `/opt/homebrew/Library/Taps` 디렉토리에 복사한다
- 이후 `brwe install`로 해당 리포지토리의 패키지를 검색, 설치할 수 있다

**홈브류 업데이트**
- `brew update`
- 홈브류의 git 리포지토리(`homebrew/homebrew-core` `homebrew/homebrew-cask` 등)를 최신 커밋으로 동기화한다

```shell
$ brew update
==> Updating Homebrew...
Updated 2 taps (homebrew/core and homebrew/cask).

# 홈브류 기본 리포지토리 `homebrew/core`에 새롭게 추가된 포뮬러 목록
==> New Formulae 
geesefs           lld@19            llvm@19

# 홈브류 `homebrew/cask` 리포지토리에 새로 추가된 캐스크 목록
==> New Casks
font-lxgw-wenkai-gb-lite   moment
inmusic-software-center    slidepad

# 현재 사용 중인 패키지 중 업그레이드 가능한 목록
==> Outdated Formulae
asdf         glib         neovim       sqlite
fzf          harfbuzz     pcre2        xz
git          icu4c@76     python@3.13  zoxide
```

**홈브류 진단**
- `homebrew doctor`
- 사용 불가능 또는 deprecated 처리된 패키지가 있는지 알려준다
- 설정, 권한 문제, 충돌 가능성 등을 진단하여 문제를 찾아주고 해결 방법을 제안한다

**홈브류 설정 정보, 시스템 환경 출력**
- `brew config`
- 홈브류 버전, Git, Curl 등 홈브류와 관련된 정보를 출력한다

**필요한 환경변수 출력**
- `brew shellenv`
- 홈브류 쉘에서 올바르게 동작하게끔 필요한 환경 변수를 출력한다


## 다른 패키지 매니저

맥
- MacPorts
- Fink
- Nix
- pkgsrc

리눅스
- apt (debian/ubuntu)
- dnf/yum (fedora/rhel)
- pacman (arch linux)

윈도우
- Chocolatey
- Scoop
- Winget