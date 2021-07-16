package com.example.cicd.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.cicd.core.enums.DateFormat;

public class DateUtils {
	
	private DateUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static String now() {
		return now(DateFormat.NANO);
	}
	
	public static String now(DateFormat pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.value());
		LocalDateTime now = LocalDateTime.now();
		return now.format(formatter);
	}
	
}
