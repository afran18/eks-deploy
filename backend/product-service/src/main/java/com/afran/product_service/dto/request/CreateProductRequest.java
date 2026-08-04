package com.afran.product_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest (
    @NotBlank(message = "Product Name is required")
    String productName,

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    BigDecimal price,

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity cannot be negative")
    Integer quantity
){

}
