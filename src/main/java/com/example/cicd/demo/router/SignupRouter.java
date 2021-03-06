package com.example.cicd.demo.router;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.demo.handler.SignupHandler;

@Configuration
public class SignupRouter {
	
	@Bean
	public RouterFunction<ServerResponse> signupRoute(SignupHandler handler) {
		return RouterFunctions
				.route(POST(DEFAULT.value() + "/signup").and(accept(APPLICATION_JSON)), handler::signUp)
				.andRoute(GET(DEFAULT.value() + "/signup/{username}").and(accept(APPLICATION_JSON)), handler::checkUsername)
				.andRoute(POST(DEFAULT.value() + "/signup/activate").and(accept(APPLICATION_JSON)), handler::activate)
				.andRoute(POST(DEFAULT.value() + "/signup/reactivate").and(accept(APPLICATION_JSON)), handler::reactivate);
	}
	
}
