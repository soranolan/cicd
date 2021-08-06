package com.example.cicd.core.unit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.util.Argon2Utils;


@ExtendWith(MockitoExtension.class)
public class Argon2UtilsTest {
	
	private String password;
	
	@BeforeEach
	void setup() {
		password = "password";
	}
	
	@Test
	void test_private_constructor() {
		final Constructor<?>[] constructors = Argon2Utils.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
	@Test
	void test_encode() {
		String test = Argon2Utils.encode(password);
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_matches_pass() {
		boolean test = Argon2Utils.matches(password, Argon2Utils.encode(password));
		assertThat(test).isNotNull();
		assertThat(test).isEqualTo(true);
	}
	
	@Test
	void test_matches_fail() {
		boolean test = Argon2Utils.matches(password + "!", Argon2Utils.encode(password));
		assertThat(test).isNotNull();
		assertThat(test).isEqualTo(false);
	}
	
}
