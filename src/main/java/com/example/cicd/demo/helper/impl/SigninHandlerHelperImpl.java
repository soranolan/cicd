package com.example.cicd.demo.helper.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.cicd.core.model.User;
import com.example.cicd.core.util.Argon2Utils;
import com.example.cicd.core.util.PasetoUtils;
import com.example.cicd.demo.helper.ISigninHandlerHelper;

@Component
public class SigninHandlerHelperImpl implements ISigninHandlerHelper {
	
	@Override
	public Map<String, Object> validateCombinator(String username, User signin, User exist) {
		boolean isMatch = Argon2Utils.matches(signin.getPassword(), exist.getPassword());
		Map<String, Object> result = new HashMap<>();
		result.put("isMatch", isMatch);
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", exist.getRoles());
		
		if (isMatch) { result.put("token", PasetoUtils.compact(username, claims)); }
		return result;
	}
	
}
