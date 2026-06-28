package com.example.payment.service;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.OrderCreatedEvent;
import com.example.common.events.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void processPayment(OrderCreatedEvent order) {

        try {
            Thread.sleep(2000); // simulate payment delay

            PaymentCompletedEvent event = PaymentCompletedEvent.builder()
                    .eventId(UUID.randomUUID())
                    .uniqueTxnId(order.getUniqueTxnId())
                    .eventType("PAYMENT_COMPLETED")
                    .orderId(order.getOrderId())
                    .paymentId(UUID.randomUUID())
                    .createdAt(Instant.now())
                    .status("SUCCESS")
                    .build();

            log.info("Order event received: \n {}", order);
            kafkaTemplate.send(
                    KafkaTopics.PAYMENT_EVENTS,
                    order.getOrderId().toString(),
                    event
            );
            log.info("Payment event sent: \n {}", event);


        } catch (Exception e) {
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}