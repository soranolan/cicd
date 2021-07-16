package com.example.cicd.core.handler;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class BaseHandler {
	
	/**
	 * <pre>
	 * HttpStatus	: 200
	 * ContentType	: JSON
	 * Method		: body
	 * <pre>
	 * 
	 * @param producer object(Mono or Flux)
	 * @param elementClass target class
	 * @return Mono
	 */
	public Mono<ServerResponse> okResponse(Object producer, Class<?> elementClass) {
		return ok().contentType(APPLICATION_JSON).body(producer, elementClass);
	}
	
	/**
	 * <pre>
	 * HttpStatus	: 201
	 * ContentType	: JSON
	 * Method		: body
	 * <pre>
	 * 
	 * @param producer object(Mono or Flux)
	 * @param elementClass target class
	 * @return Mono
	 */
	public Mono<ServerResponse> createdResponse(Object producer, Class<?> elementClass) {
		return status(CREATED).contentType(APPLICATION_JSON).body(producer, elementClass);
	}
	
	/**
	 * <pre>
	 * HttpStatus	: 404
	 * <pre>
	 * 
	 * @return Mono
	 */
	public Mono<ServerResponse> notFound() {
		return ServerResponse.notFound().build();
	}
	
}
