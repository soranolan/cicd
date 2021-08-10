package com.example.cicd.demo.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.cicd.core.model.User;
import com.example.cicd.core.util.Argon2Utils;
import com.example.cicd.core.util.DateUtils;
import com.example.cicd.demo.repository.IUserRepository;
import com.example.cicd.demo.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class UserServiceImpl implements IUserService {
	
	private IUserRepository repository;
	
	public UserServiceImpl(IUserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Mono<User> findOneByUsername(String username) {
		User probe = new User();
		probe.setUsername(username);
		probe.setEnabled(true);
		Example<User> example = Example.of(probe);
		
		JSONObject logParams = new JSONObject();
		logParams.put("username", username);
		logParams.put("probe", probe);
		logParams.put("example", example);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.findOne(example);
	}
	
	@Override
	public Mono<User> add(User entity) {
		String now = DateUtils.now();
		if (Optional.ofNullable(entity.getCreatedAt()).isEmpty()) { entity.setCreatedAt(now); }
		if (Optional.ofNullable(entity.getUpdatedAt()).isEmpty()) { entity.setUpdatedAt(now); }
		entity.setPassword(Argon2Utils.encode(entity.getPassword()));
		
		JSONObject logParams = new JSONObject();
		logParams.put("entity", entity);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.insert(entity);
	}
	
}
