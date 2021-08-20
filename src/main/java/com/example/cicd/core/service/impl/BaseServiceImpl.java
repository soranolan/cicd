package com.example.cicd.core.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static com.example.cicd.core.util.DateUtils.now;
import static java.util.Optional.ofNullable;

import org.json.JSONObject;

import com.example.cicd.core.model.BaseDocument;
import com.example.cicd.core.repository.IBaseRepository;
import com.example.cicd.core.service.IBaseService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
public class BaseServiceImpl<T extends BaseDocument> implements IBaseService<T> {
	
	private IBaseRepository<T> repository;
	
	public BaseServiceImpl(IBaseRepository<T> repository) {
		this.repository = repository;
	}
	
	@Override
	public Mono<T> find(String id) {
		JSONObject logParams = new JSONObject();
		logParams.put("id", id);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.findById(id);
	}
	
	@Override
	public Mono<T> add(T entity) {
		String now = now();
		if (ofNullable(entity.getCreatedAt()).isEmpty()) { entity.setCreatedAt(now); }
		if (ofNullable(entity.getUpdatedAt()).isEmpty()) { entity.setUpdatedAt(now); }
		
		JSONObject logParams = new JSONObject();
		logParams.put("entity", entity);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.insert(entity);
	}
	
	@Override
	public Mono<T> modify(T entity) {
		String now = now();
		entity.setUpdatedAt(now);
		
		JSONObject logParams = new JSONObject();
		logParams.put("entity", entity);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.save(entity);
	}
	
	@Override
	public Mono<Void> remove(String id) {
		JSONObject logParams = new JSONObject();
		logParams.put("id", id);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.deleteById(id);
	}
	
}
