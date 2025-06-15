package com.saswat.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory){
        RedisTemplate redisTemplates=new RedisTemplate<>();
        redisTemplates.setConnectionFactory(factory);
        redisTemplates.setKeySerializer(new StringRedisSerializer());
        redisTemplates.setValueSerializer(new StringRedisSerializer());

        return redisTemplates;
    }
}
