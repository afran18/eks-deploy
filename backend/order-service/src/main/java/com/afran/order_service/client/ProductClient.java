package com.afran.order_service.client;

import com.afran.order_service.dto.request.ReserveProductRequest;
import com.afran.order_service.dto.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient productRestClient;

    public ProductResponse getProductById(UUID productId) {
        return productRestClient
                .get()
                .uri("/api/v1/products/{productId}", productId)
                .retrieve()
                .body(ProductResponse.class);
    }

    public ProductResponse reserveProduct(
            UUID productId,
            Integer quantity
    ) {

        return productRestClient
                .patch()
                .uri("/api/v1/products/{productId}/reserve", productId)
                .body(new ReserveProductRequest(quantity))
                .retrieve()
                .body(ProductResponse.class);
    }
}
