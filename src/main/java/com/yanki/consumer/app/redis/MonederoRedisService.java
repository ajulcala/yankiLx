package com.yanki.consumer.app.redis;

import com.yanki.consumer.app.models.dto.Maestros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MonederoRedisService {
    public static final String KEY = "MAESTRAS";
    @Autowired
    private ReactiveRedisTemplate<String, Maestros> redisTemplate;
    public Mono<Long> put(Maestros maestro) {
        return redisTemplate.opsForList().rightPush(KEY, maestro);
    }
    public Flux<Maestros> getAll() {
        return redisTemplate.opsForList().range(KEY,0,-1);
    }
}
