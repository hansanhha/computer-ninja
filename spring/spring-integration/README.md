#### 인덱스
- [스프링 통합](#스프링-통합)
- [컴포넌트](#컴포넌트)
- [어댑터 종류](#어댑터-종류)
- [IntegrationFlow](#integrationflow)
- [Spring Integration vs Spring Batch vs Spring Cloud Stream](#spring-integration-vs-spring-batch-vs-spring-cloud-stream)



## 스프링 통합

스프링 통합(Spring Integration)은 Enterprise Integration Patterns(EIP)를 기반으로 구현된 프레임워크로 여러 시스템과 컴포넌트 간의 데이터 교환, 이벤트 처리, 비동기 메시징을 스프링 기반으로 구성할 수 있도록 돕는다

-> 스프링에서 **메시지**를 중심으로 애플리케이션들을 연결하고 자동화할 수 있게 해주는 엔진

-> **`메시지 -> 채널 -> 엔드포인트` 동작 구조**

일반적으로 서로 다른 시스템 간 연동을 수행해야 할 때는 아래와 같은 코드를 사용한다

```java
RestTemplate rest = new RestTemplate();
String response = rest.postForObject("https://payment-system/api/pay", request, String.class);
```

한 서비스에서 다른 서비스를 직접 호출하게 되면 강한 결합이 생긴다

또한 오류 처리, 재시도, 스로틀링과 같은 제어를 위해 코드의 복잡도가 올라갈 수 밖에 없다

일반적으로 동기 호출로 이뤄지기 때문에 시스템 부하를 증가시키기도 한다

**스프링 통합은 메시지 기반 비동기 통합으로 시스템 간 연동 문제를 해결한다**

시스템 간 직접 호출을 피하고 채널을 통해 메시지를 주고 받는 방식으로 동작한다

흐름 제어, 필터링, 변환, 라우팅 등 통합 로직을 선언적으로 설정할 수 있으며 메시징 시스템(카프카, 래빗MQ) 과도 쉽게 연결할 수 있다

### 사용 시나리오

#### 1. 결제 시스템 통합

외부 PG사 API -> Integration Gateway -> 내부 결제 서비스

(실패 시 재시도, 로깅, 라우팅 제어)

#### 2. IoT 데이터 수집

여러 센서에서 오는 데이터를 Integration Flow로 수집 -> 카프카로 전달 -> 배치 처리

#### 3. 이메일/파일 처리 자동화

매일 새벽 SFTP 폴더 감시 -> 파일 읽기 -> 데이터 변환 -> DB 저장

#### 4. 비즈니스 이벤트 처리

주문 생성 -> 메시지로 발행 -> 알림 서비스, 배송 서비스 등으로 자동 분기


## 컴포넌트

### `Message`

메시지는 스프링 통합의 `메시지 -> 채널 -> 엔드포인트` 데이터 흐름 과정에 포함되는 가장 기본적인 단위이다

단순한 데이터 객체가 아니라 통신 단위이자 메타정보 단위로써 데이터(payload)와 부가 정보(header)를 캡슐화한다

스프링 통합은 이 메시지가 시스템을 따라 흘러가는 구조를 관리한다

```java
package org.springframework.messaging;

public interface Message<T> {

    // payload: 주문 정보, JSON 파일 등 실제 비즈니스 데이터
	T getPayload();

    // header: 메시지 ID, 타임스탬프, 라우팅 키 등 부가적인 메타 정보
	MessageHeaders getHeaders();
}
```

`MessageBuilder`를 통해 특정 메시지를 생성할 수 있다

```java
Message<String> message = MessageBuilder
        .withPayload("OrderCreatedEvent")
        .setHeader("orderId", 1L)
        .build();

message.getPayload();
message.getHeaders();
```

### `MessageChannel`

메시지 채널은 메시지를 전달하는 파이프라인/통로로 생산자(producer)가 메시지를 넣고 소비자(consumer)가 꺼내서 처리한다

체널을 통해 시스템/컴포넌트 간 결합도를 낮출 수 있다

메시지 큐와 같은 외부 시스템은 채널 어댑터로 연결한다

```java
package org.springframework.messaging;

@FunctionalInterface
public interface MessageChannel {
    boolean send(Message<?> message, long timeout);
}
```

`MessageChannel` 구현체
- `DirectChannel`: 생산자가 소비자를 직접 호출하는 방식 (동기 처리)
- `QueueChannel`: 큐에 메시지를 넣고 소비자가 하나씩 꺼내는 방식 (비동기 처리)
- `PublishSubscribeChannel`: 하나의 메시지를 여러 소비자에게 동시에 전달하는 방식 (다중 구독)
- `PrioirtyChannel`: 메시지를 우선순위에 따라 처리 (메시지 정렬)
- `ExecutorChannel`: 스레드 풀을 통해 비동기 실행 (병렬성)

### `MessageEndpoint`

메시지 엔드포인트는 메시지 채널과 상호작용하여 메시지를 처리한 뒤, 다른 메시지 채널로 새로운 메시지를 전달할 수 있다

다양한 방법으로 비즈니스 로직을 수행할 수 있는 핸들러를 제공한다

#### `@ServiceActivator`

메시지를 수신하고 비즈니스 메서드를 실행하는 핸들러

스프링 통합 프레임워크의 데이터 처리 플로우에 비즈니스 로직을 접목시키는 역할을 한다

`채널 -> Service Activator -> 서비스 메서드`

```java
// 메시지가 orderInputChannel에 도착하면 자동으로 이 메서드가 실행된다
// 결과값이 있으면 다른 채널로 전파할 수 있다
@ServiceActivator(inputChannel = "orderInputChannel")
public void processOrder(Order order) {
    orderService.save(order);
}
```

#### `@Transformer`

메시지를 다른 형태로 변환하는 핸들러 (JSON -> 겍체, DTO -> 엔티티 변환 등)

페이로드만 변환하며 헤더는 기본적으로 유지된다

```java
@Transformer(inputChannel = "jsonChannel", outputChannel = "objectChannel")
public Order toOrder(String json) {
    return objectMapper.readValue(json, Order.class);
}
```

#### `@Filter`

조건에 따라 메시지를 필터링하는 핸들러

`false`를 반환하면 해당 메시지는 폐기되며, 필요 시 discardChannel로 별도로 보낼 수 있다

```java
@Filter(inputChannel = "orderChannel", outputChannel = "validateOrderChannel")
public boolean validate(Order order) {
    return order.getAmount() > 0;
}
```

#### `@Router`

메시지를 특정 기준으로 다른 채널로 라우팅하는 핸들러

메시지 흐름을 동적으로 분기할 수 있다

```java
@Router(inputChannel = "orderChannel")
public String route(Order order) {
    return order.isVid() ? "vipChannel" : "normalChannel";
}
```

#### `@Splitter`, `@Aggregator`

`@Splitter`는 메시지를 여러 개로 분할하고 `@Aggregator`는 여러 메시지를 하나로 결합한다

```java
@Splitter(inputChannel = "batchChannel", outputChannel = "singleItemChannel")
public List<Item> split(Batch batch) {
    return batch.getItems();
}
```

```java
@Aggregator(inputChannel = "resultChannel", outputChannel = "summaryChannel")
public Summary aggregate(List<Result> results) {
    return new Summary(results);
}
```

#### `@InboundChannelAdapter`, `@OutboundChannelAdapter`

`@InboundChannelAdapter`: 외부에서 데이터를 가져와 채널로 보낸다

`@OutboundChannelAdapter`: 채널에서 받은 데이터를 외부로 전송한다

```java
// 주기적으로 폴더를 감시하고 새 파일을 메시지로 발행한다
@Bean
@InboundChannelAdapter(channel = "fileChannel", poller = @Poller(fixedRate = "5000"))
public MessageSource<File> fileReader() {
    return new FileReadingMessageSource(new File("input"));
}
```

### `@MessagingGateway`, `@Gateway`

게이트웨이는 외부에서 스프링 통합 플로우를 메서드 호출 형태로 시작할 수 있도록 해주는 진입점 역할을 수행하는 컴포넌트이다

비즈니스 계층과 메시징 계층의 인터페이스 역할을 하여 트랜잭션 경계를 설정하기에 유용하고 테스트하기 쉽다

```java
@MessagingGateway
public interface OrderGateway {
    @Gateway(requestChannel = "orderInputChannel")
    void sendOrder(Order order);
}
```

### `@Poller`

폴러는 비동기 메시지 소비를 제어하는 스케줄러이다

주로 `QueueChannel`이나 `InboundChannelAdpater`에서 사용되어 pull 방식의 메시지 소비를 제어한다

```java
@InboundChannelAdapter(
    channel = "dataChannel",

    // 5초마다 최대 10개의 메시지를 가져온다
    poller = @Poller(fixedDelay = "5000", maxMessagePerPoll = "10")
)
```


## 어댑터 종류

스프링 통합에서 **내부 파이프라인은 모두 채널과 엔드포인트**로 처리하지만 **외부와 데이터를 주고받는 건 모두 어댑터**가 담당한다

`스프링 통합 메시징 파이프라인 <-> 외부 시스템(HTTP, 파일, MQ, DB 등)`

어댑터는 크게 인바운드(외부 시스템 -> Integration 내부)와 아웃바운드(Integration 내부 -> 외부 시스템)으로 나뉜다

스프링 부트 스타터를 통해 스프링 통합을 추가하게 되면 메시징 인프라만 포함되어 있는 스프링 통합 코어 모듈만 불러온다

**스프링 통합은 모듈화 구조로 이어져 있어서 특정 외부 시스템과 연동하려면 해당 어댑터 모듈을 별도로 의존성 추가해야 한다**

어댑터 종류: 파일, FTP, JMS, 메일, HTTP, TCP/UDP, 웹 소켓, AMQP, MQTT, 카프카, DB, Redis 등

### 파일 어댑터

`org.springframework.integration:spring-integration-file` 모듈

인바운드: `FileReadingMessageSource` - 지정된 디렉토리에서 파일을 읽어 `Message<File>`로 발행한다

아웃바운드: `FileWritingMessageHandler` - 수신된 메시지를 파일로 저장한다 (append, overwrite 설정 가능)

### 메일 어댑터

`org.springframework.integration:spring-integration-mail` 모듈

인바운드: `MailReceivingMessageSource` - IMAP/POP3 서버에서 이메일 수신

아웃바운드: `MailSendingMessageHandler` - SMTP를 통해 이메일 전송

유틸리티
- IntegrationMailUtils: 메시지 변환/본문 파싱 유틸리티
- ImapIdleChannelAdapter: IMAP IDLE 모드로 실시간 푸시 지원

### HTTP 어댑터

`org.springframework.integration:spring-integration-http` 모듈

인바운드: `HttpRequestHandlingMessagingGateway` - HTTP 요청을 메시지로 변환 (RestController 대체)

아웃바운드: `HttpRequestExecutingMessageHandler` - HTTP 요청을 외부로 전송 (RestController 대체)

게이트웨이: `HttpRequestHandlingController` - 요청-응답 형태의 메시지 게이트웨이

### AMQP 어댑터

스프링 부트 `spring-boot-starter-amqp`와 함께 자동 설정 가능

`org.springframework.integration:spring-integration-amqp` 모듈

인바운드: `AmqpInboundChannelAdapter` - RabbitMQ 큐에서 메시지 수신

아웃바운드: `AmqpOutboundEndpoint` - 메시지를 Exchange/Queue로 발행

게이트웨이: `AmqpGateway` - 요청 - 응답 패턴 지원

유틸리티: `RabbitTemplate` - Spring AMQP Template 사용


## IntegrationFlow

IntegrationFlow는 스프링 통합의 자바 DSL을 통해 메시지 파이프라인(데이터 흐름)을 선언적으로 구성하는 빌더를 말한다

채널 · 엔드포인트 · 어댑터 · 트랜스포머 · 라우터 · 핸들러 · 폴러 · 스레드 실행자 등을 하나의 연결된 흐름으로 조합한 객체이다

메시지 파이프라인 정의, 입력/출력 채널 지정, 엔드포인트 연결 및 변환 적용을 정의한다

일반적으로 메시지 파이프라인(IntegrationFlow)마다 빌더 API를 통해 흐름을 단계적으로 구성한 뒤 스프링 빈으로 등록한다

**Message**: payload + headers - 흐름의 이동 단위

**Channel**: 흐름의 각 단계 사이의 전송 방식 (동기/비동기/큐/Pub-Sub 등)

**Endpoint**: 작업 수행 및 다른 채널 전송 (변환, 필터, 라우터 등)

```java
@Configuration
public class BasicIntegrationFlowConfig {
    
    @Bean
    MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    MessageChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    IntegrationFlow basicFlow() {
        return IntegrationFlow
            .from(this.inputChannel()) // 시작점 정의, 두 번째 인자로 Poller(주기) 설정 가능
            .transform(String.class, String::toUpperCase) // payload 변환 또는 header 조작
            .channel(this.outputChannel()) // 명시적 채널 지정
            .get();
    }
}
```


## Spring Integration vs Spring Batch vs Spring Cloud Stream

스프링 통합: 애플리케이션 내부와 외부를 메시지 기반으로 느슨하게 연결한다 (메시지 중심의 시스템 통합 프레임워크)

스프링 배치: 대규모 데이터를 신뢰성, 실패 복구 가능하게, 확장성있게, 단계별로 처리하자 (안정적인 대규모 데이터 처리)

스프링 클라우드 스트림: 메시지 브로커를 추상화해서 코드 수정 없이 마이크로서비스 간 이벤트를 주고받자 (이벤트 중심의 마이크로서비스 통신 프레임워크)

|비교|Spring Integration|Spring Batch|Spring Cloud Stream|
|---|----|----|---|
|핵심 목적|실시간 메시지 기반 시스템 간 통합|대용량 데이터 일괄 처리|이벤트 기반 마이크로서비스 통신|
|처리 유닛|메시지 단위|청크 단위|메시지 단위(스트림 중심)|
|트리거 시점|실시간/폴링|스케줄러 또는 수동 실행|메시지 도착 시(pub/sub)|
|컴포넌트 간 통합 방식|어댑터를 통한 외부 연동|Reader, Processor, Writer 구조|Binder를 통한 메시지 브로커(카프카) 연동|
|구조적 단위|IntegrationFlow|Job, Step, Tasklet|Input/Output Binder|

위 세 가지는 상호배타적이지 않고 서로 결합하여 사용할 수도 있다
- 스프링 통합 + 배치: Integration 파일 수신(FTP 어댑터) -> 배치 Job 실행(대용량 처리)
- 스프링 통합 + 클라우드 스트림: IntegrationFlow에서 메시지를 카프카로 전송 -> 다른 서비스가 클라우드 스트림으로 소비
- 스프링 배치 + 클라우드 스트림: 배치 Job 결과를 카프카 토픽으로 발행하여 실시간 분석 서비스로 전달

