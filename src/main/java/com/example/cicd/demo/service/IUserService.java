package com.example.cicd.demo.service;

import com.example.cicd.core.model.User;

import reactor.core.publisher.Mono;

public interface IUserService {
	
	public Mono<User> findOneByAccount(String account);
	
	public Mono<User> add(User entity);
	
}
