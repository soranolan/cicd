package com.example.cicd.demo.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static com.example.cicd.core.enums.Role.ROLE_USER;
import static com.example.cicd.core.util.Argon2Utils.encode;
import static com.example.cicd.core.util.PasetoUtils.getClaims;
import static com.example.cicd.core.util.PasetoUtils.valid;
import static org.apache.commons.collections4.MapUtils.getString;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.domain.Example.of;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.cicd.core.enums.Role;
import com.example.cicd.core.model.User;
import com.example.cicd.core.service.impl.BaseServiceImpl;
import com.example.cicd.demo.repository.IUserRepository;
import com.example.cicd.demo.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	
	private IUserRepository repository;
	
	public UserServiceImpl(IUserRepository repository) {
		super(repository);
		this.repository = repository;
	}
	
	@Override
	public Mono<User> findOneByUsername(String username) {
		return findOneByUsername(username, null);
	}
	
	@Override
	public Mono<User> findOneByUsername(String username, String isActivated) {
		User probe = new User();
		probe.setUsername(username);
		if (isNotBlank(isActivated)) { probe.setIsActivated(isActivated); }
		Example<User> example = of(probe);
		
		JSONObject logParams = new JSONObject();
		logParams.put("username", username);
		logParams.put("probe", probe);
		logParams.put("example", example);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.findOne(example);
	}
	
	@Override
	public Mono<User> add(User user) {
		user.setPassword(encode(user.getPassword()));
		List<Role> roles = new ArrayList<>();
		roles.add(ROLE_USER);
		user.setRoles(roles);
		user.setIsActivated("false");
		
		return super.add(user);
	}
	
	@Override
	public Mono<User> validate(Map<String, Object> entry) {
		String token = getString(entry, "token");
		boolean isValid = valid(token);
		
		JSONObject logParams = new JSONObject();
		logParams.put("entry", entry);
		logParams.put("isValid", isValid);
		log.info(DEFAULT.value(), () -> logParams);
		
		if (isValid) { return findOneByUsername(getClaims(token).getSubject(), "false"); }
		
		User user = new User();
		List<Role> roles = new ArrayList<>();
		roles.add(ROLE_USER);
		user.setRoles(roles);
		user.setSystemMessage("TOKEN_E001");																		// Token expired
		return Mono.just(user);
	}
	
	@Override
	public Mono<User> activate(User user) {
		JSONObject logParams = new JSONObject();
		logParams.put("user", user);
		log.info(DEFAULT.value(), () -> logParams);
		
		if (isBlank(user.getSystemMessage())) {
			user.setIsActivated("true");
			return super.modify(user);
		}
		return Mono.just(user);
	}
	
	@Override
	public Mono<User> reactivate(Map<String, Object> entry) {
		log.info(DEFAULT.value(), () -> entry);
		
		String username = getString(entry, "username");
		String email = getString(entry, "email");
		Mono<User> exist = findOneByUsername(username);
		return exist.flatMap(user -> {
			user.setEmail(email);
			return Mono.just(user);
		});
	}
	
}
