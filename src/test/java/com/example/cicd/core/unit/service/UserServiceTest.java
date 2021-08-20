package com.example.cicd.core.unit.service;

import static com.example.cicd.core.util.PasetoUtils.compact;
import static org.apache.commons.collections4.MapUtils.getString;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static reactor.test.StepVerifier.create;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.impl.UserServiceImpl;
import com.example.cicd.demo.repository.IUserRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private IUserRepository repository;
	
	@InjectMocks
	private UserServiceImpl service;
	
	private User mockData;
	
	private Map<String, Object> mockMap;
	
	@BeforeEach
	void setup() {
		mockData = new User();
		mockData.setId("testId");
		mockData.setUsername("username");
		mockData.setPassword("password");
		mockData.setEnabled(true);
		mockData.setRoles(null);
		
		mockMap = new HashMap<>();
		mockMap.put("username", "test");
		mockMap.put("token", compact("test"));
		mockMap.put("email", "email");
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
	
	@Test
	void test_validate_true() {
		Mono<User> expect = Mono.just(mockData);
		when(repository.findOne(ArgumentMatchers.<Example<User>>any())).thenReturn(expect);
		Mono<User> test = service.validate(mockMap);
		
		create(test)
			.expectNextMatches(response -> response.getSystemMessage() == null)
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).findOne(ArgumentMatchers.<Example<User>>any());
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void test_validate_false() {
		mockMap.put("token", "token");
		Mono<User> test = service.validate(mockMap);
		
		create(test)
			.expectNextMatches(response -> equalsIgnoreCase(response.getSystemMessage(), "TOKEN_E001"))
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull();
		verify(repository, times(0)).findOne(ArgumentMatchers.<Example<User>>any());
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void test_activate_system_message_empty() {
		Mono<User> expect = Mono.just(mockData);
		when(repository.save(any(User.class))).thenReturn(expect);
		Mono<User> test = service.activate(mockData);
		
		create(test)
			.expectNextMatches(response -> equalsIgnoreCase(response.getIsActivated(), "true"))
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).save(any(User.class));
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void test_activate_system_message_not_empty() {
		mockData.setSystemMessage("message");
		Mono<User> test = service.activate(mockData);
		
		create(test)
			.expectNextMatches(response -> isBlank(response.getIsActivated()))
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull();
		verify(repository, times(0)).save(any(User.class));
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void test_reactivate() {
		Mono<User> expect = Mono.just(mockData);
		when(repository.findOne(ArgumentMatchers.<Example<User>>any())).thenReturn(expect);
		Mono<User> test = service.reactivate(mockMap);
		
		create(test)
			.expectNextMatches(response -> isNotBlank(response.getEmail()) && equalsIgnoreCase(response.getEmail(), getString(mockMap, "email")))
			.expectComplete()
			.verify();
		
		assertThat(test).isNotNull();
		verify(repository, times(1)).findOne(ArgumentMatchers.<Example<User>>any());
		verifyNoMoreInteractions(repository);
	}
	
}
