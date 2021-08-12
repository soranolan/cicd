package com.example.cicd.demo.handler;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IRedisService;
import com.example.cicd.demo.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class SignupHandler extends BaseHandler {
	
	private Validator validator;
	
	private IUserService userService;
	
	private IRedisService redisService;
	
	public SignupHandler(IUserService userService, Validator validator, IRedisService redisService) {
		this.userService = userService;
		this.validator = validator;
		this.redisService = redisService;
	}
	
	public Mono<ServerResponse> signUp(ServerRequest request) {
		Mono<User> user = request.bodyToMono(User.class)
									.doOnNext(body -> validate(body, validator))
									.flatMap(newUser -> userService.add(newUser))
									.flatMap(newUser -> redisService.set(newUser));
		return createdResponse(user, User.class);
	}
	
	public Mono<ServerResponse> checkUsername(ServerRequest request) {
		String username = request.pathVariable("username");
		
		JSONObject logParams = new JSONObject();
		logParams.put("username", username);
		log.info(DEFAULT.value(), () -> logParams);
		
		Mono<User> user = redisService.get(username).switchIfEmpty(Mono.defer(() -> userService.findOneByUsername(username)));
		return okResponse(user, User.class);
	}
	
}
