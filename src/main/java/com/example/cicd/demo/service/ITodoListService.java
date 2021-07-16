package com.example.cicd.demo.service;

import com.example.cicd.demo.model.TodoList;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITodoListService {
	
	/**
	 * find all by creator
	 * 
	 * @param creator user id
	 * @param sortBy sort with order
	 * @return TodoList
	 */
	public Flux<TodoList> findAllByCreator(String creator, String sortBy);
	
	/**
	 * find by id
	 * 
	 * @param id primary key
	 * @return TodoList
	 */
	public Mono<TodoList> find(String id);
	
	/**
	 * add one
	 * 
	 * @param entity TodoList
	 * @return TodoList
	 */
	public Mono<TodoList> add(TodoList entity);
	
	/**
	 * update
	 * 
	 * @param entity update content
	 * @return TodoList
	 */
	public Mono<TodoList> modify(TodoList entity);
	
	/**
	 * remove by id
	 * 
	 * @param id primary key
	 * @return void
	 */
	public Mono<Void> remove(String id);
	
}
