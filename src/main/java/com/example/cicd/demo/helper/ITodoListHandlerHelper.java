package com.example.cicd.demo.helper;

import com.example.cicd.demo.model.TodoList;

public interface ITodoListHandlerHelper {
	
	public TodoList modifyCombinator(TodoList newTodo, TodoList foundTodo);
	
}
