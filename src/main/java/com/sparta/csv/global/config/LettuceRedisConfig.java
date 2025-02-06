package com.sparta.csv.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class LettuceRedisConfig {

    /**
     * Redis와의 연결을 위한 ConnectionFactory를 생성하는 Bean
     * Spring Boot 기본 Redis 클라이언트인 Lettuce를 사용
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    /**
     * Redis 데이터 처리를 위한 RedisTemplate Bean 설정
     * - Key Serializer: StringRedisSerializer (키를 문자열로 저장)
     * - Value Serializer: GenericJackson2JsonRedisSerializer (값을 JSON 형식으로 저장)
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory); // 위에서 생성한 ConnectionFactory 사용
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
