package com.example.cicd.core.unit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.util.PasetoUtils;

import dev.paseto.jpaseto.Claims;

@ExtendWith(MockitoExtension.class)
class PasetoUtilsTest {
	
	private String subject;
	
	@BeforeEach
	void setup() {
		subject = "subject";
	}
	
	@Test
	void test_private_constructor() {
		final Constructor<?>[] constructors = PasetoUtils.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
	@Test
	void test_compact_without_claims() {
		String test = PasetoUtils.compact(subject);
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_compact_with_claims() {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("claim_key", "claim_value");
		
		String test = PasetoUtils.compact(subject, claims);
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_valid_pass() {
		String token = PasetoUtils.compact(subject);
		boolean test = PasetoUtils.valid(token);
		assertThat(test).isTrue();
	}
	
	@Test
	void test_valid_fail() {
		String token = PasetoUtils.compact(subject);
		boolean test = PasetoUtils.valid(token + "!");
		assertThat(test).isFalse();
	}
	
	@Test
	void test_parse() {
		String token = PasetoUtils.compact(subject);
		Claims test = PasetoUtils.getClaims(token);
		assertThat(test).isNotNull();
	}
	
}
