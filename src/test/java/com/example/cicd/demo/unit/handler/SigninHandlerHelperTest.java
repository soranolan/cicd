package com.example.cicd.demo.unit.handler;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.model.User;
import com.example.cicd.core.util.Argon2Utils;
import com.example.cicd.demo.helper.impl.SigninHandlerHelperImpl;

@ExtendWith(MockitoExtension.class)
public class SigninHandlerHelperTest {
	
	@InjectMocks
	private SigninHandlerHelperImpl helper;
	
	private String username;
	
	private User signin;
	
	private User exist;
	
	@BeforeEach
	void setup() {
		signin = new User();
		signin.setId("signin");
		signin.setUsername(username);
		signin.setPassword("password");
		signin.setEnabled(true);
		signin.setRoles(null);
		
		exist = new User();
		exist.setId("exist");
		exist.setUsername(username);
		exist.setPassword(Argon2Utils.encode("password"));
		exist.setEnabled(true);
		exist.setRoles(null);
	}
	
//	@Test
	void test_validateCombinator_isMatch() {
		Map<String, Object> test = helper.validateCombinator(username, signin, exist);
		
		assertThat(test).isNotNull();
		assertThat(test.get("isMatch")).isEqualTo(true);
		assertThat(test.get("token")).isNotNull();
	}
	
	@Test
	void test_validateCombinator_isNotMatch() {
		signin.setPassword("wordpass");
		Map<String, Object> test = helper.validateCombinator(username, signin, exist);
		
		assertThat(test).isNotNull();
		assertThat(test.get("isMatch")).isEqualTo(false);
		assertThat(test.get("token")).isNull();
	}
	
}
