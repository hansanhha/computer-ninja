package hansanhha.api;


import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;


public class RestClientExample {

    void simpleCreate() {
        var client = RestClient.create("http://example-baseUrl.com");
    }

    void builderCreate() {
        var client = RestClient.builder()
                .baseUrl("http://example-baseUrl.com")
                .defaultHeaders(headers -> {
                    headers.add("haeder", "value");
                })
                .build();
    }

    void exchange() {
        var client = RestClient.create("http://example-baseUrl.com");

        String userId = client
                .post()
                .uri("/users")
                .body(Map.of("id", "1234"))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange((req, res) -> {
                    if (res.getStatusCode().is4xxClientError()) {
                        // error handling
                    } else if (res.getStatusCode().is5xxServerError()) {
                        // error handling
                    } else if (res.getStatusCode() == HttpStatus.NO_CONTENT) {
                        return null;
                    }

                    return res.bodyTo(String.class);
                });
    }

    void retrieve() {
        var client = RestClient.create("http://example-baseUrl.com");

        String userId = client.get()
                .uri("/users")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    // error handling
                })
                .body(String.class);
    }

}
