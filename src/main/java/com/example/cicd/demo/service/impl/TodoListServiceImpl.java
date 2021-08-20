package com.example.cicd.demo.service.impl;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;
import static org.springframework.data.domain.Example.of;

import org.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.example.cicd.core.service.impl.BaseServiceImpl;
import com.example.cicd.demo.helper.ITodoListServiceHelper;
import com.example.cicd.demo.model.TodoList;
import com.example.cicd.demo.repository.ITodoListRepository;
import com.example.cicd.demo.service.ITodoListService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@PreAuthorize("hasRole('USER')")
@Service
public class TodoListServiceImpl extends BaseServiceImpl<TodoList> implements ITodoListService {
	
	private ITodoListRepository repository;
	
	private ITodoListServiceHelper helper;
	
	public TodoListServiceImpl(ITodoListRepository repository, ITodoListServiceHelper helper) {
		super(repository);
		this.repository = repository;
		this.helper = helper;
	}
	
	@Override
	public Flux<TodoList> findAllByCreator(String creator, String sortBy) {
		TodoList probe = new TodoList();
		probe.setCreator(creator);
		Example<TodoList> example = of(probe);
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
	
}
