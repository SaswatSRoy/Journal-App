package com.saswat.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T get(String key, Class<T> clazz) throws JsonProcessingException {
        String json = redisTemplate.opsForValue().get(key);
        if (json == null) return null;
        return objectMapper.readValue(json, clazz);
    }

    public void set(String key, Object value, Long expiry) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(value);
        redisTemplate.opsForValue().set(key, json, expiry, TimeUnit.SECONDS);
    }
}
