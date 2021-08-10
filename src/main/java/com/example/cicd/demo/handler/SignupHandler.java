package com.example.cicd.demo.handler;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.core.model.User;
import com.example.cicd.demo.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class SignupHandler extends BaseHandler {
	
	private Validator validator;
	
	private IUserService service;
	
	public SignupHandler(IUserService service, Validator validator) {
		this.service = service;
		this.validator = validator;
	}
	
	public Mono<ServerResponse> signUp(ServerRequest request) {
		Mono<User> user = request.bodyToMono(User.class)
									.doOnNext(body -> validate(body, validator))
									.flatMap(newUser -> service.add(newUser));
		return createdResponse(user, User.class);
	}
	
	public Mono<ServerResponse> checkUsername(ServerRequest request) {
		String username = request.pathVariable("username");
		
		JSONObject logParams = new JSONObject();
		logParams.put("username", username);
		log.info("[SEARCH TAG] logParams >>> [{}]", () -> logParams);
		
		Mono<User> user = service.findOneByUsername(username);
		return okResponse(user, User.class);
	}
	
}
