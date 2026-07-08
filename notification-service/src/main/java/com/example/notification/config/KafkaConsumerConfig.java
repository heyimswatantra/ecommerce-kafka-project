package com.example.notification.config;

import com.example.common.events.InventoryReservedEvent;
import com.example.common.events.PaymentRefundRequestedEvent;
import com.example.common.events.PaymentRefundedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private <T> ConsumerFactory<String, T> createConsumerFactory(
            Class<T> eventType,
            KafkaProperties properties,
            ObjectMapper objectMapper) {

        Map<String, Object> config =
                new HashMap<>(properties.buildConsumerProperties());

        JsonDeserializer<T> deserializer =
                new JsonDeserializer<>(eventType, objectMapper);

        deserializer.addTrustedPackages("com.example.common.events");
        deserializer.setUseTypeHeaders(false);

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    public ConsumerFactory<String, InventoryReservedEvent> inventoryConsumerFactory(
            KafkaProperties properties,
            ObjectMapper objectMapper) {

        return createConsumerFactory(
                InventoryReservedEvent.class,
                properties,
                objectMapper);
    }

    @Bean
    public ConsumerFactory<String, PaymentRefundedEvent> refundConsumerFactory(
            KafkaProperties properties,
            ObjectMapper objectMapper) {

        return createConsumerFactory(
                PaymentRefundedEvent.class,
                properties,
                objectMapper);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, InventoryReservedEvent>
    inventoryKafkaListenerContainerFactory(
            ConsumerFactory<String, InventoryReservedEvent> inventoryConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, InventoryReservedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(inventoryConsumerFactory);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRefundedEvent>
    refundKafkaListenerContainerFactory(
            ConsumerFactory<String, PaymentRefundedEvent> refundConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, PaymentRefundedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(refundConsumerFactory);

        return factory;
    }
}
