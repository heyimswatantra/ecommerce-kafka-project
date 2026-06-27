package com.example.order.controller;

import com.example.order.dto.CreateOrderRequest;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Map<String, String> createOrder(@RequestBody CreateOrderRequest request) {

        String orderId = orderService.createOrder(request);

        return Map.of(
                "orderId", orderId,
                "status", "CREATED"
        );
    }
}