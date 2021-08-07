package com.example.cicd.core.repository;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.cicd.core.misc.CustomAuthenticationManager;

import reactor.core.publisher.Mono;

@Component
public class CustomSecurityContextRepository implements ServerSecurityContextRepository {
	
	private CustomAuthenticationManager manager;
	
	public CustomSecurityContextRepository(CustomAuthenticationManager manager) {
		this.manager = manager;
	}
	
	@Override
	public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	@Override
	public Mono<SecurityContext> load(ServerWebExchange swe) {
		return Mono.justOrEmpty(swe.getRequest().getHeaders().getFirst(AUTHORIZATION))
					.filter(header -> header.startsWith("Bearer "))
					.flatMap(header -> {
						String token = header.substring(7);
						Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
						return manager.authenticate(authentication).map(SecurityContextImpl::new);
					});
	}
	
}
