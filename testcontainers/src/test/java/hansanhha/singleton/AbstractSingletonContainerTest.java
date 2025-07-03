package hansanhha.singleton;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;


/*
    컨테이너를 싱글톤으로 관리하는 테스트 클래스
    static 필드로 관리하기 때문에 여러 개의 서브 클래스에서 사용하더라도 단 하나의 컨테이너만 생성 및 시작된다

    다만 @TestContainer, @Container 어노테이션을 사용하면 서브 클래스가 끝날 때마다 컨테이너가 종료되는데
    다른 서브 클래스가 이전에 설정된 스프링 컨텍스트를 재사용하기 때문에 종료된 컨테이너를 연결하려고 시도하다 테스트 실패로 이어진다

    따라서 싱글톤으로 컨테이너를 관리하는 경우 클래스 초기화나 @BeforeAll 메서드를 사용해야 한다
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractSingletonContainerTest {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"));
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:latest"));

    static {
        postgres.start();
        kafka.start();
    }

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);

    }
}
