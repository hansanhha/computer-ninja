#### 인덱스
- [settings.gradle.kts](#settingsgradlekts)
- [settings.gradle.kts 작성](#settingsgradlekts-작성)


## settings.gradle.kts

settings.gradle(.kts) 파일은 프로젝트 전체 구조, 플러그인, 의존성 해결 전략을 정의하는 최상위 구성 스크립트이다

**그레이들 전체 빌드 흐름**

```text
초기화 -> 설정(구성) -> 실행
```

settings.gradle.kts 파일은 **초기화 단계**에서 가장 먼저 실행되어 빌드 시스템 자체에 대한 메타 정보를 구성한다
- 빌드에 참여하는 프로젝트 모듈 결정
- 플러그인, 라이브러리 저장소 설정
- 버전 카탈로그 설정
- 포함 빌드(`includeBuild()`로 컴포지트 빌드 구성)

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

## settings.gradle.kts 작성

```kotlin
// 클래스 import
import dev.aga.gradle.versioncatalogs.Generator.generate

// 플러그인을 어디서 어떻게 가져올지 정의한다
pluginManagement {    

    // 로컬 플러그인을 직접 만들어서 사용
    // gradle/plugins 디렉토리를 하나의 별도 빌드로 포함한다
    // 이 안에 있는 플러그인을 외부 라이브러리처럼 사용할 수 있음
    includeBuild("gradle/plugins") 

    // 모든 프로젝트에서 공통으로 사용할 플러그인 저장소 설정
    // 그레이들 공식 플러그인 포탈 지정
    // 저장소를 지정하지 않으면 java, application 같은 내장 코어 플러그인만 사용할 수 있음
    // 플러그인 저장소 종류: 공식, 구글(안드로이드), 로컬(mavenLocal), mavenCentral, 내부 리포지토리(maven) 등
    repositories {
        gradlePluginPortal()
    }
}

// settings.gradle 수준에서 플러그인 적용
// build.gradle에 있는 plugins 블럭은 프로젝트에 적용되고
// settings.gradle에 있는 plugins 블럭은 전체 빌드 구조에 영향을 끼친다
plugins {
    id("dev.aga.gradle.version-catalog-generator") version("4.0.0")
}
```

```kotlin
// 모든 프로젝트에서 사용할 의존성 정책 관리
dependencyResolutionManagement {

    // 모든 프로젝트에서 공통으로 사용할 라이브러리 저장소 설정
    // 각 모듈마다 repositories를 쓰지 않아도 됨 -> 일관성 유지
    repositories {
        mavenCentral()
    }

    // 버전 카탈로그 (라이브러리/플러그인 버전 중앙 관리)
    versionCatalogs {

        // 'libs' 라는 이름의 카탈로그 생성
        generate("libs") {

            // TOML 파일에서 의존성 목록을 읽어옴. 버전 충돌 방지/의존성 통합 관리
            fromToml("springBootDependencies")
        }
    }
}

// 전체 프로젝트 이름 정의 (빌드 결과물, IDE 표시, 아티팩트의 이름이 됨)
rootProject.name = "root project name"

// 서브 프로젝트 이름 정의 (멀티 프로젝트인 경우)
// 각 include가 하나의 서브 프로젝트가 됨
// 그레이들은 include된 것들만 프로젝트로 인식함
include("sub project directory name")
include("sub project directory name")
```

