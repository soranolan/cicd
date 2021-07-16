package com.example.cicd.demo.router;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static com.example.cicd.core.enums.PathVariable.QUERY_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.demo.handler.TodoListHandler;

@Configuration
public class TodoListRouter {
	
	@Bean
	public RouterFunction<ServerResponse> route(TodoListHandler handler) {
		return RouterFunctions
				.route(GET(DEFAULT.value() + "/{creator}&{sortBy}").and(accept(APPLICATION_JSON)), handler::all)
				.andRoute(GET(DEFAULT.value() + QUERY_ID.value()).and(accept(APPLICATION_JSON)), handler::find)
				.andRoute(POST(DEFAULT.value()).and(accept(APPLICATION_JSON)), handler::add)
				.andRoute(PUT(DEFAULT.value() + QUERY_ID.value()).and(accept(APPLICATION_JSON)), handler::modify)
				.andRoute(DELETE(DEFAULT.value() + QUERY_ID.value()).and(accept(APPLICATION_JSON)), handler::remove);
	}
	
}
