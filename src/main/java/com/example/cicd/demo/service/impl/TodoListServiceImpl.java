package com.example.cicd.demo.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.cicd.core.util.DateUtils;
import com.example.cicd.demo.helper.ITodoListServiceHelper;
import com.example.cicd.demo.model.TodoList;
import com.example.cicd.demo.repository.ITodoListRepository;
import com.example.cicd.demo.service.ITodoListService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
		return repository.findAll(example, sort);
	}
	
	@Override
	public Mono<TodoList> find(String id) {
		return repository.findById(id);
	}
	
	@Override
	public Mono<TodoList> add(TodoList entity) {
		String now = DateUtils.now();
		if (Optional.ofNullable(entity.getCreatedAt()).isEmpty()) { entity.setCreatedAt(now); }
		if (Optional.ofNullable(entity.getUpdatedAt()).isEmpty()) { entity.setUpdatedAt(now); }
		return repository.insert(entity);
	}
	
	@Override
	public Mono<TodoList> modify(TodoList entity) {
		String now = DateUtils.now();
		entity.setUpdatedAt(now);
		return repository.save(entity);
	}
	
	@Override
	public Mono<Void> remove(String id) {
		return repository.deleteById(id);
	}
	
}
