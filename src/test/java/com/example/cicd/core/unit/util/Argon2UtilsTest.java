package com.example.cicd.core.unit.util;

import static com.example.cicd.core.util.Argon2Utils.encode;
import static com.example.cicd.core.util.Argon2Utils.matches;
import static java.lang.reflect.Modifier.isPrivate;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.util.Argon2Utils;


@ExtendWith(MockitoExtension.class)
class Argon2UtilsTest {
	
	private String password;
	
	@BeforeEach
	void setup() {
		password = "password";
	}
	
	@Test
	void test_private_constructor() {
		final Constructor<?>[] constructors = Argon2Utils.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertThat(isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
	@Test
	void test_encode() {
		String test = encode(password);
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_matches_pass() {
		boolean test = matches(password, encode(password));
		assertThat(test).isTrue();
	}
	
	@Test
	void test_matches_fail() {
		boolean test = matches(password + "!", encode(password));
		assertThat(test).isFalse();
	}
	
}
