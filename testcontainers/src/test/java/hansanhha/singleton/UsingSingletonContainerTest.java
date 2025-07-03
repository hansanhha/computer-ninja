package hansanhha.singleton;


import hansanhha.Customer;
import hansanhha.CustomerRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


public class UsingSingletonContainerTest extends AbstractSingletonContainerTest {

    @LocalServerPort
    private Integer port;

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
