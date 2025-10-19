package hansanhha;

import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class SerializationTest {

    @Test
    void stringToByteArray() {
        String str = "hello";
        byte[] serialized = new StringRedisSerializer().serialize(str);
        System.out.println(serialized);
    }
    
}
