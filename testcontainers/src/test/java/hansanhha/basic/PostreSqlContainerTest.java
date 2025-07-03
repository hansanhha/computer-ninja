package hansanhha.basic;


import hansanhha.Customer;
import hansanhha.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostreSqlContainerTest {

    /*
     * testcontainers 사용 패턴
     *
     * 1. 클래스 멤버로 사용할 컨테이너 생성 및 커넥션 프로퍼티 설정
     * 2. @BeforeAll 메서드에서 컨테이너 시작
     * 3. 각 테스트 메서드 실행 (필요 시@BeforeEach 초기화)
     * 4. @AfterAll 메서드에서 컨테이너 종료
     *
     */

    @LocalServerPort
    private Integer port;

    /*
        PostgreSQL 데이터베이스 컨테이너 생성 -> 랜덤 포트(호스트)와 컨테이너의 5432 포트를 매핑한다

        static 필드인 경우 클래스에서 한 번만 시작/종료되며 (@BeforeAll, @AfterAll)
        non-static 필드인 경우 각 테스트 인스턴스마다 컨테이너가 시작/종료된다 (@BeforeEach, @AfterEach) -> 리소스 낭비
     */
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    // 테스트 인스턴스의 테스트 메서드 실행 전에 PostgreSQL 컨테이너를 시작한다
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    // 명시적으로 종료하지 않아도 testcontainers는 Ryuk 컨테이너를 사용하여 컨테이너를 중지하고 삭제한다
    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    // Postgres 컨테이너로부터 동적으로 데이터베이스 커넥션 프로퍼티를 등록한다
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        customerRepository.deleteAll();
    }

    @Test
    void 모든_Customer엔티티를_조회한다() {
        List<Customer> customers = List.of(
                new Customer(null, "홍길동", "hong@mail.com"),
                new Customer(null, "김길동", "kim@mail.com"));

        customerRepository.saveAll(customers);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/customers")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(".", Matchers.hasSize(2));
    }

}
