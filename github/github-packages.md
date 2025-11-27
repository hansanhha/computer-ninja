#### 인덱스
- [깃허브 패키지](#깃허브-패키지)


## 깃허브 패키지

```text
GitHub
 ├─ Repositories (소스코드)
 └─ GitHub Packages (패키지)
      ├─ Package Registries (아티팩트)
      │    ├─ Maven
      │    ├─ npm
      │    ├─ NuGet
      │    ├─ RubyGems
      │    └─ Generic (ZIP 등)
      │
      └─ Container Registry (도커/OCI, ghcr.io)
```

깃허브 패키지는 **패키지 레지스트리**이다

**깃허브(repo)**: 코드 저장소

**깃허브 패키지**: 패키지 저장소 (도커/OCI 이미지, 라이브러리, 아티팩트)

일반적으로 사용하는 깃허브 리포지토리는 **소스코드를 관리**하기 위한 목적이고 깃허브 패키지는 **빌드 결과물을 관리**하기 위해 사용한다

**깃허브는 둘을 합쳐서 개발/빌드/배포를 하나의 플랫폼 안에서 끝낼 수 있는 구조를 구축해놓았다**

크게 패키지 레지스트리와 컨테이너 레지스트리를 제공하여 패키지를 깃허브 플랫폼 내에 저장할 수 있게 해준다

### 패키지 레지스트리

패키지 레지스트리는 개발자가 깃허브 패키지 플랫폼 내에 아티팩트(빌드 결과물)를 설치/다운받을 수 있게 해준다

깃허브 패키지에서 지원하는 패키지 레지스트리
- 자바스크립트: `npm` (`package.json`)
- 루비: `gem` (`Gemfile`)
- 자바: `mvn` (`pom.xml`), `gradle` (`build.gradle`/`build.gradle.kts`)
- 닷넷: `dotnet` (`nupkg`)

#### 예시

그레이들 JAR 파일을 깃허브 패키지로 퍼블리시하는 publishing 태스크 설정

```java
plugins {
    `maven-publish`
}
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/<OWNER>/<REPOSITORY>")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}
```

깃헙 액션에서 publishing 태스크를 실행하여 아티팩트를 깃허브 패키지 레지스트리에 업로드

```yaml
- name: Publish package
  run: ./gradlew publish
  env:
    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
```


### 컨테이너 레지스트리

도커/OCI 이미지를 푸시, 풀할 때 도커 허브 대신 깃허브에서 제공하는 컨테이너 레지스트리인 **GitHub Container Registry(GHCR)**를 이용할 수 있다

깃허브 패키지 중 컨테이너만 따로 확장한 레지스트리로 깃헙 액션 CI/CD 파이프라인에서 빌드-배포 흐름을 자연스럽게 이을 수 있다


