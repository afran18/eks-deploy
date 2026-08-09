package com.afran.api_gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	@Bean
	CommandLineRunner test(Environment environment) {
		return args -> {
			System.out.println("AUTH = " + environment.getProperty("AUTH_SERVICE_URL"));
			System.out.println("PRODUCT = " + environment.getProperty("PRODUCT_SERVICE_URL"));
		};
	}

}
