package com.afran.order_service.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {
}
