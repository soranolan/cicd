package com.example.cicd.core.service;

import com.example.cicd.core.model.User;

import reactor.core.publisher.Mono;

public interface IRedisService {
	
	public Mono<User> set(User user);
	
	public Mono<User> get(String key);
	
	public Mono<User> activate(User entity);
	
}
