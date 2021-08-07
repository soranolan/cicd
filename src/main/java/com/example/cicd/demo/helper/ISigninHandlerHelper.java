package com.example.cicd.demo.helper;

import java.util.Map;

import com.example.cicd.core.model.User;

public interface ISigninHandlerHelper {
	
	public Map<String, Object> validateCombinator(String username, User signin, User exist);
	
}
