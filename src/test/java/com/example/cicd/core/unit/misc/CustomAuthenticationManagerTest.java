package com.example.cicd.core.unit.misc;

import static com.example.cicd.core.enums.Role.ROLE_USER;
import static com.example.cicd.core.util.PasetoUtils.compact;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.misc.CustomAuthenticationManager;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticationManagerTest {
	
	@InjectMocks
	private CustomAuthenticationManager manager;
	
	@Test
	void test_save() {
		// TODO not fully code covered
		List<Role> roles = new ArrayList<>();
		roles.add(ROLE_USER);
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("role", roles);
		String token = compact("testid", claims);
		
		Authentication auth = mock(Authentication.class);
		when(auth.getCredentials()).thenReturn(token);
		
		Mono<Authentication> test = manager.authenticate(auth);
		
		assertThat(test).isNotNull();
	}
	
}
