package com.example.cicd.demo.helper;

import com.example.cicd.demo.model.TodoList;

public interface ITodoListHandlerHelper {
	
	/**
	 * modify todolist
	 * 
	 * @param newTodo request new todolist object
	 * @param foundTodo exist todolist object
	 * @return combine todolist object
	 */
	public TodoList modifyCombinator(TodoList newTodo, TodoList foundTodo);
	
}
