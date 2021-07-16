package com.example.cicd.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.cicd.demo.model.TodoList;

public interface ITodoListRepository extends ReactiveMongoRepository<TodoList, String> {
	
}
