package com.servicenow.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServicenowEmailApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicenowEmailApplication.class, args);

	}

}
