package com.example.cicd.demo.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.core.model.User;
import com.example.cicd.core.util.Argon2Utils;
import com.example.cicd.core.util.PasetoUtils;
import com.example.cicd.demo.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Component
public class SigninHandler extends BaseHandler {
	
	private IUserService service;
	
	public SigninHandler(IUserService service) {
		this.service = service;
	}
	
	public Mono<ServerResponse> signIn(ServerRequest request) {
		String account = request.pathVariable("account");
		if (log.isDebugEnabled()) { log.debug("[SEARCH TAG] signin account >>> [{}]", account); }
		
		Mono<User> existUser = service.findOneByAccount(account);
		Mono<User> signinUser = request.bodyToMono(User.class);
		Mono<Map<String, Object>> exist = signinUser.zipWith(existUser).flatMap(objects -> {
			boolean isMatch = Argon2Utils.matches(objects.getT1().getPassword(), objects.getT2().getPassword());
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("isMatch", isMatch);
			if (isMatch) { result.put("token", PasetoUtils.compact(account)); }
			return Mono.just(result);
		});
		return okResponse(exist, Map.class);
	}
	
}
