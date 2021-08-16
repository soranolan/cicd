package com.example.cicd.demo.service;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IBaseService;

import reactor.core.publisher.Mono;

public interface IUserService extends IBaseService<User> {
	
	public Mono<User> findOneByUsername(String username);
	
}
