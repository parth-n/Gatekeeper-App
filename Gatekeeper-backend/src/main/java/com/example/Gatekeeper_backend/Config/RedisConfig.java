package com.example.Gatekeeper_backend.Config;

import com.example.Gatekeeper_backend.DTO.VisitorDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    RedisTemplate<String, VisitorDTO> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,VisitorDTO> template = new RedisTemplate<>() ;
        template.setConnectionFactory(redisConnectionFactory);
        template.setValueSerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<VisitorDTO>(VisitorDTO.class));
        return template ;
    }
}
