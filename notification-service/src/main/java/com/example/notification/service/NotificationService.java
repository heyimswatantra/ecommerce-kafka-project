package com.example.notification.service;

import com.example.common.events.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void dispatchNotification (InventoryReservedEvent event) {

        try {
            Thread.sleep(5000);

            log.info("Notification sent successfully: \n {}", event);
        } catch (Exception e) {
            throw new RuntimeException("Notification dispatch failed", e);
        }
    }
}
