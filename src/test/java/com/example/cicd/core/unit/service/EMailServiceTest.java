package com.example.cicd.core.unit.service;

import static com.example.cicd.core.enums.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.model.User;
import com.example.cicd.core.service.impl.EMailServiceImpl;

@ExtendWith(MockitoExtension.class)
class EMailServiceTest {
	
	@Mock
	private JavaMailSender sender;
	
	@InjectMocks
	private EMailServiceImpl service;
	
	@Test
	void test_sendMail() {
		service.sendMail("to", "subject", "text");
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_sendActivateLink() {
		User mockUser = new User();
		List<Role> roles = new ArrayList<>();
		roles.add(ROLE_USER);
		mockUser.setRoles(roles);
		service.sendActivateLink(mockUser);
		assertThat(service).isNotNull();
	}
	
}
