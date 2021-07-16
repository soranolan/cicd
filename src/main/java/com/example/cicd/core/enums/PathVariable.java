package com.example.cicd.core.enums;

public enum PathVariable {
	
	ID("id"),
	
	SORT_BY("sortBy"),
	
	QUERY_ID("/{" + ID.value() + "}");
	
	private final String value;
	
	private PathVariable(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
