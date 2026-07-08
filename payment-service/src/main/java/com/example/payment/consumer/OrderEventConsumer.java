package com.example.payment.consumer;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.OrderCreatedEvent;
import com.example.common.events.PaymentRefundRequestedEvent;
import com.example.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final PaymentService paymentService;

    @KafkaListener(
            topics = KafkaTopics.ORDER_EVENTS,
            containerFactory = "orderKafkaListenerContainerFactory"
    )    public void consume(OrderCreatedEvent event) {

        log.info("Received order event: {}", event.getOrderId());

        paymentService.processPayment(event);
    }

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_REFUNDS,
            containerFactory = "refundKafkaListenerContainerFactory"
    )
    public void refund(PaymentRefundRequestedEvent event) {
        log.info("Payment Refund event: \n{}", event);
        paymentService.refund(event);
    }
}
