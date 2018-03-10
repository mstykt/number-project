package com.number.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class NumberServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumberServerApplication.class, args);
	}
}
