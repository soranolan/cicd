package com.example.cicd.core.unit.util;

import static com.example.cicd.core.util.PasetoUtils.compact;
import static com.example.cicd.core.util.PasetoUtils.getClaims;
import static com.example.cicd.core.util.PasetoUtils.valid;
import static java.lang.reflect.Modifier.isPrivate;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
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
			assertThat(isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
	@Test
	void test_compact_without_claims() {
		String test = compact(subject);
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_compact_with_claims() {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("claim_key", "claim_value");
		
		String test = compact(subject, claims);
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_valid_pass() {
		String token = compact(subject);
		boolean test = valid(token);
		assertThat(test).isTrue();
	}
	
	@Test
	void test_valid_fail() {
		String token = compact(subject);
		boolean test = valid(token + "!");
		assertThat(test).isFalse();
	}
	
	@Test
	void test_parse() {
		String token = compact(subject);
		Claims test = getClaims(token);
		assertThat(test).isNotNull();
	}
	
}
