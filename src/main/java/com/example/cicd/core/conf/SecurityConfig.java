package com.example.cicd.core.conf;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.cicd.core.misc.AuthenticationManager;
import com.example.cicd.core.repository.SecurityContextRepository;

@EnableWebFluxSecurity
public class SecurityConfig {
	
	private AuthenticationManager authenticationManager;
	
	private SecurityContextRepository securityContextRepository;
	
	public SecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
		this.authenticationManager = authenticationManager;
		this.securityContextRepository = securityContextRepository;
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http
			.cors()
		.and()
			.csrf().disable()
			.formLogin().disable()
			.httpBasic().disable()
			.authenticationManager(authenticationManager)
			.securityContextRepository(securityContextRepository)
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
		config.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		config.setAllowedHeaders(Arrays.asList("Content-Type"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
}
