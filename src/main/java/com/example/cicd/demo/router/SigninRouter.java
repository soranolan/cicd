package com.example.cicd.demo.router;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.demo.handler.SigninHandler;

@Configuration
public class SigninRouter {
	
	@Bean
	public RouterFunction<ServerResponse> signinRoute(SigninHandler handler) {
		return RouterFunctions
				.route(POST(DEFAULT.value() + "/signin/{username}").and(accept(APPLICATION_JSON)), handler::signIn);
	}
	
}
