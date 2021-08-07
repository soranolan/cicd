package com.example.cicd.demo.service;

import com.example.cicd.core.model.User;

import reactor.core.publisher.Mono;

public interface IUserService {
	
	public Mono<User> findOneByUsername(String username);
	
	public Mono<User> add(User entity);
	
}
