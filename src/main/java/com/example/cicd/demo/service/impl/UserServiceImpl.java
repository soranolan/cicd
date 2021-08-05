package com.example.cicd.demo.service.impl;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.cicd.core.model.User;
import com.example.cicd.core.util.Argon2Utils;
import com.example.cicd.demo.repository.IUserRepository;
import com.example.cicd.demo.service.IUserService;

import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements IUserService {
	
	private IUserRepository repository;
	
	public UserServiceImpl(IUserRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Mono<User> findOneByAccount(String account) {
		User probe = new User();
		probe.setUsername(account);
		Example<User> example = Example.of(probe);
		return repository.findOne(example);
	}
	
	@Override
	public Mono<User> add(User entity) {
//		String now = DateUtils.now();
//		if (Optional.ofNullable(entity.getCreatedAt()).isEmpty()) { entity.setCreatedAt(now); }
//		if (Optional.ofNullable(entity.getUpdatedAt()).isEmpty()) { entity.setUpdatedAt(now); }
		entity.setPassword(Argon2Utils.encode(entity.getPassword()));
		return repository.insert(entity);
	}
	
}
