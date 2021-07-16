package com.example.cicd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.cicd.demo.service.ITodoListService;

@SpringBootTest
class CicdApplicationTests {
	
	@Autowired
	private ITodoListService service;
	
	@Test
	void contextLoads() {
		assertThat(service).isNotNull();
	}
	
}
