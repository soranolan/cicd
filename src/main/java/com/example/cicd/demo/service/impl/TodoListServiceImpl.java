package com.example.cicd.demo.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.cicd.core.util.DateUtils;
import com.example.cicd.demo.helper.ITodoListServiceHelper;
import com.example.cicd.demo.model.TodoList;
import com.example.cicd.demo.repository.ITodoListRepository;
import com.example.cicd.demo.service.ITodoListService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@PreAuthorize("hasRole('USER')")
@Service
public class TodoListServiceImpl implements ITodoListService {
	
	private ITodoListRepository repository;
	
	private ITodoListServiceHelper helper;
	
	public TodoListServiceImpl(ITodoListRepository repository, ITodoListServiceHelper helper) {
		this.repository = repository;
		this.helper = helper;
	}
	
	@Override
	public Flux<TodoList> findAllByCreator(String creator, String sortBy) {
		TodoList probe = new TodoList();
		probe.setCreator(creator);
		Example<TodoList> example = Example.of(probe);
		Sort sort = helper.buildSort(sortBy);
		
		JSONObject logParams = new JSONObject();
		logParams.put("creator", creator);
		logParams.put("sortBy", sortBy);
		logParams.put("probe", probe);
		logParams.put("example", example);
		logParams.put("sort", sort);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.findAll(example, sort);
	}
	
	@Override
	public Mono<TodoList> find(String id) {
		JSONObject logParams = new JSONObject();
		logParams.put("id", id);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.findById(id);
	}
	
	@Override
	public Mono<TodoList> add(TodoList entity) {
		String now = DateUtils.now();
		if (Optional.ofNullable(entity.getCreatedAt()).isEmpty()) { entity.setCreatedAt(now); }
		if (Optional.ofNullable(entity.getUpdatedAt()).isEmpty()) { entity.setUpdatedAt(now); }
		
		JSONObject logParams = new JSONObject();
		logParams.put("entity", entity);
		log.info(DEFAULT.value(), () -> logParams);
		
		return repository.insert(entity);
	}
	
	@Override
	public Mono<TodoList> modify(TodoList entity) {
		String now = DateUtils.now();
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
