package com.example.cicd.demo.helper.impl;

import org.springframework.stereotype.Component;

import com.example.cicd.demo.helper.ITodoListHandlerHelper;
import com.example.cicd.demo.model.TodoList;

@Component
public class TodoListHandlerHelperImpl implements ITodoListHandlerHelper {
	
	@Override
	public TodoList modifyCombinator(TodoList newTodo, TodoList foundTodo) {
		TodoList merge = new TodoList();
		merge.setId(foundTodo.getId());
		merge.setCreatedAt(foundTodo.getCreatedAt());
		merge.setCreator(foundTodo.getCreator());
		merge.setSharedTo(newTodo.getSharedTo());
		merge.setTitle(newTodo.getTitle());
		merge.setContent(newTodo.getContent());
		merge.setExpirationDate(newTodo.getExpirationDate());
		return merge;
	}
	
}
