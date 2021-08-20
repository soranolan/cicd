package com.example.cicd.demo.helper.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static com.example.cicd.core.util.Argon2Utils.matches;
import static com.example.cicd.core.util.PasetoUtils.compact;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.cicd.core.model.User;
import com.example.cicd.demo.helper.ISigninHandlerHelper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SigninHandlerHelperImpl implements ISigninHandlerHelper {
	
	@Override
	public Map<String, Object> validateCombinator(String username, User signin, User exist) {
		boolean isMatch = matches(signin.getPassword(), exist.getPassword());
		Map<String, Object> result = new HashMap<>();
		result.put("isMatch", isMatch);
		
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", exist.getRoles());
		
		if (isMatch) { result.put("token", compact(username, claims)); }
		log.info(DEFAULT.value(), () -> result);
		return result;
	}
	
}
