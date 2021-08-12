package com.example.cicd.demo.unit.handler;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static org.mockito.ArgumentMatchers.any;
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
import com.example.cicd.core.service.impl.RedisServiceImpl;
import com.example.cicd.demo.handler.SigninHandler;
import com.example.cicd.demo.helper.impl.SigninHandlerHelperImpl;
import com.example.cicd.demo.router.SigninRouter;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class SigninHandlerTest {
	
	@Mock
	private Validator validator;
	
	@Mock
	private RedisServiceImpl redisService;
	
	@Mock
	private SigninHandlerHelperImpl helper;
	
	@InjectMocks
	private SigninHandler handler;
	
	@InjectMocks
	private SigninRouter router;
	
	private WebTestClient client;
	
	private User mockData;
	
	private Map<String, Object> mockMap;
	
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToRouterFunction(router.signinRoute(handler)).build();
		
		mockData = new User();
		mockData.setId("mockId");
		mockData.setUsername("username");
		mockData.setPassword("password");
		mockData.setEnabled(true);
		
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		mockData.setRoles(roles);
		
		mockMap = new HashMap<String, Object>();
		mockMap.put("isMatch", "true");
	}
	
	@Test
	void test_signIn() {
		when(helper.validateCombinator(anyString(), any(User.class), any(User.class))).thenReturn(mockMap);
		
		Mono<User> expectFind = Mono.just(mockData);
		when(redisService.get(anyString())).thenReturn(expectFind);
		
		client.post().uri(DEFAULT.value() + "/signin/username")
					.accept(APPLICATION_JSON)
					.bodyValue(mockData)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(Map.class)
					.hasSize(1);
	}
	
}
