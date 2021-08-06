package com.example.cicd.demo.unit.handler;

import static com.example.cicd.core.enums.PathInformation.DEFAULT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.cicd.demo.handler.TodoListHandler;
import com.example.cicd.demo.helper.impl.TodoListHandlerHelperImpl;
import com.example.cicd.demo.model.TodoList;
import com.example.cicd.demo.router.TodoListRouter;
import com.example.cicd.demo.service.impl.TodoListServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class TodoListHandlerTest {
	
	@Mock
	private TodoListServiceImpl service;
	
	@Mock
	private TodoListHandlerHelperImpl helper;
	
	@InjectMocks
	private TodoListHandler handler;
	
	@InjectMocks
	private TodoListRouter router;
	
	private WebTestClient client;
	
	private String id;
	
	private TodoList mockData;
	
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToRouterFunction(router.todolistRoute(handler)).build();
		id = "testId";
		mockData = new TodoList();
		mockData.setId(id);
		mockData.setCreatedAt("createdAt");
		mockData.setUpdatedAt("updatedAt");
		mockData.setCreator("creator");
		mockData.setSharedTo(new String[] { "id002", "id003" });
		mockData.setTitle("title");
		mockData.setContent("content");
		mockData.setExpirationDate("expirationDate");
	}
	
	@Test
	void test_all() {
		Flux<TodoList> expect = Flux.just(mockData, mockData);
		when(service.findAllByCreator(anyString(), anyString())).thenReturn(expect);
		
		client.get().uri(DEFAULT.value() + "/creator&createdAt:DESC")
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(TodoList.class)
					.hasSize(2);
	}
	
	@Test
	void test_find_is_ok() {
		Mono<TodoList> expect = Mono.just(mockData);
		when(service.find(anyString())).thenReturn(expect);
		
		client.get().uri(DEFAULT.value() + "/" + id)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(TodoList.class)
					.hasSize(1);
	}
	
	@Test
	void test_find_is_not_found() {
		Mono<TodoList> expect = Mono.empty();
		when(service.find(anyString())).thenReturn(expect);
		
		client.get().uri(DEFAULT.value() + "/" + id)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isNotFound();
	}
	
	@Test
	void test_add() {
		Mono<TodoList> expect = Mono.just(mockData);
		when(service.add(any(TodoList.class))).thenReturn(expect);
		
		client.post().uri(DEFAULT.value())
					.accept(APPLICATION_JSON)
					.bodyValue(mockData)
					.exchange()
					.expectStatus().isCreated()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(TodoList.class)
					.hasSize(1);
	}
	
	@Test
	void test_modify_is_ok() {
		when(helper.modifyCombinator(any(TodoList.class), any(TodoList.class))).thenReturn(mockData);
		
		Mono<TodoList> expectFind = Mono.just(mockData);
		when(service.find(anyString())).thenReturn(expectFind);
		
		Mono<TodoList> expectModify = Mono.just(mockData);
		when(service.modify(any(TodoList.class))).thenReturn(expectModify);
		
		client.put().uri(DEFAULT.value() + "/" + id)
					.accept(APPLICATION_JSON)
					.bodyValue(mockData)
					.exchange()
					.expectStatus().isOk()
					.expectHeader().contentType(APPLICATION_JSON)
					.expectBodyList(TodoList.class)
					.hasSize(1);
	}
	
	@Test
	void test_modify_is_not_found() {
		Mono<TodoList> expect = Mono.empty();
		when(service.find(anyString())).thenReturn(expect);
		
		client.put().uri(DEFAULT.value() + "/" + id)
					.accept(APPLICATION_JSON)
					.bodyValue(mockData)
					.exchange()
					.expectStatus().isNotFound();
	}
	
	@Test
	void test_remove_is_ok() {
		Mono<TodoList> expectFind = Mono.just(mockData);
		when(service.find(anyString())).thenReturn(expectFind);
		
		Mono<Void> expect = Mono.empty();
		when(service.remove(id)).thenReturn(expect);
		
		client.delete().uri(DEFAULT.value() + "/" + id)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isOk()
					.expectBody().isEmpty();
	}
	
	@Test
	void test_remove_is_not_found() {
		Mono<TodoList> expect = Mono.empty();
		when(service.find(anyString())).thenReturn(expect);
		
		client.delete().uri(DEFAULT.value() + "/" + id)
					.accept(APPLICATION_JSON)
					.exchange()
					.expectStatus().isNotFound();
	}
	
}
