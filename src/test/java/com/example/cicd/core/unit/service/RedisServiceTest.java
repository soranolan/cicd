package com.example.cicd.core.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.impl.RedisServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class RedisServiceTest {
	
	@Mock
	private ReactiveValueOperations<String, User> operations;
	
	@Mock
	private ReactiveRedisTemplate<String, User> template;
	
	@InjectMocks
	private RedisServiceImpl service;
	
	private User mockData;
	
	@BeforeEach
	void setup() {
		mockData = new User();
		mockData.setId("testId");
		mockData.setUsername("username");
		mockData.setPassword("password");
		mockData.setEnabled(true);
		mockData.setRoles(null);
	}
	
	@Test
	void test_set() {
		when(template.opsForValue()).thenReturn(operations);
		Mono<Boolean> expect = Mono.just(true);
		when(operations.set(anyString(), any(User.class), any(Duration.class))).thenReturn(expect);
		Mono<User> test = service.set(mockData);
		
		assertThat(test).isNotNull();
		verify(operations, times(1)).set(anyString(), any(User.class), any(Duration.class));
		verifyNoMoreInteractions(operations);
		verifyNoMoreInteractions(template);
	}
	
	@Test
	void test_get() {
		when(template.opsForValue()).thenReturn(operations);
		Mono<User> expect = Mono.just(mockData);
		when(operations.get(anyString())).thenReturn(expect);
		Mono<User> test = service.get(mockData.getUsername());
		
		assertThat(test).isNotNull();
		verify(operations, times(1)).get(anyString());
		verifyNoMoreInteractions(operations);
		verifyNoMoreInteractions(template);
	}
	
}
