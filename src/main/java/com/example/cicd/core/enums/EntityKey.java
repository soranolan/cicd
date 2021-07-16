package com.example.cicd.core.enums;

public enum EntityKey {
	
	CREATED_AT("createdAt"),
	
	CREATOR("creator");
	
	private final String value;
	
	private EntityKey(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
