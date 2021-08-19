package com.example.cicd.core.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IRedisService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class RedisServiceImpl implements IRedisService {
	
	private ReactiveRedisTemplate<String, User> template;
	
	public RedisServiceImpl(ReactiveRedisTemplate<String, User> template) {
		this.template = template;
	}
	
	@Override
	public Mono<User> set(User user) {
		JSONObject logParams = new JSONObject();
		logParams.put("user", user);
		log.info(DEFAULT.value(), () -> logParams);
		return template.opsForValue().set(user.getUsername(), user, Duration.ofMinutes(15L)).flatMap(bool -> Mono.just(user));
	}
	
	@Override
	public Mono<User> get(String key) {
		JSONObject logParams = new JSONObject();
		logParams.put("key", key);
		log.info(DEFAULT.value(), () -> logParams);
		return template.opsForValue().get(key);
	}
	
	@Override
	public Mono<User> activate(User entity) {
		JSONObject logParams = new JSONObject();
		logParams.put("entity", entity);
		log.info(DEFAULT.value(), () -> logParams);
		
		if (StringUtils.isBlank(entity.getSystemMessage())) {
			return set(entity);
		}
		return Mono.just(entity);
	}
	
}
