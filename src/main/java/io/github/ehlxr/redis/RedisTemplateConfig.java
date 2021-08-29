package io.github.ehlxr.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author ehlxr
 */
@Configuration
public class RedisTemplateConfig {
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, String> getRedisTemplate(RedisConnectionFactory factory) {
        return buildRedisTemplateByString(factory);
    }

    /**
     * 构建 redisTemplate 使用 string序列化
     */
    public RedisTemplate<String, String> buildRedisTemplateByString(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
