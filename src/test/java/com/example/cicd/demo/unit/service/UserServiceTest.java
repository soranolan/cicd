package com.example.cicd.demo.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import com.example.cicd.core.model.User;
import com.example.cicd.demo.repository.IUserRepository;
import com.example.cicd.demo.service.impl.UserServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private IUserRepository repository;
	
	@InjectMocks
	private UserServiceImpl service;
	
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
	void test_findOneByUsername() {
		Mono<User> expect = Mono.just(mockData);
		when(repository.findOne(ArgumentMatchers.<Example<User>>any())).thenReturn(expect);
		Mono<User> test = service.findOneByUsername("username");
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).findOne(ArgumentMatchers.<Example<User>>any());
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void test_add_withOut_createdAt() {
		Mono<User> expect = Mono.just(mockData);
		when(repository.insert(any(User.class))).thenReturn(expect);
		Mono<User> test = service.add(mockData);
		
		create(test)
			.expectNextMatches(response -> (response.getCreatedAt() != null) && (response.getUpdatedAt() != null))
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).insert(any(User.class));
	}
	
	@Test
	void test_add_with_createdAt() {
		mockData.setCreatedAt("createdAt");
		mockData.setUpdatedAt("updatedAt");
		Mono<User> expect = Mono.just(mockData);
		when(repository.insert(any(User.class))).thenReturn(expect);
		Mono<User> test = service.add(mockData);
		
		create(test)
			.expectNextMatches(response -> (response.getCreatedAt() != null) && (response.getUpdatedAt() != null))
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).insert(any(User.class));
	}
	
}
