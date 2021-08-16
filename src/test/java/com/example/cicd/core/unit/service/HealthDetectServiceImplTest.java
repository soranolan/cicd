package com.example.cicd.core.unit.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.service.impl.HealthDetectServiceImpl;

@ExtendWith(MockitoExtension.class)
class HealthDetectServiceImplTest {
	
	@InjectMocks
	private HealthDetectServiceImpl service;
	
	@Test
	void test_disk() {
		service.disk();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_heapMemory() {
		service.heapMemory();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_thread() {
		service.thread();
		assertThat(service).isNotNull();
	}
	
}
