package com.afran.order_service.dto.request;

import com.afran.order_service.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequest(

        @NotNull(message = "Order status is required")
        OrderStatus status
) {
}