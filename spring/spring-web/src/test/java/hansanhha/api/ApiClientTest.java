package hansanhha.api;

import static org.assertj.core.api.Assertions.assertThat;

import hansanhha.api.HelloController.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiClientTest {
    
    @LocalServerPort
    private int port;

    private String api() {
        return "http://localhost:" + port + "/api/hello";
    }

    @Test
    void restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        var response = restTemplate.getForObject(api(), ApiResponse.class);

        assertThat(response.message()).isEqualTo("hello api");
    }

    @Test
    void webClientSync() {
        WebClient client = WebClient.create(api());

        var response = client.get()
            .retrieve()
            .bodyToMono(ApiResponse.class)
            .block();
        
        assertThat(response.message()).isEqualTo("hello api");
    }

    @Test
    void webClientAsync() {
        WebClient client = WebClient.create(api());

        Mono<ApiResponse> mono = client.get()
            .retrieve()
            .bodyToMono(ApiResponse.class);

        mono.subscribe(res -> System.out.println("WebClient (aysnc): " + res.message()));

        var response = mono.block(); // 테스트 진행을 위한 블로킹
        assertThat(response.message()).isEqualTo("hello api");
    }

    @Test
    void restClient() {
        RestClient client = RestClient.create(api());

        var response = client.get()
            .retrieve()
            .body(ApiResponse.class);

        assertThat(response.message()).isEqualTo("hello api");
    }
}
