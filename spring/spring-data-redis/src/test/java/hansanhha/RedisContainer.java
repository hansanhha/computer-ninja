package hansanhha;

import static com.redis.testcontainers.RedisContainer.DEFAULT_IMAGE_NAME;
import static com.redis.testcontainers.RedisContainer.DEFAULT_TAG;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class RedisContainer {

    private static final int PORT = 6379;
    private static final GenericContainer<?> REDIS_CONTAINER;

    static {
        REDIS_CONTAINER = new GenericContainer<>(DEFAULT_IMAGE_NAME + ":" + DEFAULT_TAG)
                .withExposedPorts(PORT)
                .withReuse(true);

        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    private static void registerRedisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(PORT)
                .toString());
    }

}
