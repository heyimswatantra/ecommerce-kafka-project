package com.example.order.config;

import com.example.common.constants.KafkaTopics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic orderEventsTopic() {
        return TopicBuilder
                .name(KafkaTopics.ORDER_EVENTS)
                .partitions(3)
                .replicas(1)
                .build();
    }


    @Bean
    public NewTopic paymentEventsTopic() {
        return TopicBuilder.name(KafkaTopics.PAYMENT_EVENTS)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inventoryEventsTopic() {
        return TopicBuilder.name(KafkaTopics.INVENTORY_EVENTS)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentDlqTopic() {
        return TopicBuilder.name(KafkaTopics.PAYMENT_DLQ)
                .partitions(1)
                .replicas(1)
                .build();
    }
}