package com.example.cicd.core.unit.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.server.ServerWebExchange;

import com.example.cicd.core.misc.CustomAuthenticationManager;
import com.example.cicd.core.repository.CustomSecurityContextRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class CustomSecurityContextRepositoryTest {
	
	@Mock
	private CustomAuthenticationManager manager;
	
	@InjectMocks
	private CustomSecurityContextRepository repository;
	
	@Test
	void test_save() {
		assertThrows(UnsupportedOperationException.class, () -> repository.save(null, null));
	}
	
	@Test
	void test_load() {
		// TODO not fully code covered
		ServerWebExchange swe = Mockito.mock(ServerWebExchange.class);
		ServerHttpRequest shr = Mockito.mock(ServerHttpRequest.class);
		HttpHeaders headers = Mockito.mock(HttpHeaders.class);
		Mockito.when(swe.getRequest()).thenReturn(shr);
		Mockito.when(swe.getRequest().getHeaders()).thenReturn(headers);
		Mockito.when(swe.getRequest().getHeaders().getFirst(AUTHORIZATION)).thenReturn("Bearer 1234");
		Mono<SecurityContext> test = repository.load(swe);
		
		assertThat(test).isNotNull();
	}
	
}
