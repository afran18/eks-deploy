package com.afran.order_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient productRestClient(
            @Value("${product-service-url}") String productServiceUrl) {

        System.out.println("PRODUCT SERVICE URL = [" + productServiceUrl + "]");
        return RestClient.builder()
                .baseUrl(productServiceUrl)
                .build();
    }
}
