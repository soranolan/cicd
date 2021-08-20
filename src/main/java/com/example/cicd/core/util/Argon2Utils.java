package com.example.cicd.core.util;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class Argon2Utils {
	
	private Argon2Utils() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * encrypt
	 * 
	 * @param rawPassword raw text
	 * @return encrypt
	 */
	public static String encode(String rawPassword) {
		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
		return encoder.encode(rawPassword);
	}
	
	/**
	 * compare
	 * 
	 * @param rawPassword raw text
	 * @param encodedPassword encrypt text
	 * @return true if match, else return false
	 */
	public static boolean matches(String rawPassword, String encodedPassword) {
		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
		return encoder.matches(rawPassword, encodedPassword);
	}
	
}
