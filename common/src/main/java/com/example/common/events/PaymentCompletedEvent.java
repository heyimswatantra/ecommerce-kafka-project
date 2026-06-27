package com.example.common.events;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent extends BaseEvent {

    private String orderId;

    private String paymentId;

    private String status;
}