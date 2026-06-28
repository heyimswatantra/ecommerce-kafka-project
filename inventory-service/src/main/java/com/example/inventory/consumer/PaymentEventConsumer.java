package com.example.inventory.consumer;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.PaymentCompletedEvent;
import com.example.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = KafkaTopics.PAYMENT_EVENTS,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(PaymentCompletedEvent event) {

        log.info("Payment completed event: {}", event.getOrderId());

        inventoryService.reserveInventoryAfter3Attempts(event);
    }
}
