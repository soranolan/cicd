package com.example.cicd.core.unit.job;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.job.HealthDetect;
import com.example.cicd.core.service.impl.HealthDetectServiceImpl;

@ExtendWith(MockitoExtension.class)
class HealthDetectTest {
	
	@Mock
	private HealthDetectServiceImpl service;
	
	@InjectMocks
	private HealthDetect detect;
	
	@Test
	void test_execute() {
		detect.execute();
		verify(service, times(1)).disk();
		verify(service, times(1)).heapMemory();
		verify(service, times(1)).thread();
	}
	
}
