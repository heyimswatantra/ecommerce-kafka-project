package com.example.inventory.consumer;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.PaymentCompletedEvent;
import com.example.common.events.PaymentRefundRequestedEvent;
import com.example.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventDlqConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "payment-events-dlq")
    public void consumeDlq(PaymentCompletedEvent payment) {

        log.error(
                "[{}] Inventory permanently failed for order {}",
                payment.getUniqueTxnId(),
                payment.getOrderId());

        PaymentRefundRequestedEvent refund = PaymentRefundRequestedEvent.builder()
                .paymentRefundId(UUID.randomUUID())
                .orderId(payment.getOrderId())
                .paymentId(payment.getPaymentId())
                .reason("some reason")
                .createdAt(Instant.now())
                .eventId(payment.getEventId())
                .uniqueTxnId(payment.getUniqueTxnId())
                .eventType("PAYMENT-REFUND")
                .build();

        kafkaTemplate.send(
                KafkaTopics.PAYMENT_REFUNDS,
                payment.getOrderId().toString(),
                refund);
    }
}
