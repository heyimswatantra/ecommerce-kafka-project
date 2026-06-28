package com.example.inventory.service;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.InventoryReservedEvent;
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
public class InventoryService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void reserveInventory(PaymentCompletedEvent payment) {

        try {
            Thread.sleep(2000); // simulate payment delay

            InventoryReservedEvent event = InventoryReservedEvent.builder()
                    .eventId(UUID.randomUUID())
                    .eventType("INVENTORY_RESERVED")
                    .orderId(payment.getOrderId())
                    .status("SUCCESS")
                    .createdAt(Instant.now())
                    .build();

            log.info("Payment completed event received: \n {}", payment);
            kafkaTemplate.send(
                    KafkaTopics.INVENTORY_EVENTS,
                    payment.getOrderId().toString(),
                    event
            );
            log.info("Inventory reserved event sent: \n {}", event);


        } catch (Exception e) {
            throw new RuntimeException("Payment processing failed", e);
        }
    }
}