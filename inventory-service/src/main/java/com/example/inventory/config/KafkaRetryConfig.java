package com.example.inventory.config;

import jakarta.validation.ValidationException;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

@Configuration
public class KafkaRetryConfig {

    @Bean
    public DefaultErrorHandler errorHandler(
            KafkaTemplate<String, Object> kafkaTemplate) {

        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(
                        kafkaTemplate,
                        (record, ex) ->
                                new TopicPartition(
                                        record.topic() + "-dlq",
                                        record.partition()));

        ExponentialBackOffWithMaxRetries backOff =
                new ExponentialBackOffWithMaxRetries(3);

        backOff.setInitialInterval(1000);
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(8000);

        DefaultErrorHandler handler =
                new DefaultErrorHandler(recoverer, backOff);

//         only retry this type of exception
//        handler.addRetryableExceptions(
//                ConnectException.class,
//                SocketTimeoutException.class);

//        handler.addNotRetryableExceptions(
//                IllegalArgumentException.class,
//                ValidationException.class);

        return handler;
    }
}
