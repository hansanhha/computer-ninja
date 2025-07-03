![docs](assets/kick-the-java-logo.png)

자바의 기초 문법부터 최신 릴리즈 기능까지 폭넓게 학습하고 실험하기 위한 공간이다

평소에 사용하지 않는 패키지나, 자주 사용되는 패턴, 최신 프로젝트들(Project Loom, Project Panama 등)의 실험적인 기능까지 자바를 탐험하며 학습하고 직접 코드를 통해 검증하는 것을 목표로 한다


## 다루는 내용 및 모듈 구성

[문법](./lang): 타입(클래스, Enum, Sealed Class 등), var, 제네릭, 패턴 매칭, 텍스트 블록, instanceof/스위치 개선 등

[컬렉션 프레임워크](./collections): List/Set/Map/Queue/Deque, Immutable Collections, Concurrent Collections, SequencedCollection

[함수형 프로그래밍](./functional): 람다식, 스트림(Gatherer), Optional, 함수형 인터페이스

[멀티스레딩 & 동시성](./thread): Thread, ExecutorService, volatile, ReentrantLock, CompletableFuture, ForkJoinPool, Virtual Threads, Structured Concurrency

[JVM](./jvm): 클래스 로딩/클래스패스, 바이트코드 분석, GC 전략, JVM 옵션, JIT

[네이티브](./native): AOT, GraalVM, 네이티브 이미지

[입출력](./io): InputStream/OutputStream, Reader/Writer, nio, Memory-mapped I/O, Buffered vs Unbuffered, Stream + I/O

[네트워크](./network): Socket, ServerSocket, HttpURLConnection, HttpClient, Jackson, WebSocket

[네이티브 코드](./foreign): JNI, Project Panama 기반 FFI, MemorySegment, MemoryLayout, Linker, SymbolLookup

[유틸리티](./utils): 날짜/시간, 문자열 처리, 로그 처리 등

[보안](./security): MessageDigest, Signature, KeyPairGenerator, Cipher, AES/PSA

[도구](./tools): JMeter, Checkstyle, SonarQube, JVisualVM, JMC, JFR, JShell 

[프로젝트](./experimental): Project Amber / Loom / Panama / Valhalla / Leyden / Galahad / ZGC / Shenandoah


## 읽기 좋은 문서/사이트

[inside.java](https://inside.java/tags): 자바의 최신 기능과 프로젝트에 대한 공식 블로그로써 자바의 발전 방향과 새로운 기능에 대한 깊이 있는 글을 제공한다

[dev.java](https://dev.java): 자바 개발자들을 위한 공식 사이트로 자바의 최신 소식, 튜토리얼, 샘플 코드 등을 제공한다

