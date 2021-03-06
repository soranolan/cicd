package com.example.cicd.demo.handler;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IRedisService;
import com.example.cicd.core.service.IUserService;
import com.example.cicd.demo.helper.ISigninHandlerHelper;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class SigninHandler extends BaseHandler {
	
	private Validator validator;
	
	private IUserService userService;
	
	private ISigninHandlerHelper helper;
	
	private IRedisService redisService;
	
	public SigninHandler(IUserService userService, ISigninHandlerHelper helper, Validator validator, IRedisService redisService) {
		this.userService = userService;
		this.helper = helper;
		this.validator = validator;
		this.redisService = redisService;
	}
	
	public Mono<ServerResponse> signIn(ServerRequest request) {
		String username = request.pathVariable("username");
		
		JSONObject logParams = new JSONObject();
		logParams.put("username", username);
		log.info(DEFAULT.value(), () -> logParams);
		
		Mono<User> existUser = redisService.get(username)
											// check if user account is activated
											.filter(user -> equalsIgnoreCase(user.getIsActivated(), "true"))
											// if redis return empty then turn to db
											.switchIfEmpty(Mono.defer(() -> userService.findOneByUsername(username, "true")
																						// if db exist then insert redis
																						.flatMap(user -> redisService.set(user))));
		Mono<User> signinUser = request.bodyToMono(User.class).doOnNext(body -> validate(body, validator));
		Mono<Map<String, Object>> valid = signinUser.zipWith(existUser, (signin, exist) -> helper.validateCombinator(username, signin, exist));
		return okResponse(valid, Map.class);
	}
	
}
