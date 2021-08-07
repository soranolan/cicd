package com.example.cicd.core.util;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2Utils {
	
	private Argon2Utils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String encode(String rawPassword) {
		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
		return encoder.encode(rawPassword);
	}
	
	public static boolean matches(String rawPassword, String encodedPassword) {
		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
		return encoder.matches(rawPassword, encodedPassword);
	}
	
}
