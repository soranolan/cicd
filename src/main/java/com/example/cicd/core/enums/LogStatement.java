package com.example.cicd.core.enums;

public enum LogStatement {
	
	DEFAULT("[SEARCH_TAG] logParams >>> [{}]"),
	
	LOCK("lock"),
	
	UNLOCK("unlock"),
	
	SKIP("skip");
	
	private final String value;
	
	private LogStatement(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
