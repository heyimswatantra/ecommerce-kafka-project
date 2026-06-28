package com.example.notification.consumer;

import com.example.common.constants.KafkaTopics;
import com.example.common.events.InventoryReservedEvent;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = KafkaTopics.INVENTORY_EVENTS,
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume (InventoryReservedEvent event) {

        log.info("Inventory reserved event received : {}", event);

        notificationService.dispatchNotification(event);
    }
}
