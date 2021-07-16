package com.example.cicd.core.enums;

public enum DateFormat {
	
	NANO("yyyy-MM-dd HH:mm:ss.nnnnnnnnn");
	
	private final String value;
	
	private DateFormat(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
