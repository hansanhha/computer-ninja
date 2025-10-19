package hansanhha;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;

@Configuration
public class RedisConfig {

    private final RedisProperties redisProperties;    

    public RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
                new RedisStandaloneConfiguration(
                        redisProperties.getHost(),
                        redisProperties.getPort()
                )
        );
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
     
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        template.setEnableTransactionSupport(true);

        return template;
    }

    @Bean
    RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(CacheProperties cacheProperties) {
        return builder -> {
            RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
            redisCacheConfig.serializeKeysWith(SerializationPair.fromSerializer(new StringRedisSerializer()));
            redisCacheConfig.serializeValuesWith(SerializationPair.fromSerializer(new StringRedisSerializer()));

            CacheProperties.Redis redis = cacheProperties.getRedis();
            redisCacheConfig.entryTtl(redis.getTimeToLive());

            builder.withCacheConfiguration("example", redisCacheConfig);
        };
    }
    
}
