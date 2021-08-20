package com.example.cicd.core.conf;

import static org.springframework.data.redis.serializer.RedisSerializationContext.newSerializationContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.RedisSerializationContextBuilder;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.cicd.core.model.User;

@Configuration
public class RedisConfig {
	
	@Bean
	public ReactiveRedisTemplate<String, User> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
		Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
		RedisSerializationContextBuilder<String, User> builder = newSerializationContext(new StringRedisSerializer());
		RedisSerializationContext<String, User> context = builder.value(serializer).build();
		return new ReactiveRedisTemplate<>(factory, context);
	}
	
}
