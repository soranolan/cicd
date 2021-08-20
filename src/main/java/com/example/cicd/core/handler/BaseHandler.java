package com.example.cicd.core.handler;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;

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
	
	/**
	 * common validate method
	 * 
	 * @param body request entity
	 * @param validator validator
	 */
	public void validate(Object body, Validator validator) {
		Errors errors = new BeanPropertyBindingResult(body, body.getClass().getName());
		validator.validate(body, errors);
		if (errors.getAllErrors().isEmpty()) { return; }
		
		String error = errors.getFieldErrors().stream()
												.map(fieldError -> fieldError.getField() + fieldError.getDefaultMessage())
												.collect(joining(","));
		throw new ServerWebInputException(error);
	}
	
}
