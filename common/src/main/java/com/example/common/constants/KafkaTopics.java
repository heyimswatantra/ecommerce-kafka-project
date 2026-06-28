package com.example.common.constants;

public final class KafkaTopics {

    private KafkaTopics() {}

    public static final String ORDER_EVENTS = "order-events";
    public static final String PAYMENT_EVENTS = "payment-events";
    public static final String INVENTORY_EVENTS = "inventory-events";
    public static final String PAYMENT_DLQ = "payment-dlq";
    public static final String PAYMENT_REFUNDS = "payment-refunds";

    public static final String PAYMENT_REFUNDED = "payment-refunded";

}