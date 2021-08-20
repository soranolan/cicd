package com.example.cicd.core.util;

import static com.example.cicd.core.enums.DateFormat.NANO;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.cicd.core.enums.DateFormat;

public class DateUtils {
	
	private DateUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * yyyy-MM-dd HH:mm:ss.nnnnnnnnn
	 * 
	 * @return now
	 */
	public static String now() {
		return now(NANO);
	}
	
	/**
	 * now
	 * 
	 * @param pattern pattern
	 * @return format now
	 */
	public static String now(DateFormat pattern) {
		DateTimeFormatter formatter = ofPattern(pattern.value());
		LocalDateTime now = LocalDateTime.now();
		return now.format(formatter);
	}
	
}
