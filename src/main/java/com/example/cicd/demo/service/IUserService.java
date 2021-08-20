package com.example.cicd.demo.service;

import java.util.Map;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IBaseService;

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
