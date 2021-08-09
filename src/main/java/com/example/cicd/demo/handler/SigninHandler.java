package com.example.cicd.demo.handler;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.core.model.User;
import com.example.cicd.demo.helper.ISigninHandlerHelper;
import com.example.cicd.demo.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class SigninHandler extends BaseHandler {
	
	private IUserService service;
	
	private ISigninHandlerHelper helper;
	
	public SigninHandler(IUserService service, ISigninHandlerHelper helper) {
		this.service = service;
		this.helper = helper;
	}
	
	public Mono<ServerResponse> signIn(ServerRequest request) {
		String username = request.pathVariable("username");
		
		JSONObject logParams = new JSONObject();
		logParams.put("username", username);
		log.info("[SEARCH TAG] logParams >>> [{}]", () -> logParams);
		
		Mono<User> existUser = service.findOneByUsername(username);
		Mono<User> signinUser = request.bodyToMono(User.class);
		Mono<Map<String, Object>> valid = signinUser.zipWith(existUser, (signin, exist) -> helper.validateCombinator(username, signin, exist));
		return okResponse(valid, Map.class);
	}
	
}
