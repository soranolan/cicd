package com.example.cicd.core.misc;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.cicd.core.util.PasetoUtils;

import dev.paseto.jpaseto.Claims;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {
	
	@SuppressWarnings("unchecked")
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = authentication.getCredentials().toString();
		return Mono.just(PasetoUtils.valid(token))
				.filter(valid -> valid)
				.switchIfEmpty(Mono.empty())
				.map(valid -> {
					Claims claims = PasetoUtils.parse(token).getClaims();
					String username = claims.getSubject();
					List<String> roles = claims.get("role", List.class);
					return new UsernamePasswordAuthenticationToken(username, null, roles.stream()
																						.map(SimpleGrantedAuthority::new)
																						.collect(Collectors.toList())
																	);
				});
	}
	
}
