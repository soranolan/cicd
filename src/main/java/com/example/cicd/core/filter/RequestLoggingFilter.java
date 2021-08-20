package com.example.cicd.core.filter;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import java.net.InetAddress;
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
		
		JSONObject logParams = new JSONObject();
		if (remoteAddress != null) {
			InetAddress address = remoteAddress.getAddress();
			logParams.put("HostAddress", address.getHostAddress());
			logParams.put("HostName", address.getHostName());
			logParams.put("Port", remoteAddress.getPort());
		}
		logParams.put("Method", request.getMethod());
		logParams.put("URI", request.getURI());
		logParams.put("Path", request.getPath());
		logParams.put("Headers", request.getHeaders());
		logParams.put("QueryParams", request.getQueryParams());
		log.info(DEFAULT.value(), () -> logParams);
		
		return chain.filter(exchange);
	}
	
}
