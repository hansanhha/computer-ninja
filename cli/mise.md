#### 인덱스
- [mise](#mise)
- [개발 도구 버전 관리](#개발-도구-버전-관리)
- [개발 도구 버전 관리 명령어](#개발-도구-버전-관리-명령어)


## mise

mise는 asdf, nvm, direnv 등 기존의 다양한 개발 편의 CLI 도구를 통합하여 **로컬 개발 환경 관리 범용 도구**이다

주요 기능
- 개발 도구 버전 관리: 여러가지 프로그래밍 언어와 도구의 다양한 버전을 디렉토리 별로 관리할 수 있게 한다
- 환경 변수 관리: 프로젝트 별로 필요한 환경 변수를 설정할 수 있게 한다
- 태스크 러너: 자주 사용하는 스크립트나 명령어를 실행할 수 있게 한다

### mise 설치

```shell
# homebrew 설치
brew install mise

# 직접 설치 mac 실리콘 (macos-arm64) 
curl https://mise.jdx.dev/mise-latest-macos-arm64 > ~/.local/bin/mise
chmod +x ~/.local/bin/mise

# mise 활성화 (mise 컨텍스트 자동 로드)
mise activate
```

CI/CD 또는 스크립트같이 비대화형 쉘인 경우 활성화 대신 shim을 사용한다

심(shim)은 명령어를 가로채서 적절한 환경/파일을 로드해주는 심볼릭 링크 기능이다

```shell
# 쉘 시작 시 자동으로 활성화시키도록 로그인 파일에 명시
eval "$(mise activate zsh)" >> ~/.zshrc

# 활성화 여부 확인
mise doctor
```

mise와 관련된 자동 완성 기능을 사용할 수도 있다

```shell
# 자동 완성 기능을 사용하기 위한 의존성 설치
mise use -g usage

# oh-my-zsh를 사용한다면 plugins 목록에 mise를 추가한다
# plugins = ( ... mise )
source ~/.zshrc
```

[asdf 마이그레이션](https://mise.jdx.dev/faq.html#how-do-i-migrate-from-asdf)

[삭제](https://mise.jdx.dev/installing-mise.html#uninstalling)


## 개발 도구 버전 관리

mise에서는 버전 관리의 대상이 되는 개발 관련 도구나 개발 도구를 **dev-tools**라고 일컫는다

크게 2가지 프로세스를 거쳐 해당 개발 도구를 관리한다
- 개발 도구 설치: `mise install`, `mise use` 명령어 사용, `~/.local/share/mise/installs` 디렉토리에 설치된다
- 구성 파일 명시: `mise.toml` 또는 `.tool-versions`에 설치한 개발 도구 중 어느 버전을 사용할지 명시한다 (글로벌 파일 `~/.config/mise/config.toml`)

### 버전 결정 메커니즘

`mise activate`로 활성화하면 mise는 다음의 순서로 개발 도구 버전을 결정한다
- 디렉토리를 탐색하여 모든 구성 파일(`mise.toml`, `.tool-versions` 등)들을 찾은 다음 계층적으로 머지한다
- 구성 파일을 참고하여 개발 도구 버전을 결정한다
- 각 개발 도구를 가져올 백엔드(core, asdf, aqua 등)를 선택한다
- 해당 도구가 설치되어 있는지 확인하고 필요한 경우 설치한다
- 결정된 개발 도구 버전을 사용하기 위해 PATH와 환경변수를 설정한다

만약 로컬에서만 사용하고 Git 커밋에 제외시키고 싶으면 파일명을 `mise.local.toml`으로 설정하고 `.gitignore`에 추가하면 된다

mise는 아래의 디렉토리를 탐색하는데 개발 도구이 중복되는 경우 가장 높은 우선순위 파일의 내용이 적용된다
- `~/.config/mise/config.toml`: 모든 프로젝트에 적용되는 글로벌 구성 파일 (가장 낮은 우선순위)
- `~/directory/mise.toml`: 특정 디렉토리의 구성 파일
- `~/directory/project/mise.toml`: 특정 프로젝트의 구성 파일
- `~/directory/project/mise.local.toml`: 특정 프로젝트의 구성 파일 (가장 높은 우선순위, 로컬 전용/`.gitignore`)

### asdf와의 차이점

asdf는 특정 개발 도구에 대한 실행 경로를 심으로 미리 설정해놓고 사용자가 도구를 사용하는 시점에 가로챈다음, 실제로 사용할 도구를 호출하는 방식이다

이와 달리 mise는 프롬프트가 표시될 때마다 `mise dev-hook` 명령어를 호출하여 환경변수(PATH)에 직접 개발 도구를 설정하기 때문에 사용자가 도구를 호출하는 시점에 오버헤드가 아예 발생하지 않고 `which` 명령어가 바이너리의 실제 경로를 반환한다

그리고 `mise.toml` 또는 `.tool-versions` 파일이 수정되지 않으면 PATH 설정이 조기 종료되어 더 빠르게 프롬프트를 이용할 수 있다

asdf의 경우 도구를 설치하기 전에 `asdf plugin add <plugin_name>` 명령어로 플러그인을 먼저 설정해줘야했는데, mise는 필요한 경우 자동으로 수행한다

mise에서는 기본 레지스트리 대신 다른 레지스트리를 사용하고 싶을 때 `mise plugin add <plugin_name>`을 이용한다

### shims

심(shim)은 동적으로 도구의 버전을 결정하여 실행시켜주는 **프록시 역할** 을 하는 메커니즘이다

터미널에서 `node`, `python` 등 특정 명령어를 입력했을 때 심이 이 명령어를 가로채서 구성 파일을 확인한 뒤, 명시된 버전의 바이너리 파일에 요청을 위임한다

즉, 동적으로 어떤 버전의 프로그램이 실행되어야 하는지 결정하고 PATH를 수정한다

대화형 쉘의 경우 로그인 파일(`.zshrc` `.bashrc`)에 `eval "$(mise activate zsh)"` 명령어를 적용하여 mise를 활성화하면 동적으로 도구의 버전과 PATH를 설정할 수 있다

[위에서](#asdf와의-차이점) mise는 asdf와 달리 심대신 직접 개발 도구를 설정한다고 했는데 이 기능은 mise를 활성화해야 하며, 대화형 쉘의 경우 로그인 파일을 통해 자동으로 활성화를 할 수 있다

반면 CI/CD 환경이나 스크립트 같은 비대화형 쉘에서는 로그인 파일을 로드하지 않을 수 있기 때문에 `mise activate`를 매번 명시적으로 실행해야 한다

심은 이런 번거로운 작업을 줄이고 어떤 쉘이든 올바른 버전이 실행될 수 있게 하여 일관된 동작을 보장한다

`mise activate --shims` 명령으로 PATH를 수정하는 대신 심을 생성할 수 있다

[자세한 내용](https://mise.jdx.dev/dev-tools/shims.html)

### registry, backends, plugins

```text
┌───────────────────────┐
│      Registry         │  ← 플러그인 목록 (인덱스)
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│       Plugin          │  ← 특정 도구 설치 로직 정의
│    node, java, go     │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│       Backend         │  ← 실제 설치 엔진
│      asdf, npm,       │
│      cargo, pipx      │
└───────────────────────┘
```
- **플러그인**: 개발 도구를 설치/관리하기 위한 정보 보관
- **백엔드**: 플러그인을 참고하여 실제 개발 도구를 설치
- **레지스트리**: 플러그인 목록 제공 (실제 설치 담당 X, 메타데이터 관리 담당)

플러그인이 "무엇을 설치하겠다"를 설명하면 백엔드는 플러그인이 지정한 버전을 OS/CPU 아키텍처에 맞게 선택하고 설치 및 구성을 설정한다

mise에서 지원하는 백엔드 종류
- aqua
- ubi
- pipx
- npm
- vfox
- asdf
- go
- cargo
- dotnet

**개발 도구 설치 흐름**
- 플러그인 추가: `mise plugins install <new plugin name> [git url]`
- 개발 도구 설치: `mise install [tool@version]`

자주 사용되는 개발 도구에 대해 내장된 플러그인을 제공하므로 개발 도구 설치 시 플러그인 설치를 생략할 수 있다

**core-tools (내장 플러그인)**
- bun, deno, nodejs
- elixir, erlang
- go
- java
- python
- ruby
- rust
- swift
- zig

```shell
$ mise registry | grep java
java  core:java
```

```shell
$ mise registry | grep aws-cli
aws-cli  aqua:aws/aws-cli asdf:MetricMike/asdf-awscli
```


## 개발 도구 버전 관리 명령어

**설치 및 사용**
- `mise use [-g] <tool@version>`: 해당 버전의 도구 사용 (필요 시 설치) - `mise.toml` 참고, `-g`는 글로벌 파일 참고
- `mise unuse [-g] <tool@version>`: 해당 버전의 도구를 구성 파일에서 제거 (삭제 X)
- `mise install`: 구성 파일에 명시된 버전의 도구 설치 (사용 X)
- `mise uninstall <tool@version>`: 설치된 버전의 도구 삭제
- `mise upgrade <tool@version> [--bump]`: 메이저 버전 중 가장 최신의 마이너 버전 설치 (java 22.0.1 -> java 22.10.3), `--bump` 옵션은 가장 최신 메이저 버전을 설치 (java 22 -> 24)

**정보 확인**
- `mise ls`: 현재 시스템에 설치되거나 활성화된 도구 나열
- `mise ls-remote <tool@version> [prefix]`: 해당 도구에 대한 설치 가능한 모든 버전 나열, prefix를 지정하면 해당 prefix에 대한 모든 버전만 나열한다
- `mise config`: 현재 적용된 구성 파일과 개발 도구 표시

**기타**
- `mise exec <tool@version> -- <file>`: 해당 버전의 도구 커맨드 실행 (보통 `mise activate`나 심을 사용하지 않을 때 이용함)

## 사용되는 디렉토리

mise는 **POSIX 표준(XDG Base Directory Spec)**을 따른 디렉토리를 사용한다

`~/.config/mise/`: 전역 사용자 설정 디렉토리 (`~/.config`는 XDG 표준에서 config 파일을 두는 위치로 사용하는 디렉토리임)
- `config.toml`: 개발 도구, 로깅 옵션 등 전역 사용자 설정
- `config.<env>.toml`: 환경별 사용자 설정

`~/.local/share/mise/`: 플러그인, 개발 도구, 심, 다운로드 등 데이터를 저장하는 디렉토리