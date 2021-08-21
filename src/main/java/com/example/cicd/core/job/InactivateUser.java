package com.example.cicd.core.job;

import static com.example.cicd.core.enums.LogStatement.DEFAULT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cicd.core.model.User;
import com.example.cicd.core.service.IUserService;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

@Log4j2
@Component
public class InactivateUser {
	
	@Autowired
	private IUserService service;
	
	@Scheduled(cron = "0 0 22 * * ?")
	public void execute() {
		log.info(DEFAULT.value(), () -> "Inactivate User detect start");
		Flux<User> user = service.findAll("false");
		service.deleteAll(user).subscribe();
		log.info(DEFAULT.value(), () -> "Inactivate User detect end");
	}
	
}
