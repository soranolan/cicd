package com.example.cicd.demo.unit.handler;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.model.User;
import com.example.cicd.demo.handler.SignupHandler;
import com.example.cicd.demo.router.SignupRouter;
import com.example.cicd.demo.service.impl.UserServiceImpl;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
public class SignupHandlerTest {
	
	@Mock
	private UserServiceImpl service;
	
	@InjectMocks
	private SignupHandler handler;
	
	@InjectMocks
	private SignupRouter router;
	
	private WebTestClient client;
	
	private User mockData;
	
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToRouterFunction(router.signupRoute(handler)).build();
		
		mockData = new User();
		mockData.setId("mockId");
		mockData.setUsername("username");
		mockData.setPassword("password");
		mockData.setEnabled(true);
		
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		mockData.setRoles(roles);
	}
	
	@Test
	void test_signUp() {
		Mono<User> expect = Mono.just(mockData);
		when(service.add(any(User.class))).thenReturn(expect);
		
		client.post().uri(DEFAULT.value() + "/signup")
					.accept(APPLICATION_JSON)
					.bodyValue(mockData)
					.exchange()
					.expectStatus().isCreated()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
	@Test
	void test_checkUsername() {
		Mono<User> expect = Mono.just(mockData);
		when(service.findOneByUsername(anyString())).thenReturn(expect);
		
		client.get().uri(DEFAULT.value() + "/signup/username")
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
}
