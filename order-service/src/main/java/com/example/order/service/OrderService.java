package com.example.order.service;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.OrderCreatedEvent;
import com.example.order.dto.CreateOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String createOrder(CreateOrderRequest request) {

        String orderId = UUID.randomUUID().toString();

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .eventId(UUID.randomUUID())
                .uniqueTxnId(UUID.randomUUID())
                .createdAt(Instant.now())
                .eventType("ORDER_CREATED")
                .orderId(UUID.fromString(orderId))
                .userId(request.userId())
                .amount(request.amount())
                .items(request.items())
                .build();

        kafkaTemplate.send(KafkaTopics.ORDER_EVENTS, orderId, event);

        return orderId;
    }
}
