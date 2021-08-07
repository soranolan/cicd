package com.example.cicd.core.handler;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
	
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
		Map<String, Object> map = super.getErrorAttributes(request, options);
		Throwable e = getError(request);
		map.put("message", e.getMessage());
		return map;
	}
	
}
