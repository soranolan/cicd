package com.example.cicd.core.service;

import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;

import com.example.cicd.core.model.BaseDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBaseService<T extends BaseDocument> {
	
	/**
	 * find by id
	 * 
	 * @param id primary key
	 * @return Mono
	 */
	public Mono<T> find(String id);
	
	/**
	 * find all by example
	 * 
	 * @param example example entity
	 * @return Flux
	 */
	public Flux<T> findAll(Example<T> example);
	
	/**
	 * add one
	 * 
	 * @param entity insert entity
	 * @return Mono
	 */
	public Mono<T> add(T entity);
	
	/**
	 * update
	 * 
	 * @param entity update content
	 * @return Mono
	 */
	public Mono<T> modify(T entity);
	
	/**
	 * remove by id
	 * 
	 * @param id primary key
	 * @return void
	 */
	public Mono<Void> remove(String id);
	
	/**
	 * delete all by publisher
	 * 
	 * @param entityStream Flux or Mono
	 * @return void
	 */
	public Mono<Void> deleteAll(Publisher<T> entityStream);
	
}
