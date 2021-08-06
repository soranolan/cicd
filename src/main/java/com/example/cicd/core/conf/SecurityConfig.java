package com.example.cicd.core.conf;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.cicd.core.misc.CustomAuthenticationManager;
import com.example.cicd.core.repository.CustomSecurityContextRepository;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
	
	private CustomAuthenticationManager manager;
	
	private CustomSecurityContextRepository repository;
	
	public SecurityConfig(CustomAuthenticationManager manager, CustomSecurityContextRepository repository) {
		this.manager = manager;
		this.repository = repository;
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
			.cors()
		.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.authenticationManager(manager)
			.securityContextRepository(repository)
			.authorizeExchange().pathMatchers(DEFAULT.value() + "/signup/**").permitAll()
		.and()
			.authorizeExchange().pathMatchers(DEFAULT.value() + "/signin/**").permitAll()
		.and()
			.authorizeExchange().anyExchange().authenticated();
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("http://localhost:8081", "https://vue-ci-cd-practice.herokuapp.com"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
}
