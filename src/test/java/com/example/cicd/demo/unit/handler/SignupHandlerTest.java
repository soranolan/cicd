package com.example.cicd.demo.unit.handler;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static com.example.cicd.core.enums.Role.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.validation.Validator;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.model.User;
import com.example.cicd.core.service.impl.EMailServiceImpl;
import com.example.cicd.core.service.impl.RedisServiceImpl;
import com.example.cicd.core.service.impl.UserServiceImpl;
import com.example.cicd.demo.handler.SignupHandler;
import com.example.cicd.demo.router.SignupRouter;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SignupHandlerTest {
	
	@Mock
	private Validator validator;
	
	@Mock
	private UserServiceImpl userService;
	
	@Mock
	private RedisServiceImpl redisService;
	
	@Mock
	private EMailServiceImpl emailService;
	
	@InjectMocks
	private SignupHandler handler;
	
	@InjectMocks
	private SignupRouter router;
	
	private WebTestClient client;
	
	private User mockUser;
	
	private Map<String, Object> mockMap;
	
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToRouterFunction(router.signupRoute(handler)).build();
		
		mockUser = new User();
		mockUser.setId("mockId");
		mockUser.setUsername("username");
		mockUser.setPassword("password");
		mockUser.setEnabled(true);
		
		List<Role> roles = new ArrayList<>();
		roles.add(ROLE_USER);
		mockUser.setRoles(roles);
		
		mockMap = new HashMap<>();
		mockMap.put("username", "username");
		mockMap.put("email", "email");
	}
	
	@Test
	void test_signUp() {
		Mono<User> expect = Mono.just(mockUser);
		when(userService.add(any(User.class))).thenReturn(expect);
		when(redisService.set(any(User.class))).thenReturn(expect);
		
		client.post().uri(DEFAULT.value() + "/signup")
					.accept(APPLICATION_JSON)
					.bodyValue(mockUser)
					.exchange()
					.expectStatus().isCreated()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
	@Test
	void test_checkUsername_redisservice() {
		Mono<User> expect = Mono.just(mockUser);
		when(redisService.get(anyString())).thenReturn(expect);
		
		client.get().uri(DEFAULT.value() + "/signup/username")
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
	@Test
	void test_checkUsername_userservice() {
		Mono<User> empty = Mono.empty();
		Mono<User> expect = Mono.just(mockUser);
		when(redisService.get(anyString())).thenReturn(empty);
		when(userService.findOneByUsername(anyString())).thenReturn(expect);
		
		client.get().uri(DEFAULT.value() + "/signup/username")
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
	@Test
	void test_activate() {
		Mono<User> expect = Mono.just(mockUser);
		when(userService.validate(anyMap())).thenReturn(expect);
		when(userService.activate(any(User.class))).thenReturn(expect);
		when(redisService.activate(any(User.class))).thenReturn(expect);
		
		client.post().uri(DEFAULT.value() + "/signup/activate")
					.accept(APPLICATION_JSON)
					.bodyValue(mockMap)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
	@Test
	void test_reactivate() {
		Mono<User> expect = Mono.just(mockUser);
		when(userService.reactivate(anyMap())).thenReturn(expect);
		
		client.post().uri(DEFAULT.value() + "/signup/reactivate")
					.accept(APPLICATION_JSON)
					.bodyValue(mockMap)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(User.class)
					.hasSize(1);
	}
	
}
