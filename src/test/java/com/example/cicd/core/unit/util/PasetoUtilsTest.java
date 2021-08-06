package com.example.cicd.core.unit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.util.PasetoUtils;

import dev.paseto.jpaseto.Paseto;

@ExtendWith(MockitoExtension.class)
public class PasetoUtilsTest {
	
	private String subject;
	
	@BeforeEach
	void setup() {
		subject = "subject";
	}
	
	@Test
	void test_algo() throws NoSuchAlgorithmException {
		boolean isExist = false;
		for (Provider provider : Security.getProviders()) {
			for (Provider.Service service : provider.getServices()) {
				String algorithm = service.getAlgorithm();
				if ("Ed25519".equals(algorithm)) {
					System.out.println(algorithm);
					System.out.println(service);
					isExist = true;
				}
			}
		}
		if (!isExist) {
			throw new NoSuchAlgorithmException();
		}
	}
	
	@Test
	void test_private_constructor() {
		final Constructor<?>[] constructors = PasetoUtils.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
//	@Test
	void test_compact_without_claims() {
		String test = PasetoUtils.compact(subject);
		assertThat(test).isNotNull();
	}
	
//	@Test
	void test_compact_with_claims() {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("claim_key", "claim_value");
		
		String test = PasetoUtils.compact(subject, claims);
		assertThat(test).isNotNull();
	}
	
//	@Test
	void test_valid_pass() {
		String token = PasetoUtils.compact(subject);
		boolean test = PasetoUtils.valid(token);
		assertThat(test).isNotNull();
		assertThat(test).isEqualTo(true);
	}
	
//	@Test
	void test_valid_fail() {
		String token = PasetoUtils.compact(subject);
		boolean test = PasetoUtils.valid(token + "!");
		assertThat(test).isNotNull();
		assertThat(test).isEqualTo(false);
	}
	
//	@Test
	void test_parse() {
		String token = PasetoUtils.compact(subject);
		Paseto test = PasetoUtils.parse(token);
		assertThat(test).isNotNull();
	}
	
}
