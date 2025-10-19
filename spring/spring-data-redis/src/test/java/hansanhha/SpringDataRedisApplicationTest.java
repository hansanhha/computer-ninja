package hansanhha;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class SpringDataRedisApplicationTest extends RedisContainer {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testStrings() {
        var key = "msg";
        var greeting = "Hello World";
        redisTemplate.opsForValue().set(key, greeting);
        assertThat((String) redisTemplate.opsForValue().get(key)).isEqualTo(greeting);
    }

}
