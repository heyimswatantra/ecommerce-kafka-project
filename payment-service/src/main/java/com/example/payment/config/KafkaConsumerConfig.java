package com.example.payment.config;

import com.example.common.events.OrderCreatedEvent;
import com.example.common.events.PaymentRefundRequestedEvent;
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

@Configuration
@EnableKafka
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
    public ConsumerFactory<String, OrderCreatedEvent> orderConsumerFactory(
            KafkaProperties properties,
            ObjectMapper objectMapper) {

        return createConsumerFactory(
                OrderCreatedEvent.class,
                properties,
                objectMapper);
    }

    @Bean
    public ConsumerFactory<String, PaymentRefundRequestedEvent> refundConsumerFactory(
            KafkaProperties properties,
            ObjectMapper objectMapper) {

        return createConsumerFactory(
                PaymentRefundRequestedEvent.class,
                properties,
                objectMapper);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent>
    orderKafkaListenerContainerFactory(
            ConsumerFactory<String, OrderCreatedEvent> orderConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(orderConsumerFactory);

        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRefundRequestedEvent>
    refundKafkaListenerContainerFactory(
            ConsumerFactory<String, PaymentRefundRequestedEvent> refundConsumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, PaymentRefundRequestedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(refundConsumerFactory);

        return factory;
    }
}