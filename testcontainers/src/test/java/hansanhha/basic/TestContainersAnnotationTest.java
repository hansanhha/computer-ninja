package hansanhha.basic;


import hansanhha.Customer;
import hansanhha.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

/*
    testcontainers에서 제공하는 JUnit5 Extension
    개발자가 @BeforeAll, @AfetrAll 콜백 메서드를 사용해서 명시적으로 컨테이너를 시작/중지하는 것을 대신해준다

     @Container 어노테이션과 사용할 컨테이너 클래스를 테스트 클래스에 명시해야 한다
     static 필드인 경우 테스트 클래스에서 한 번만 시작/종료되며
     non-static 필드인 경우 각 테스트 메서드마다 컨테이너가 시작/종료된다 (리소스 낭비)
 */
@Testcontainers
public class TestContainersAnnotationTest {

    @LocalServerPort
    private Integer port;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    CustomerRepository customerRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

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
