[mise 소개 및 시작](#mise-소개-및-시작)

[dev-tools 메커니즘](#dev-tools-메커니즘)

[dev-tools 명령어](#dev-tools-명령어)



## mise 소개 및 시작

mise는 asdf, nvm, direnv 등 기존의 다양한 개발 편의 CLI 도구를 통합하여 **특정 언어에 종속되지 않는 범용 개발 환경 관리 도구** 로 다음의 기능을 제공한다

버전 관리: 여러가지 프로그래밍과 런타임 도구를 다양한 버전으로 디렉토리 별로 관리할 수 있게 한다

환경 변수 관리: 프로젝트 별로 필요한 환경 변수를 설정할 수 있게 한다

태스크 러너: 자주 사용하는 스크립트나 명령어를 실행할 수 있게 한다


```shell
# homebrew 설치
brew install mise

# 직접 설치 mac 실리콘 (macos-arm64) 
curl https://mise.jdx.dev/mise-latest-macos-arm64 > ~/.local/bin/mise
chmod +x ~/.local/bin/mise
```

mise 활성화(activate)를 하면 대화형 쉘에서 쉘 세션에 mise 컨텍스트(도구, 환경 변수)를 자동으로 로드한다

CI/CD 또는 스크립트같이 비대화형 쉘인 경우 활성화 대신 shim을 사용할 수 있다

심(shim)은 명령어를 가로채서 적절한 환경으로 로드해주는 mise 바이너리에 대한 심볼릭 링크이다

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


## dev-tools 메커니즘


#### 도구 설치

특정 도구를 설치하고 사용하려면 **도구를 설치하는 것** 과 **구성 파일(`mise.toml`, `.tool-versions`)에 해당 도구를 사용할 것이라고 명시** 해야 한다

mise가 설치한 도구는 `~/.local/share/mise/installs`에 위치한다

#### 런타임 설정

mise를 활성화하면 자동으로 현재 디렉토리의 `mise.toml` 파일을 탐색하고 명시된 내용을 기반으로 컨텍스트를 로드하여 PATH 환경 변수에 바이너리 런타임을 설정한다 (현재 디렉토리에 없으면 부모 디렉토리로 올라가서 탐색함)

디렉토리를 이동할 때마다 mise가 구성 파일을 확인한 뒤 PATH 환경 변수를 설정하기 때문에 디렉토리 별로 유연하게 의존성을 관리할 수 있다

mise는 asdf의 `.tool-versions` 파일과도 호환되기 때문에 asdf에서 넘어온 경우에도 해당 파일을 그대로 사용할 수 있다

만약 로컬에서만 구성 파일을 사용하고 싶다면 파일명을 `mise.local.toml`이라고 지정하고 `.gitignore`에 추가하면 된다

mise는 아래와 같은 순서로 구성 파일을 탐색하여 컨텍스트를 적용하는데 더 가까운 파일이 우선시된다 
- `~/.config/mise/config.toml`: 모든 프로젝트에 적용되는 글로벌 구성 파일
- `~/work/mise.toml`: 특정 디렉토리의 구성 파일
- `~/work/project/mise.toml`: 특정 프로젝트의 구성 파일
- `~/work/project/mise.local.toml`: 특정 프로젝트의 구성 파일 (로컬 전용)


#### asdf와의 차이점

mise는 프롬프트가 표시될 때마다 `mise dev-hook` 명령어를 호출하여 새 환경 변수를 불러오는데, 만약 `mise.toml` 또는 `.tool-versions` 파일이 수정되지 않으면 조기 종료된다

그리고 asdf처럼 shim을 통해 동적으로 도구를 호출하는 방식이 아닌 PATH에 직접 런타임을 설정하기 때문에 해당 도구를 호출한다고 해도 오버헤드가 아예 발생하지 않고 `which` 명령어가 바이너리의 실제 경로를 반환한다

asdf의 경우 도구를 설치하기 전에 `asdf plugin add <plugin_name>` 명령어로 플러그인을 먼저 설정해줘야했는데, mise는 필요한 경우 자동으로 수행한다

mise에서는 기본 레지스트리 대신 다른 레지스트리를 사용하고 싶을 때 `mise plugin add <plugin_name>`을 이용한다

### shims

심(shim)은 동적으로 도구의 버전을 결정하여 실행시켜주는 **프록시 역할** 을 하는 메커니즘이다

터미널에서 `node`, `python` 등 특정 명령어를 입력했을 때 심이 이 명령어를 가로채서 구성 파일을 확인한 뒤, 명시된 버전의 바이너리 파일에 요청을 위임한다

즉, 동적으로 어떤 버전의 프로그램이 실행되어야 하는지 결정하고 PATH를 수정한다

대화형 쉘의 경우 로그인 파일(`.zshrc` `.bashrc`)에 `eval "$(mise activate zsh)"`와 같은 명령어를 적용하여 mise를 활성화하여 동적으로 도구의 버전과 PATH를 설정할 수 있다

반면 비대화형 쉘(CI/CD, 스크립트 등)은 `.zshrc` 같은 쉘 설정 파일을 로드하지 않을 수 있기 때문에 `mise activate`를 매번 명시적으로 실행해야 된다

심은 이런 번거로운 작업을 줄이고 어떤 쉘이든 올바른 버전이 실행될 수 있게 하여 일관된 동작을 보장한다

### registry, backends, plugins

레지스트리는 별칭이 지정된 모든 도구의 목록이다

`mise install java@22`와 같은 명령어에서 java는 mise에서 java 도구에 대한 별칭을 지어준 것이다

mise는 각 별칭과 특정 플러그인을 매핑하여 해당 플러그인을 통해 다양한 버전을 설치할 수 있도록 해준다

하나의 도구에는 여러 개의 플러그인이 지원되며 [이 곳](https://mise.jdx.dev/registry.html#tools)에서 각 도구와 매핑 목록을 확인할 수 있다

참고로 mise에는 core-tool이라고 하는 내장된 플러그인이 있는데, 이들은 설치 시 별도의 플러그인을 추가하지 않아도 된다. 다른 나머지 도구들은 해당 도구에 지원되는 플러그인을 설치해야만 사용할 수 있다 (플러그인 설치는 mise가 자동으로 해줌)

각 도구마다 여러 개의 플러그인이 지원되는 것은 해당 도구의 버전 관리, 다운로드 등의 수행을 담당하는 도구가 여러 개 있기 때문인데 mise는 다양한 baceknd를 활용하여 다양한 도구와 언어를 지원할 수 있는 것이다

`mise use` 명령을 사용하면 해당 도구에 매핑된 플러그인을 기반으로 적절한 backend를 결정하고, 이 backend가 설치/구성 등 도구를 사용하기 위한 필수 작업들을 처리한다 

간단하게 설명하면 backend는 aqua, asdf같은 버전 관리 매니저이고, 플러그인은 특정 도구를 설치/관리하는 기능으로 각 backend 별로 이들을 관리하는 레지스트리(위에서 설명한 레지스트리와 다름)를 제공한다

mise는 보안상의 이유로 새로운 도구에 대해서 웬만하면 asdf와 vfox의 플러그인을 받지 않으며 대신 aqua와 ubi 사용을 권장한다

```text
별칭        plugin 목록(backend: aqua, ubi, cargo, asdf)

bat         aqua:sharkdp/bat
            ubi:sharkdp/bat
            cargo:bat
            asdf:https://gitlab.com/wt0f/asdf-bat
```


core-tools
- bun, deno, nodejs
- elixir, erlang
- go
- java
- python
- ruby
- rust
- swift
- zig

[backends](https://mise.jdx.dev/dev-tools/backends/)
- aqua
- ubi
- pipx
- npm
- vfox
- asdf
- go
- cargo
- dotnet


## dev-tools 명령어


`mise u|use [-g] <tool@version>`: 현재 구성 파일(`mise.toml`, `.tool-versions`)에 주어진 도구의 버전을 명시하고 필요한 경우 다운로드받는다. -g 옵션 사용 시 글로벌 toml파일에 해당 도구와 버전을 명시한다

`mise unuse [-g] <tool@version>`: 구성 파일에 명시된 도구의 특정 버전을 제거한다

`mise install`: 구성 파일에 명시된 의존성을 설치만 하고 `mise.toml` 파일에 명시하지 않는다

```shell
# java 24 버전 설치 (openjdk)
mise install java@24

# openjdk의 특정 배포판 설치 (prefix의 가장 최신 버전)
mise install java@liberica-24

# openjdk의 특정 배포판 설치 (특정 prefix)
mise install java@liberica-24.0.1+11

# 구성 파일에 명시된 모든 플러그인과 도구 설치
mise install

# liberica 24 버전을 전역 PATH에 지정한다
mise use -g java@liberica-24
```

`mise uninstall <tool@version>`: 설치 삭제

`mise exec|x <tool@version> -- <file>`: `mise activate`나 shim을 사용하지 않은 경우 mise를 이용할 수 있는 명령어 (mise가 마찬가지로 구성 파일을 스캔하며 설치되지 않은 경우 자동으로 도구를 설치함)

`mise upgrade <tool@version> [--bump]`: 해당 도구의 prefix를 기준으로 가장 최신 버전을 설치한다(java 22.0.1 -> java 22.1.5), --bump 옵션을 주면 가장 최신 버전으로 업그레이드한다 (java 22 -> 24)

`mise ls-remote <tool@version> [prefix]`: 해당 tool에 대한 설치 가능한 모든 버전 나열, prefix를 지정하면 해당 prefix에 대한 모든 버전만 나열한다

`mise ls`: 현재 시스템에 설치되거나 활성화된 도구 나열

`mise config`: 현재 적용된 구성 파일과 도구들을 표시한다
