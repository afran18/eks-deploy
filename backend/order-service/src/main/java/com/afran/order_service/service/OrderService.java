package com.afran.order_service.service;

import com.afran.order_service.dto.request.CreateOrderRequest;
import com.afran.order_service.dto.request.UpdateOrderRequest;
import com.afran.order_service.dto.response.OrderResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(UUID orderId);

    OrderResponse updateOrder(UUID orderId, UpdateOrderRequest request);

    void deleteOrder(UUID orderId);
}