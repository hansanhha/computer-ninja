package hansanhha.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Order Service API")
                .description("주문 생성/조회/취소 등 주문 관련 기능을 제공하는 API")
                .version("v1.0")
                .contact(new Contact()
                        .name("API Support")
                        .email("support@example.com"))
                .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }

}
