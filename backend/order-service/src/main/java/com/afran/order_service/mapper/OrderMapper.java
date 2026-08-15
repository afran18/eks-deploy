package com.afran.order_service.mapper;

import com.afran.order_service.dto.request.CreateOrderRequest;
import com.afran.order_service.dto.response.OrderResponse;
import com.afran.order_service.entity.Order;
import com.afran.order_service.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderRequest request, BigDecimal totalAmount) {
        return Order.builder()
                .userName(request.userName())
                .productId(request.productId())
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .build();
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getUserName(),
                order.getProductId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}