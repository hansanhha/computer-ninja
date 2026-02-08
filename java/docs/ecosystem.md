#### 인덱스
- [자바 커뮤니티](#자바-커뮤니티)


## 자바 커뮤니티 

JCP, JSR, JEP

[JCP (Java Community Process)](https://www.jcp.org/en/home/index)
- 자바 기술의 표준을 만들고 관리하는 총괄 기구
- 자바 언어나 플랫폼에 공식적으로 추가되기 위한 모든 절차를 담당한다

[JSR (Java Specification Request)](https://www.jcp.org/ja/jsr/overview)
- 자바의 기능 추가/개선 사항을 JCP에 제출하는 공식 제안서
- 제출된 JSR는 검토, 참조 구현, 테스트 도구 모음(Test Compatability Kit, TCK) 개발 등의 과정을 거쳐 최종 명세로 승인된다
- 참조 구현과 TCK가 모두 준비되면 최종 투표를 통해 JSR이 최종적으로 승인되고, 승인된 기능은 이후 자바 공식 버전(JDK)에 포함될 수 있다

![jsr life cycle](https://www.jcp.org/images/JSR_Life_Cycle_Dec2018.png)

[JEP](https://openjdk.org/jeps/0)
- JDK의 새로운 기능이나 개선 사항을 제안하는 공식 문서
- JSR은 자바 언어와 플랫폼 전반을 다루고, JEP는 JDK의 특정 기능의 세부 구현에 초점을 맞춘다
- JSR과 별개로 OpenJDK 커뮤니티에서 제안 및 검토되며 최종적으로 JDK의 특정 버전에 포함될지 결정된다
- Draft -> Candidate -> Funding -> Featured -> Rejected -> Completed
- Draft 상태인 JEP는 프리뷰 또는 인큐베이터로써 기능이 일시적으로 제공될 수 있다
- [JEP 444: 가상 스레드](https://openjdk.org/jeps/444)

![jep workflow](https://cr.openjdk.org/~mr/jep/jep-2.0-fi.png)


## 기술스택

언어/런타임: 자바/JVM, 코틀린/JVM

빌드 도구: 메이븐, 그레이들

프레임워크: 스프링, 아르메리아, Micronaut, Quarkus

웹 서버/컨테이너: Tomcat, Jetty, Undertow, Netty

ORM/데이터 액세스: Hibernate, JPA, MyBatis, Spring Data JDBC

데이터베이스: MySQL, PostgreSQL, Oracle, H2 (인메모리), MongoDB, Redis (캐싱/키-밸류)

테스트 프레임워크: JUnit, TestNG, Mockito, Spring Boot Test, AssertJ

로깅: Log4j, Logback, SLF4J

보안: Spring Security, JWT, OAuth2

REST 클라이언트: RestTemplate, WebClient, Feign Client

비동기/반응형: Project Reactor, RxJava

정적 분석: Checkstyle, PMD, SonarQube

코드 품질/포맷팅: SpotBugs, Lombok

API 문서화: Swagger/OpenAPI, Spring REST Docs

모니터링/메트릭스: Micrometer, Prometheus, Grafana, Spring Boot Actuator

클라우드/배포: AWS, Azure, Google Cloud, Heroku

컨테이너화: Docker, Podman

오케스트레이션: Kubernetes, Docker Compose

CI/CD: GitHub Actions, Jenkins, GitLab CI, CircleCI

버전 관리: Git, GitHub, GitLab, Bitbucket

IDE/개발 도구: IntelliJ IDEA, Eclipse, VS Code