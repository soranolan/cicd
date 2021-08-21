package com.example.cicd.core.unit.job;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;

import com.example.cicd.core.job.InactivateUser;
import com.example.cicd.core.model.User;
import com.example.cicd.core.service.impl.UserServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class InactivateUserTest {
	
	@Mock
	private UserServiceImpl service;
	
	@InjectMocks
	private InactivateUser job;
	
	@Test
	void test_execute() {
		Flux<User> expect = Flux.just(mock(User.class));
		Mono<Void> expectDelete = Mono.empty();
		when(service.findAll(anyString())).thenReturn(expect);
		when(service.deleteAll(ArgumentMatchers.<Publisher<User>>any())).thenReturn(expectDelete);
		job.execute();
		
		verify(service, times(1)).findAll("false");
		verify(service, times(1)).deleteAll(expect);
	}
	
}
