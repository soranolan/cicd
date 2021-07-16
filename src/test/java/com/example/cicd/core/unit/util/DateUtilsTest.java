package com.example.cicd.core.unit.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.enums.DateFormat;
import com.example.cicd.core.util.DateUtils;

@ExtendWith(MockitoExtension.class)
class DateUtilsTest {
	
	@Test
	void test_private_constructor() {
		final Constructor<?>[] constructors = DateUtils.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
	@Test
	void test_now() {
		String test = DateUtils.now();
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_now_with_pattern() {
		String test = DateUtils.now(DateFormat.NANO);
		assertThat(test).isNotNull();
	}
	
}
