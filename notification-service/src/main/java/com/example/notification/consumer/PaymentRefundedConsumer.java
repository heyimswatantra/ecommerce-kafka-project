package com.example.notification.consumer;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.InventoryReservedEvent;
import com.example.common.events.PaymentRefundedEvent;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentRefundedConsumer {

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_REFUNDED,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume (PaymentRefundedEvent event) {

        log.info("Payment refunded successfully : {}", event);

    }
}
