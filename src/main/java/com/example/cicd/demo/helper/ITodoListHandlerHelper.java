package com.example.cicd.demo.helper;

import com.example.cicd.demo.model.TodoList;

public interface ITodoListHandlerHelper {
	
	/**
	 * modify todolist
	 * 
	 * @param newTodo request new todo object
	 * @param foundTodo exist todo object
	 * @return combine todo object
	 */
	public TodoList modifyCombinator(TodoList newTodo, TodoList foundTodo);
	
}
