package com.example.cicd.core.service;

import com.example.cicd.core.model.User;

import reactor.core.publisher.Mono;

public interface IRedisService {
	
	/**
	 * insert
	 * 
	 * @param user insert user object
	 * @return user object
	 */
	public Mono<User> set(User user);
	
	/**
	 * query 
	 * 
	 * @param key user name, user id, account id
	 * @return user object
	 */
	public Mono<User> get(String key);
	
	/**
	 * activate account
	 * 
	 * @param user modify user object
	 * @return user object
	 */
	public Mono<User> activate(User user);
	
}
