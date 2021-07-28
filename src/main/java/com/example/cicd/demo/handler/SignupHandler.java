package com.example.cicd.demo.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.core.model.User;
import com.example.cicd.demo.service.IUserService;

import reactor.core.publisher.Mono;

@Component
public class SignupHandler extends BaseHandler {
	
	private IUserService service;
	
	public SignupHandler(IUserService service) {
		this.service = service;
	}
	
	public Mono<ServerResponse> signUp(ServerRequest request) {
		Mono<User> user = request.bodyToMono(User.class).flatMap(newUser -> service.add(newUser));
		return createdResponse(user, User.class);
	}
	
	public Mono<ServerResponse> checkAccount(ServerRequest request) {
		String account = request.pathVariable("account");
		Mono<User> user = service.findOneByAccount(account);
		return okResponse(user, User.class);
	}
	
}
