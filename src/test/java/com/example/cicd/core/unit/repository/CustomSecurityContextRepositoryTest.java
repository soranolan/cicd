package com.example.cicd.core.unit.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;

import com.example.cicd.core.repository.CustomSecurityContextRepository;

@ExtendWith(MockitoExtension.class)
public class CustomSecurityContextRepositoryTest {
	
	@InjectMocks
	private CustomSecurityContextRepository repository;
	
	@Test
	void test_save() {
		ServerWebExchange swe = null;
		SecurityContext sc = null;
		assertThrows(UnsupportedOperationException.class, () -> repository.save(swe, sc));
	}
	
}
