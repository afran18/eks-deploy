package com.afran.order_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderRequest(

        @NotBlank(message = "Username is required")
        String userName,

        @NotNull(message = "Product ID is required")
        UUID productId,

        @Positive(message = "Quantity must be greater than zero")
        Integer quantity
) {
        public CreateOrderRequest {
                if (quantity == null) {
                        quantity = 1;
                }
        }
}