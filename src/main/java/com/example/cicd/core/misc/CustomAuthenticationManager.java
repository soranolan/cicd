package com.example.cicd.core.misc;

import static com.example.cicd.core.util.PasetoUtils.getClaims;
import static com.example.cicd.core.util.PasetoUtils.valid;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import dev.paseto.jpaseto.Claims;
import reactor.core.publisher.Mono;

@Component
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

	@SuppressWarnings("unchecked")
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = authentication.getCredentials().toString();
		Mono<Authentication> auth = Mono.just(valid(token))
										.filter(valid -> valid)
										.switchIfEmpty(Mono.empty())
										.map(valid -> {
											Claims claims = getClaims(token);
											String username = claims.getSubject();
											List<String> roles = claims.get("role", List.class);
											UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null, roles.stream().map(SimpleGrantedAuthority::new).collect(toList()));
											return authToken;
										});
		return auth;
	}

}
