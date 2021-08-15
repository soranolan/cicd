package com.example.cicd.core.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.cicd.core.model.BaseDocument;

public interface IBaseRepository<T extends BaseDocument> extends ReactiveMongoRepository<T, String> {

}
