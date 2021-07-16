package com.example.cicd.core.enums;

public enum PathInformation {
	
	VERSION("v1.0.0"),
	
	MODULE("api"),
	
	APP("todolist"),
	
	DEFAULT("/" + VERSION.value() + "/" + MODULE.value() + "/" + APP.value());
	
	private final String value;
	
	private PathInformation(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
