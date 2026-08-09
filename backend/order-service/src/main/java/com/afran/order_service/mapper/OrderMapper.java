package com.afran.order_service.mapper;

import com.afran.order_service.dto.request.CreateOrderRequest;
import com.afran.order_service.dto.response.OrderResponse;
import com.afran.order_service.entity.Order;
import com.afran.order_service.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderRequest request) {
        return Order.builder()
                .userName(request.userName())
                .productId(request.productId())
                .totalAmount(request.totalAmount())
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