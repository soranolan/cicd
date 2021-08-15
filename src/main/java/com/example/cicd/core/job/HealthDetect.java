package com.example.cicd.core.job;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cicd.core.service.IHealthDetectService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class HealthDetect {
	
	@Autowired
	IHealthDetectService service;
	
	@Async("taskExecutor")
	@Scheduled(cron = "0 0 23 * * ?")
	public void execute() {
		log.info(DEFAULT.value(), () -> "main start");
		service.disk();
		service.heapMemory();
		service.thread();
		log.info(DEFAULT.value(), () -> "main end");
	}
	
}
