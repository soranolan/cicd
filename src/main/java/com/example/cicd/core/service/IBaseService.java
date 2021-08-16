package com.example.cicd.core.service;

import com.example.cicd.core.model.BaseDocument;

import reactor.core.publisher.Mono;

public interface IBaseService<T extends BaseDocument> {
	
	/**
	 * find by id
	 * 
	 * @param id primary key
	 * @return TodoList
	 */
	public Mono<T> find(String id);
	
	/**
	 * add one
	 * 
	 * @param entity TodoList
	 * @return TodoList
	 */
	public Mono<T> add(T entity);
	
	/**
	 * update
	 * 
	 * @param entity update content
	 * @return TodoList
	 */
	public Mono<T> modify(T entity);
	
	/**
	 * remove by id
	 * 
	 * @param id primary key
	 * @return void
	 */
	public Mono<Void> remove(String id);
	
}
