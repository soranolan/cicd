package com.example.cicd.demo.unit.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.demo.helper.impl.TodoListHandlerHelperImpl;
import com.example.cicd.demo.model.TodoList;

@ExtendWith(MockitoExtension.class)
class TodoListHandlerHelperTest {
	
	@InjectMocks
	private TodoListHandlerHelperImpl helper;
	
	private TodoList newTodo;
	
	private TodoList foundTodo;
	
	@BeforeEach
	void setup() {
		newTodo = new TodoList();
		newTodo.setId("newId");
		newTodo.setCreatedAt("newCreatedAt");
		newTodo.setCreator("newCreator");
		newTodo.setSharedTo(new String[] { "newSharedTo" });
		newTodo.setTitle("newTitle");
		newTodo.setContent("newContent");
		newTodo.setExpirationDate("newExpirationDate");
		
		foundTodo = new TodoList();
		foundTodo.setId("foundId");
		foundTodo.setCreatedAt("foundCreatedAt");
		foundTodo.setCreator("foundCreator");
		foundTodo.setSharedTo(new String[] { "foundSharedTo" });
		foundTodo.setTitle("foundTitle");
		foundTodo.setContent("foundContent");
		foundTodo.setExpirationDate("foundExpirationDate");
		
	}
	
	@Test
	void test_modifyCombinator() {
		TodoList test = helper.modifyCombinator(newTodo, foundTodo);
		
		assertThat(test).isNotNull();
		assertThat(test.getId()).isEqualTo(foundTodo.getId());
		assertThat(test.getCreatedAt()).isEqualTo(foundTodo.getCreatedAt());
		assertThat(test.getCreator()).isEqualTo(foundTodo.getCreator());
		assertThat(test.getSharedTo()).isEqualTo(newTodo.getSharedTo());
		assertThat(test.getTitle()).isEqualTo(newTodo.getTitle());
		assertThat(test.getContent()).isEqualTo(newTodo.getContent());
		assertThat(test.getExpirationDate()).isEqualTo(newTodo.getExpirationDate());
	}
	
}
