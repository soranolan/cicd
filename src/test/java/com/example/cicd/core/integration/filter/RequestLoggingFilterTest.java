package com.example.cicd.core.integration.filter;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static com.example.cicd.core.enums.Role.ROLE_USER;
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

@SpringBootTest
@AutoConfigureWebTestClient
class RequestLoggingFilterTest {
	
	@Autowired
	private WebTestClient client;
	
	private User mockData;
	
	@BeforeEach
	void setup() {
		mockData = new User();
		mockData.setId("mockId");
		mockData.setUsername("username");
		mockData.setPassword("password");
		mockData.setEnabled(true);
		
		List<Role> roles = new ArrayList<>();
		roles.add(ROLE_USER);
		mockData.setRoles(roles);
	}
	
	@Test
	void contextLoads() {
		client.post().uri(DEFAULT.value() + "/signin/username")
						.accept(APPLICATION_JSON)
						.bodyValue(mockData)
						.exchange()
						.expectStatus().isOk()
						.expectHeader().contentType(APPLICATION_JSON)
						.expectBodyList(Map.class)
						.hasSize(0);
	}
	
}
