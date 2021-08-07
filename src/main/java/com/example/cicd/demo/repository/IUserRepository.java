package com.example.cicd.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.cicd.core.model.User;

public interface IUserRepository extends ReactiveMongoRepository<User, String> {

}
