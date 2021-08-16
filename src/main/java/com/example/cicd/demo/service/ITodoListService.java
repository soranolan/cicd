package com.example.cicd.demo.service;

import com.example.cicd.core.service.IBaseService;
import com.example.cicd.demo.model.TodoList;

import reactor.core.publisher.Flux;

public interface ITodoListService extends IBaseService<TodoList> {
	
	/**
	 * find all by creator
	 * 
	 * @param creator user id
	 * @param sortBy sort with order
	 * @return TodoList
	 */
	public Flux<TodoList> findAllByCreator(String creator, String sortBy);
	
}
