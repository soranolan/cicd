package com.example.cicd.core.service;

import java.util.Map;

import com.example.cicd.core.model.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService extends IBaseService<User> {
	
	/**
	 * find one by user name
	 * 
	 * @param username user name, user id, account id
	 * @return User
	 */
	public Mono<User> findOneByUsername(String username);
	
	/**
	 * find one by user name
	 * 
	 * @param username user name, user id, account id
	 * @param isActivated "true" or "false"
	 * @return User
	 */
	public Mono<User> findOneByUsername(String username, String isActivated);
	
	/**
	 * find all by isActivated
	 * 
	 * @param isActivated true or false
	 * @return User
	 */
	public Flux<User> findAll(String isActivated);
	
	/**
	 * validate token then find the sign in db user
	 * 
	 * @param entry token
	 * @return User
	 */
	public Mono<User> validate(Map<String, Object> entry);
	
	/**
	 * activate account
	 * 
	 * @param user sign up user
	 * @return User
	 */
	public Mono<User> activate(User user);
	
	/**
	 * resend activate link
	 * 
	 * @param entry user name and email
	 * @return User
	 */
	public Mono<User> reactivate(Map<String, Object> entry);
	
}
