package com.example.cicd.core.enums;

public enum PathInformation {
	
	MODULE("api"),
	
	VERSION("v1.0.0"),
	
	APP("todolist"),
	
	DEFAULT("/" + MODULE.value() + "/" + VERSION.value() + "/" + APP.value());
	
	private final String value;
	
	private PathInformation(String value) {
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
