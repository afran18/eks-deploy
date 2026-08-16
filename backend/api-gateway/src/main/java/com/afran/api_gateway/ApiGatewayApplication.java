package com.afran.api_gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ApiGatewayApplication {

	private static final Logger log = LoggerFactory.getLogger(ApiGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	@Bean
	CommandLineRunner test(Environment environment) {
		return args -> {
			log.info("AUTH = {}", environment.getProperty("AUTH_SERVICE_URL"));
			log.info("PRODUCT = {}", environment.getProperty("PRODUCT_SERVICE_URL"));
			log.info("ORDER = {}", environment.getProperty("ORDER_SERVICE_URL"));
		};
	}
}
