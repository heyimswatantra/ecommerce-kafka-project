package com.example.payment.service;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.OrderCreatedEvent;
import com.example.common.events.PaymentCompletedEvent;
import com.example.common.events.PaymentRefundRequestedEvent;
import com.example.common.events.PaymentRefundedEvent;
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

            log.info("Order event received: \n {} {}", order, order.getUniqueTxnId());
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

    public void refund(PaymentRefundRequestedEvent event) {

        try {
            log.info(
                    "[{}] Refunding payment {}",
                    event.getUniqueTxnId(),
                    event.getPaymentId());

            Thread.sleep(1000);

            PaymentRefundedEvent paymentRefundedEvent = PaymentRefundedEvent.builder()
                    .paymentRefundId(event.getPaymentRefundId())
                    .orderId(event.getOrderId())
                    .paymentId(event.getPaymentId())
                    .reason(event.getReason())
                    .createdAt(Instant.now())
                    .eventId(event.getEventId())
                    .uniqueTxnId(event.getUniqueTxnId())
                    .eventType("PAYMENT-REFUNDED")
                    .build();

            kafkaTemplate.send(
                    KafkaTopics.PAYMENT_REFUNDED,
                    event.getOrderId().toString(),
                    paymentRefundedEvent);

            log.info("Payment refunded event sent to topic");

        } catch (Exception e) {
            throw new RuntimeException("Payment refund failed", e);
        }
    }
}