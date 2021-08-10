package com.example.cicd.demo.integration.handler;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.model.User;
import com.example.cicd.demo.model.TodoList;

@SpringBootTest
@AutoConfigureWebTestClient
class TodoListHandlerTest {
	
	@Autowired
	private WebTestClient client;
	
	private String username;
	
	private String creator;
	
	private User mockUser;
	
	@BeforeEach
	void setup() {
		username = "1";
		creator = "id001";
		
		mockUser = new User();
		mockUser.setUsername(username);
		mockUser.setPassword("2");
		
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ROLE_USER);
		mockUser.setRoles(roles);
	}
	
	@Test
	void test_all() {
		String token = client.post().uri(DEFAULT.value() + "/signin/" + username)
						.accept(APPLICATION_JSON)
						.bodyValue(mockUser)
						.exchange()
						.expectStatus().isOk()
						.expectHeader().contentType(APPLICATION_JSON)
						.expectBodyList(Map.class)
						.hasSize(1)
						.returnResult().getResponseBody()
						.get(0).get("token").toString();
		
		client.get().uri(DEFAULT.value() + "/" + creator + "&createdAt:DESC")
					.accept(APPLICATION_JSON)
					.headers(http -> http.setBearerAuth(token))
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(TodoList.class)
					.hasSize(2);
	}
	
}
