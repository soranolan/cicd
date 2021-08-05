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
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
	
	@Override
	@SuppressWarnings("unchecked")
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();
		return Mono.just(PasetoUtils.valid(authToken)).filter(valid -> valid).switchIfEmpty(Mono.empty())
				.map(valid -> {
					Claims claims = PasetoUtils.parse(authToken).getClaims();
					String username = claims.getSubject();
					List<String> rolesMap = claims.get("role", List.class);
					return new UsernamePasswordAuthenticationToken(username, null, rolesMap.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
				});
	}
	
}
