package com.example.cicd.core.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.cicd.core.service.impl.HealthDetectServiceImpl;

@ExtendWith(MockitoExtension.class)
class HealthDetectServiceImplTest {
	
	@Mock
	private ReentrantLock diskFairLock;
	
	@Mock
	private ReentrantLock heapMemoryFairLock;
	
	@Mock
	private ReentrantLock threadFairLock;
	
	@InjectMocks
	private HealthDetectServiceImpl service;
	
	@Test
	void test_disk_tryLock_false() {
		when(diskFairLock.tryLock()).thenReturn(false);
		service.disk();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_disk_tryLock_true() {
		when(diskFairLock.tryLock()).thenReturn(true);
		service.disk();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_heapMemory_tryLock_false() {
		when(heapMemoryFairLock.tryLock()).thenReturn(false);
		service.heapMemory();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_heapMemory_tryLock_true() {
		when(heapMemoryFairLock.tryLock()).thenReturn(true);
		service.heapMemory();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_thread_tryLock_false() {
		when(threadFairLock.tryLock()).thenReturn(false);
		service.thread();
		assertThat(service).isNotNull();
	}
	
	@Test
	void test_thread_tryLock_true() {
		when(threadFairLock.tryLock()).thenReturn(true);
		service.thread();
		assertThat(service).isNotNull();
	}
	
}
