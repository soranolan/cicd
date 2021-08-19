package com.example.cicd.demo.service;

import java.util.Map;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IBaseService;

import reactor.core.publisher.Mono;

public interface IUserService extends IBaseService<User> {
	
	public Mono<User> findOneByUsername(String username);
	
	public Mono<User> findOneByUsername(String username, String isActivated);
	
	public Mono<User> validate(Map<String, Object> entry);
	
	public Mono<User> activate(User entity);
	
	public Mono<User> reactivate(Map<String, Object> entry);
	
}
