package com.example.cicd.demo.handler;

import static com.example.cicd.core.enums.EntityKey.CREATOR;
import static com.example.cicd.core.enums.PathVariable.ID;
import static com.example.cicd.core.enums.PathVariable.SORT_BY;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.cicd.core.handler.BaseHandler;
import com.example.cicd.demo.helper.ITodoListHandlerHelper;
import com.example.cicd.demo.model.TodoList;
import com.example.cicd.demo.service.ITodoListService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TodoListHandler extends BaseHandler {
	
	private ITodoListService service;
	
	private ITodoListHandlerHelper helper;
	
	public TodoListHandler(ITodoListService service, ITodoListHandlerHelper helper) {
		this.service = service;
		this.helper = helper;
	}
	
	public Mono<ServerResponse> all(ServerRequest request) {
		String creator = request.pathVariable(CREATOR.value());
		String sortBy = request.pathVariable(SORT_BY.value());
		Flux<TodoList> all = service.findAllByCreator(creator, sortBy);
		return okResponse(all, TodoList.class);
	}
	
	public Mono<ServerResponse> find(ServerRequest request) {
		String id = request.pathVariable(ID.value());
		Mono<TodoList> foundMono = service.find(id);
		return foundMono.flatMap(found -> okResponse(Mono.just(found), TodoList.class)).switchIfEmpty(notFound());
	}
	
	public Mono<ServerResponse> add(ServerRequest request) {
		Mono<TodoList> todoList = request.bodyToMono(TodoList.class).flatMap(newList -> service.add(newList));
		return createdResponse(todoList, TodoList.class);
	}
	
	public Mono<ServerResponse> modify(ServerRequest request) {
		String id = request.pathVariable(ID.value());
		Mono<TodoList> foundMono = service.find(id);
		Mono<TodoList> newMono = request.bodyToMono(TodoList.class);
		Mono<TodoList> zipMono = newMono.zipWith(foundMono, (newTodo, foundTodo) -> helper.modifyCombinator(newTodo, foundTodo));
		return zipMono.flatMap(mono -> okResponse(service.modify(mono), TodoList.class)).switchIfEmpty(notFound());
	}
	
	public Mono<ServerResponse> remove(ServerRequest request) {
		String id = request.pathVariable(ID.value());
		Mono<TodoList> foundMono = service.find(id);
		return foundMono.flatMap(found -> okResponse(service.remove(found.getId()), TodoList.class)).switchIfEmpty(notFound());
	}
	
}
