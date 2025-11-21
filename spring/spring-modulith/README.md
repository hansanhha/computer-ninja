#### 인덱스
- [모듈라 모놀리스](#모듈라-모놀리스)
- [스프링 모듈리스](#스프링-모듈리스)
- [애플리케이션 모듈](#애플리케이션-모듈)
- [모듈 검증](#모듈-검증)
- [이벤트: 모듈 간 느슨한 결합](#이벤트-모듈-간-느슨한-결합)
- [통합 테스트](#통합-테스트)
- [문서화](#문서화)
- [옵저버빌리티](#옵저버빌리티)
- [스프링 모듈리스 한계](#스프링-모듈리스-한계)
- [스프링 모듈리스 vs 그레이들 멀티 모듈](#스프링-모듈리스-vs-그레이들-멀티-모듈)


## 모듈라 모놀리스

<img src="./images/Monolithic-Architecture.webp" alt="monolith architecture" />

**모놀리스 아키텍처**는 하나의 애플리케이션 단위로 구성된 통합형 아키텍처이다

시스템의 모든 기능이 **단일 프로세스** 안에서 동작하며 단일 코드베이스와 단일 빌드 아티팩트(WAR/JAR)로 관리된다

즉, API, 서비스 로직, 리포지토리, 배치, 이벤트, 캐시 등이 하나의 JVM 프로세스에서 동작하는 것이다

주로 하나의 RDB 스키마, 단일 DB를 사용하며 동일 애플리케이션에서 트랜잭션이 실행된다

<img src="./images/modular_monolith.webp" alt="modular monolith" style="width: 70%"/>

**모듈라 모놀리스 아키텍처**는 모놀리스 아키텍처이지만 기능별로 독립된 도메인 모듈로 코드를 구분한다

모듈 간 경계를 논리적으로 엄격히 구분하고 의존성을 최소화하며 모듈 내부는 캡슐화, 모듈 외부는 공개 API(메서드 호출)를 사용하여 통신한다

모놀리스와 마찬가지로 여전히 하나의 배포 단위이며, 단일 JVM 프로세스에서 동작한다

단지 하나일 뿐이지만 내부는 기능별로 마이크로서비스처럼 깔끔하게 분리되어 있는 구조이다

일반적으로 MSA로 확장하기 전에 모듈라 모놀리스 아키텍처를 사용하여 미리 도메인 간 경계를 구분해놓고 필요 시 효율적으로 아키텍처를 전환할 수 있도록 한다

MSA와 달리 네트워크 대신 메서드 호출로 모듈과 모듈의 통신을 대신하여 복잡함이 덜하고 단일 애플리케이션만 바로 실행할 수 있다

다만 특정 모듈만 스케일링할 수 없으며 대규모 트래픽을 감당하기에 어렵고 특정 모듈에서 실패가 발생하면 해당 애플리케이션 전체가 동작하지 않을 가능성이 크다

자바/스프링 진영에서 모듈라 모놀리스 아키텍처를 구축할 수 있는 도구는 크게 **그레이들 멀티 모듈**과 **스프링 모듈리스**가 있다 (코드 레벨 아키텍처 제외)


## 스프링 모듈리스

스프링 모듈리스는 도메인 중심, 모듈화된 애플리케이션을 빌드할 때 주로 사용되는 디렉토리 구조나 상호작용 방식 등을 **제안**하여 비즈니스 요구사항에 빠르게 대응할 수 있도록 하는 도구 모음 프로젝트이다

**스프링 모듈리스의 철학: 기능(feature)이 아니라 도메인을 기준으로 모듈을 나눈다**

일반적으로 자주 사용하는 Controller, Service, Repository로만 구성된 기술적 레이어가 아니라 도메인을 기준으로 상위 패키지를 나눈다

단일 배포 단위(monolithic) 애플리케이션의 내부를 논리적인 모듈로 나눠 관리할 수 있게 해준다

**주요 기능**
- 도메인 중심 코드 설계 지원
- 모듈 간 경계 검증 (ArchUnit)
- 모듈 간 느슨한 결합 (이벤트 레지스트리)
- 모듈 단위 통합 테스트 (`@ApplicationModuleTest`)
- 런타임 모듈간 관측성(Observability) 제공
- 문서 자동화 (C4-style-view, AsciiDoc)

스프링 모듈리스는 **스프링 부트를 기반으로 작동**한다 [스프링 부트 + 스프링 모듈리스 호환성 표](https://docs.spring.io/spring-modulith/reference/appendix.html#compatibility-matrix)


## 애플리케이션 모듈

```text
modulith app                     // 애플리케이션 메인 패키지
 ├─ @SpringBootApplication
 │       
 ├─ order                        // 애플리케이션 모듈 패키지
 │   │
 │   ├─ application              // order 모듈에서만 사용하는 패키지
 │   │   └─ OrderService.java
 │   │
 │   ├─ nested                   // order 모듈의 중첩 모듈
 │   │   ├─ package-info.java    // @ApplicationModule
 │   │   └─ NestedApi.java
 │   │
 │   └─ OrderManagementApi.java  // 외부에 공개되는 API(facade)
```

스프링 모듈리스는 `@SpringBootApplication`이 위치한 애플리케이션 메인 패키지의 바로 아래의 패키지를 **애플리케이션 모듈 패키지**로 취급한다

애플리케이션 모듈은 다음과 같은 유닛들로 구성된다

**API**
- 다른 모듈에 노출되는 스프링 빈 또는 모듈에서 발행하는 애플리케이션 이벤트로, provided interface라고 한다
- 모듈 베이스 패키지가 API 패키지로 간주된다. 기본적으로 API 패키지만 외부에서 접근할 수 있다
- 명시적인 설정을 통해 내부 컴포넌트를 외부에 노출시킬 수 있다 [모듈 부분 공개](#모듈-부분-공개)

**내부 컴포넌트**
- 모듈 베이스 패키지의 모든 하위 패키지는 해당 모듈에서만 사용할 수 있는 내부 컴포넌트이다
- 기본적으로 다른 모듈의 접근을 허용하지 않기 때문에 다른 모듈에 노출하려면 모듈에서 명시적으로 공개 설정해야 하며, 의존 모듈은 명시적으로 의존 설정을 해야 접근할 수 있다

**참조**
- 다른 모듈에서 스프링 DI로 의존하는 빈, 리스닝하는 이벤트, 노출된 프로퍼티 등을 말하며 required interface라고 한다

**중첩 모듈**
- 1.3 버전부터 모듈 안에 중첩된 모듈을 지원한다. 하위 패키지의 `package-info.java` 파일에 `@ApplicationModule`을 명시하면 내부 컴포넌트가 아닌 중첩 모듈이 된다
- 중첩 모듈(nested)의 코드는 오직 상위 모듈(order) 또는 형제 모듈에서 사용할 수 있다
- 중첩 모듈의 코드에서 내부 컴포넌트를 포함한 부모 모듈의 모든 코드와 다른 모듈의 최상위 레벨 API에 접근할 수 있다


### 모듈 전체 공개

```java
@org.springframework.modulith.ApplicationModule(
  type = Type.OPEN
)
package example.inventory;
```

`package-info.java`에 위와 같이 명시하여 모듈을 공개하면 다른 모듈이 해당 모듈의 내부 컴포넌트에 모두 접근할 수 있다

### 모듈 부분 공개

모듈 베이스 패키지 하위의 모든 패키지는 기본적으로 외부에 노출되지 않는 내부 컴포넌트가 된다

특정 패키지에 대한 외부의 접근을 허용하려면 `package-info.java` 파일에 `@NamedInterface` 또는 `@org.springframework.modulith.PackageInfo`를 명시해야 한다

```text
modulith app                     // 애플리케이션 메인 패키지
 ├─ @SpringBootApplication
 │       
 └─ order                        // 애플리케이션 모듈 패키지
     │
     └─ application              // 외부 공개 패키지 (기본값 private)
         ├─ package-info.java    // @NamedInterface
         └─ OrderService.java
```

```java
// 외부에 공개할 인터페이스의 이름을 명시한다
@org.springframework.modulith.NamedInterface("application")
package modulith.order.application;
```

### 다른 모듈에 대한 의존성 명시

payment 모듈에서 order 모듈이 공개한 application 인터페이스(패키지)를 의존한다면 아래와 같이 `package-info.java` 파일에 명시할 수 있다

```java
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"order :: application"}
)
package modulith.payment;
```

위처럼 명시하면 order 모듈의 application 패키지에 접근할 수 있게 되지만, 원래 기본적으로 접근할 수 있는 API 패키지(order 베이스 패키지)에 대한 접근이 허용되지 않는다

이 경우 order도 같이 명시하거나 애스터리스크를 사용하여 order 모듈에서 공개한 모든 패키지에 대한 의존성을 명시한다

```java
@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"order", "order :: application"}
)
package modulith.payment;

@org.springframework.modulith.ApplicationModule(
    allowedDependencies = {"order :: *"}
)
package modulith.payment;
```


## 모듈 검증

```java
ApplicationModules.of(SpringModulithApplication.class).verify();
```

위의 애플리케이션 모듈 검증은 아래의 규칙을 따른다

**모듈 간 순환 의존 X**

**API 패키지를 통해서만 외부 모듈 접근**

**의존하는 모듈과 패키지 명시** (생략 가능)

아래와 같이 `verify` 대신 `detectViolations` 메서드를 사용하면 규칙 위반에 접근할 수 있다

```java
ApplicationModules.of(…)
  .detectViolations()
  .filter(violation -> …)
  .throwIfPresent();
```


## 이벤트: 모듈 간 느슨한 결합

스프링 모듈리스는 각 모듈 간 느슨한 결합 구조를 지원하기 위해 이벤트 발행 - 소비 모델을 제공한다

참고로 스프링 모듈리스 core 스타터 아티팩트에는 이벤트 아티팩트가 빠져있다

이벤트 기능을 이용하여 모듈 간 결합도를 낮추려면 이벤트 모듈을 추가하거나 스프링 모듈리스에서 영속성 기술에 따른 스타터를 제공하는데, 이를 사용하면 된다

`org.springframework.modulith:spring-modulith-starter-jpa`

### 도메인 이벤트

`OrderCreated`, `PaymentCompleted`와 같이 특정 도메인(모듈)에서 정의한 비즈니스 이벤트이다

모듈은 자신의 비즈니스 변화를 이벤트로 표현하여 외부로 송신하고, 이 이벤트를 다른 모듈에서 소비하여 처리한다

다른 모듈의 메서드를 직접 호출하지 않고도 비즈니스 흐름을 유지할 수 있게 하는 주요 매개체 역학을 한다

### `ApplicationEventPublisher`

스프링 컨텍스트가 제공하는 이벤트 발행 기능기를 이용하여 애플리케이션 내에서 도메인 이벤트를 발행한다

스프링의 애플리케이션 이벤트는 JVM 내부의 단순 메시지 브로커 역할로 설계되었다

즉, 비동기 메시징 시스템이 아니며 트랜잭션에 정확히 맞춰 동작하여 트랜잭션 일관성을 보장하고, 단순하며 예측 가능한 제어 흐름을 만들 수 있다

### `@Async`, `@TransactionalEventListener`, `@ApplicationModuleListener`

스프링의 이벤트 발행기를 이용하면 도메인 간 결합도를 낮출 수 있지만 다음과 같은 문제가 발생할 수 있다
- 소비자(리스너)의 처리가 늦어질수록 발행자의 이벤트 발행 속도도 같이 느려진다
- 발행자와 리스너가 글로벌 트랜잭션으로 묶여 있기 때문에 리스너에서 예외가 발생하면 상위 트랜잭션에 전파된다 -> 연쇄 실패 위험
- 발행자는 모든 리스너를 순서대로 호출하기 때문에 리스너 수가 늘어날수록 전체 처리 시간이 증가한다

이벤트 퍼블리셔의 성능 영향을 최소화하기 위해 아래와 같이 `@Async`를 사용한다

`@Async` 어노테이션을 사용하면 퍼블리셔는 리스너를 즉시 반환하고, 리스너는 발행자로부터 독럽적인 스레드풀에서 실행된다

비동기 실행이므로 트랜잭션 경계를 벗어나고, 영속성 컨텍스트가 유지되지 않는다 - 더군다나 재시도/보장된 전달 기능을 제공하지 않는다

```java
@Component
public class PaymentListener {

    @Async
    @EventListener
    public void on(OrderCompletedEvent event) {
        processPayment(event);
    }
}
```

`@TransactionalEventListener`는 트랜잭션의 특정 상황에만 이벤트를 처리하도록 보장하는 리스너이다

커밋 전/후, 롤백 이후, 완료 이후 시점에 동작할 수 있다

이벤트는 대게 도메인 상태 변화가 확정된 후 처리하는 것이 자연스럽기 때문에 주로 커밋 후 시점을 사용한다

리스너를 비동기로 실행하면 발행자와 다른 스레드에서 동작하므로 트랜잭션을 공유하지 못하기 때문에 새로운 트랜잭션에서 로직을 처리해야 한다

따라서 비동기 + 트랜잭션 커밋 이후 시점 리스너를 제대로 활용하려면 아래와 같이 세 개의 어노테이션을 선언해야 한다

```java
@Component
public class PaymentListener {

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void on(OrderCompletedEvent event) {
        processPayment(event);
    }
}
```

스프링 모듈리스는 위의 세 가지 어노테이션을 합친 모듈 간 통신이라는 문맥에 맞게 더 명시적이고 일관성 있는 어노테이션을 제공한다 (syntatic sugar)

참고로 1.1 버전부터 core에서 제공하는 `@ApplicationModuleListener`는 deprecated 처리되었으며 events 모듈에서 제공하는 걸 사용하면 된다

```java
@Component
public class PaymentListener {
    
    @ApplicationModuleListener
    public void on(OrderCompletedEvent event) {
        processPayment(event);
    }

    // 아래의 어노테이션을 모두 포함한다
    // @Async
    // @Transactional(propagation = Propagation.REQUIRES_NEW)
    // @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
}
```

### 이벤트 발행 레지스트리

스프링이 제공하는 이벤트 퍼블리셔는 JVM 내에서만 동작하며 별도로 이벤트를 따로 저장하거나 재시도하는 메커니즘을 제공하지 않는다

스프링 모듈리스는 DDD 스타일의 모듈 아키텍처 애플리케이션 구축을 지원하기 때문에 Outbox 기반 이벤트 저장(DB에 도메인 상태 변화와 이벤트를 함께 저장하는 패턴), 자동 재시도, 이벤트 외부화(kafka) 등의 기능을 제공한다

이벤트 발행 레지스트리가 **이벤트 발행과 소비를 DB에 기록**하는 시스템이다 (Outbox)

스프링 모듈리스는 `event_publication` 이름의 이벤트 기록 테이블을 자동적으로 생성하며, 이벤트가 발행되면 기존 비즈니스 트랜잭션(`@TransactionalEventListener`)의 일부로 이벤트 정보를 DB에 저장한다
- 발행 시점
- 완료 시점
- 이벤트 타입
- 직렬화된 이벤트 내용
- 리스너 id

이벤트 저장을 위해 각 영속성 기술별로 스타터를 제공하며 기본적으로 잭슨 기반 이벤트 시리얼라이저를 사용한다
- JPA: `spring-modulith-starter-jpa`
- JDBC: `spring-modulith-starter-jdbc`
- MongoDB: `spring-modulith-starter-mongodb`
- Neo4j: `spring-modulith-starter-neo4j`

### 이벤트 발행 완료 처리 또는 재발행

이벤트 발행 기록은 `@Transactional` 또는 `@ApplicationModuleListener`이 성공한 경우 완료 시점을 업데이트함으로써 이벤트 완료로 처리한다

완료된 이벤트가 계속해서 데이터베이스에 쌓여가여 성능 저하가 발생할 수 있으니 데이터베이스에서 삭제해야 한다

기본적으로 스프링 모듈리스 이벤트 아티팩트에서 제공하는 `CompletedEventPublications`를 통해 이벤트 발행 테이블에 접근하여 완료 처리된 데이터를 모두 삭제하거나, 기준 시간보다 오래된 경우에 삭제한다

1.3 버전부터는 `spring.modulith.events.completion-mode` 프로퍼티를 제공하여 3가지 동작을 지원한다
- `UPDATE`: 기본 모드, 이벤트 발행 테이블에 접근하여 완료 처리된 이벤트 기록 제거 (모두 제거 or 기준 시간보다 오래된 경우만 제거)
- `DELETE`: 이벤트 완료 시 해당 기록 레코드를 즉시 삭제 (`CompletedEventPublications` 사용 안함)
- `ARCHIVE`: 이벤트 완료 시 별도의 아카이브 테이블에 저장하고 기존 이벤트 발행 기록 테이블에서는 제거

이벤트 처리를 실패한 경우 `IncompleteEventPublications`를 이용하여 특정 조건식에 만족하거나 기존 발행 시간보다 오래된 경우 재발행할 수 있다


## 통합 테스트

`testImplementation("org.springframework.modulith:spring-modulith-starter-test")`

```java
@ApplicationModuleTest
public class OrderIntegrationTest {
    
    @Test
    void contextLoads() {
    }
}
```

테스트 클래스가 있는 애플리케이션 모듈을 한정으로 스프링 컨텍스트가 부트스트랩된다

`org.springframework.modulith` 로그 레벨을 `DEBUG`로 설정하면 테스트 환경 정보를 상세히 확인할 수 있다

부트스트랩 모드
- `STANDALONE`: 테스트 클래스가 속한 모듈만 실행, 기본값
- `DIRECT_DEPENDENCIES`: 테스트 클래스가 속한 모듈이 직접 의존하는 모듈까지 실행
- `ALL_DEPENDENCIES`: 테스트 클래스가 속한 모듈이 직접 의존하는 모듈과 전이적으로 의존하는 모듈까지 실행

모듈이 다른 모듈의 빈을 의존하는 상태에서 테스트를 수행할 때는 **`@MockitoBean`** 또는 **도메인 이벤트**를 사용해야 한다

기본적으로 테스트 수행 모듈을 기반으로 스프링 컨텍스트를 구축하기 때문에 다른 모듈의 의존성이 포함되면 부트스트랩에 실패한다

```java
@ApplicationModuleTest
class OrderIntegrationTest {

  @MockitoBean SomeOtherComponent someOtherComponent;
}
```

### 테스트 시나리오

스프링 모듈리스는 비동기(이벤트), 트랜잭션 이벤트 리스너 기반의 통합 테스트를 수행할 수 있는 시나리오 추상화를 제공한다

모듈 통합 테스트의 테스트 메서드에 `Senario` 파라미터를 선언하여 시나리오 API를 이용할 수 있다

```java
@ApplicationModuleTest
class OrderIntegrationTest {

  @Test
  public void someTest(Scenario scenario) {
    
  }
}
```

가장 먼저 특정 이벤트가 발행되는 상황이나 모듈의 컴포넌트를 호출하는 상황을 가정한다

```java
// Start with an event publication
scenario.publish(new MyApplicationEvent(…)).…

// Start with a bean invocation
scenario.stimulate(() -> someBean.someMethod(…)).…
```

[공식문서 참고](https://docs.spring.io/spring-modulith/reference/testing.html#scenarios)


## 문서화

[공식 문서 참고](https://docs.spring.io/spring-modulith/reference/documentation.html)


## 옵저버빌리티

[공식 문서 참고](https://docs.spring.io/spring-modulith/reference/production-ready.html)


## 스프링 모듈리스 한계

MSA처럼 프로세스 단위로 독립 배포할 수 없다 -> 모듈라 모놀리스의 근본적인 단점

스프링 모듈리스의 구조 검사는 런타임 시점에 수행되기 때문에 **컴파일 시점에 모듈 경계를 강제할 수 없다**

도메인 이벤트를 저장하고 재시도하는 Outbox 기술을 제공하지만 애플리케이션 내부 이벤트의 신뢰성과 비동기화를 위한 도구일 뿐이다

외부 시스템 간 메시징 시스템으로 확장할 수는 있으나 그렇게 되면 네트워크에 접근해야 하므로 모듈라 모놀리스의 장점과 트레이드 오프를 해야한다

스프링 모듈리스가 중점적으로 지원하는 것은 DDD 친화적인 디렉토리 구조 권장, 도메인 이벤트를 통한 느슨한 결합, 문서화 자동화이다

장애 복구 전략(failover), 대규모 트래픽 스케일링, 고가용성 보장 등의 메커니즘을 제공하지 않는다


## 스프링 모듈리스 vs 그레이들 멀티 모듈

### 목적

**스프링 모듈리스**
- 애플리케이션 내부 도메인 모듈 **아키텍처** 검증
- 논리적 모듈 구조화, 결합도 관리
- 도메인 경계 구분, 이벤트 기반 느슨한 결합도, 문서화

**그레이들 멀티 모듈**
- **빌드 시스템**에서 프로젝트 논리적 분리
- 빌드 속도, 재사용성, 독립 빌드 지원
- 코드 분리, 컴파일/테스트 분리

### 적용 대상

**스프링 모듈리스**
- 스프링 부트 기반 애플리케이션 기반
- 패키지 기반 논리적 모듈 (`src/main` 하위 디렉토리)

**그레이들 멀티 모듈**
- 프로젝트 전체, 언어/프레임워크 무관
- 디렉토리 기반 물리적 모듈

### 모듈 경계 및 의존성 관리

**스프링 모듈리스**
- 패키지 스캐닝 + 구조 검증
- 런타임에 규칙 위반 감지 가능
- 클래스패스 동일

**그레이들 멀티 모듈**
- 빌드 파일 기반 의존성 제어
- 의존성 위반 시 컴파일 불가
- 모듈 별 클래스패스 분리 (스프링 부트 사용 시 통합됨)

### 빌드 및 배포

**스프링 모듈리스**
- fat jar 단일 아티팩트

**그레이들 멀티 모듈**
- 모듈별 빌드/배포 가능
- 여러 아티팩트
- 스프링 부트 사용 시 fat jar로 통합 가능