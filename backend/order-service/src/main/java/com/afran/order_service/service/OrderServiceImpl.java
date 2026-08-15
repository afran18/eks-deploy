package com.afran.order_service.service;

import com.afran.order_service.client.ProductClient;
import com.afran.order_service.dto.request.CreateOrderRequest;
import com.afran.order_service.dto.request.UpdateOrderRequest;
import com.afran.order_service.dto.response.OrderResponse;
import com.afran.order_service.dto.response.ProductResponse;
import com.afran.order_service.entity.Order;
import com.afran.order_service.entity.OrderStatus;
import com.afran.order_service.exception.ResourceNotFoundException;
import com.afran.order_service.mapper.OrderMapper;
import com.afran.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        ProductResponse product;

        try {
           product = productClient.getProductById(request.productId());
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Product not found");
        }

        if(product.quantity() <= 0) {
            throw new ResourceNotFoundException("Product is out of stock");
        }

        if(product.quantity() < request.quantity()) {
            throw new ResourceNotFoundException("Insufficient quantity");
        }

        BigDecimal totalAmount =
                product.price().multiply(
                        BigDecimal.valueOf(request.quantity())
                );

        Order order = orderMapper.toEntity(request, totalAmount);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse updateOrder(
            UUID orderId,
            UpdateOrderRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        order.setStatus(request.status());

        Order updatedOrder = orderRepository.save(order);

        return orderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        orderRepository.delete(order);
    }
}