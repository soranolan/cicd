package com.example.cicd.core.filter;

import java.net.InetSocketAddress;

import org.json.JSONObject;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class RequestLoggingFilter implements WebFilter {
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		InetSocketAddress remoteAddress = request.getRemoteAddress();
		
		JSONObject loggingRequest = new JSONObject();
		if (remoteAddress != null) {
			loggingRequest.put("HostAddress", remoteAddress.getAddress().getHostAddress());
			loggingRequest.put("HostName", remoteAddress.getAddress().getHostName());
			loggingRequest.put("Port", remoteAddress.getPort());
		}
		loggingRequest.put("Method", request.getMethod());
		loggingRequest.put("URI", request.getURI());
		loggingRequest.put("Path", request.getPath());
		loggingRequest.put("Headers", request.getHeaders());
		loggingRequest.put("QueryParams", request.getQueryParams());
		log.info("[SEARCH_TAG] loggingRequest >>> [{}]", () -> loggingRequest.toString());
		
		return chain.filter(exchange);
	}
	
}
