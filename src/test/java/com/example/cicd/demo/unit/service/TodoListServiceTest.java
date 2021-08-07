package com.example.cicd.demo.unit.service;

import static com.example.cicd.core.enums.EntityKey.CREATED_AT;
import static com.example.cicd.core.enums.SortDirection.ASC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import com.example.cicd.demo.helper.ITodoListServiceHelper;
import com.example.cicd.demo.model.TodoList;
import com.example.cicd.demo.repository.ITodoListRepository;
import com.example.cicd.demo.service.impl.TodoListServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class TodoListServiceTest {
	
	@Mock
	private ITodoListRepository repository;
	
	@Mock
	private ITodoListServiceHelper helper;
	
	@InjectMocks
	private TodoListServiceImpl service;
	
	private String id;
	
	private TodoList mockData;
	
	@BeforeEach
	void setup() {
		id = "testId";
		mockData = new TodoList();
		mockData.setId(id);
		mockData.setCreator("creator");
		mockData.setSharedTo(new String[] { "id002", "id003" });
		mockData.setTitle("title");
		mockData.setContent("content");
		mockData.setExpirationDate("expirationDate");
	}
	
	@Test
	void test_findAllByCreator() {
		Flux<TodoList> expect = Flux.just(mockData, mockData);
		when(repository.findAll(ArgumentMatchers.<Example<TodoList>>any(), ArgumentMatchers.<Sort>any())).thenReturn(expect);
		
		String sortBy = CREATED_AT.value() + ":" + ASC.value();
		Flux<TodoList> test = service.findAllByCreator(id, sortBy);
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).findAll(ArgumentMatchers.<Example<TodoList>>any(), ArgumentMatchers.<Sort>any());
		verifyNoMoreInteractions(repository);
	}
	
	@Test
	void test_find() {
		Mono<TodoList> expect = Mono.just(mockData);
		when(repository.findById(anyString())).thenReturn(expect);
		Mono<TodoList> test = service.find(id);
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).findById(anyString());
		verifyNoMoreInteractions(repository, helper);
	}
	
	@Test
	void test_add_withOut_createdAt() {
		Mono<TodoList> expect = Mono.just(mockData);
		when(repository.insert(any(TodoList.class))).thenReturn(expect);
		Mono<TodoList> test = service.add(mockData);
		
		assertThat(test).isNotNull().isEqualTo(expect);
		assertThat(test.block().getCreatedAt()).isNotNull();
		assertThat(test.block().getUpdatedAt()).isNotNull();
		verify(repository, times(1)).insert(any(TodoList.class));
		verifyNoMoreInteractions(repository, helper);
	}
	
	@Test
	void test_add_with_createdAt() {
		mockData.setCreatedAt("createdAt");
		mockData.setUpdatedAt("updatedAt");
		Mono<TodoList> expect = Mono.just(mockData);
		when(repository.insert(any(TodoList.class))).thenReturn(expect);
		Mono<TodoList> test = service.add(mockData);
		
		assertThat(test).isNotNull().isEqualTo(expect);
		assertThat(test.block().getCreatedAt()).isNotNull();
		assertThat(test.block().getUpdatedAt()).isNotNull();
		verify(repository, times(1)).insert(any(TodoList.class));
		verifyNoMoreInteractions(repository, helper);
	}
	
	@Test
	void test_modify() {
		Mono<TodoList> expect = Mono.just(mockData);
		when(repository.save(any(TodoList.class))).thenReturn(expect);
		Mono<TodoList> test = service.modify(mockData);
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).save(any(TodoList.class));
		verifyNoMoreInteractions(repository, helper);
	}
	
	@Test
	void test_remove() {
		Mono<Void> expect = Mono.empty();
		when(repository.deleteById(anyString())).thenReturn(expect);
		Mono<Void> test = service.remove(id);
		
		assertThat(test).isNotNull().isEqualTo(expect);
		verify(repository, times(1)).deleteById(anyString());
		verifyNoMoreInteractions(repository, helper);
	}
	
}
