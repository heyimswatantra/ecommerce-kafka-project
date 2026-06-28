package com.example.notification.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, Object> producerFactory(
            KafkaProperties properties,
            ObjectMapper objectMapper
    ) {

        Map<String, Object> config =
                new HashMap<>(properties.buildConsumerProperties());

        JsonSerializer<Object> serializer =
                new JsonSerializer<>(objectMapper);

        serializer.setAddTypeInfo(false);

        return new DefaultKafkaProducerFactory<>(
                config,
                new StringSerializer(),
                serializer);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate (ProducerFactory<String, Object> producerFactory) {

        return  new KafkaTemplate<>(producerFactory);
    }
}
