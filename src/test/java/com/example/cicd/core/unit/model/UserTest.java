package com.example.cicd.core.unit.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.model.User;

@ExtendWith(MockitoExtension.class)
class UserTest {
	
	@InjectMocks
	private User user;
	
	@Test
	void test_isAccountNonExpired() {
		boolean test = user.isAccountNonExpired();
		assertThat(test).isFalse();
	}
	
	@Test
	void test_isAccountNonLocked() {
		boolean test = user.isAccountNonLocked();
		assertThat(test).isFalse();
	}
	
	@Test
	void test_isCredentialsNonExpired() {
		boolean test = user.isCredentialsNonExpired();
		assertThat(test).isFalse();
	}
	
	@Test
	void test_isEnabled() {
		boolean test = user.isEnabled();
		assertThat(test).isFalse();
	}
	
}
