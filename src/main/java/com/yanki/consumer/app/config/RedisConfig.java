package com.yanki.consumer.app.config;

import com.yanki.consumer.app.models.dto.Maestros;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public ReactiveRedisTemplate<String, Maestros> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Maestros> serializer = new Jackson2JsonRedisSerializer<>(Maestros.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, Maestros> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
        RedisSerializationContext<String, Maestros> context = builder.value(serializer)
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
