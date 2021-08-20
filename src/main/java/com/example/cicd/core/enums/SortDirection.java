package com.example.cicd.core.enums;

public enum SortDirection {
	
	ASC("ASC"),
	
	DESC("DESC");
	
	private final String value;
	
	private SortDirection(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
