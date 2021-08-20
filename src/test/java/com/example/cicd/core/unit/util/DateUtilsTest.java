package com.example.cicd.core.unit.util;

import static com.example.cicd.core.enums.DateFormat.NANO;
import static com.example.cicd.core.util.DateUtils.now;
import static java.lang.reflect.Modifier.isPrivate;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.util.DateUtils;

@ExtendWith(MockitoExtension.class)
class DateUtilsTest {
	
	@Test
	void test_private_constructor() {
		final Constructor<?>[] constructors = DateUtils.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertThat(isPrivate(constructor.getModifiers())).isTrue();
		}
	}
	
	@Test
	void test_now() {
		String test = now();
		assertThat(test).isNotNull();
	}
	
	@Test
	void test_now_with_pattern() {
		String test = now(NANO);
		assertThat(test).isNotNull();
	}
	
}
