package com.example.cicd.demo.helper;

import java.util.Map;

import com.example.cicd.core.model.User;

public interface ISigninHandlerHelper {
	
	/**
	 * validate sign in password
	 * 
	 * @param username sign in user name
	 * @param signin sign in user object
	 * @param exist db user object
	 * @return map include match or not, token
	 */
	public Map<String, Object> validateCombinator(String username, User signin, User exist);
	
}
