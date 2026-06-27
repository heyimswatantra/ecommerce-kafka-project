package com.example.common.events;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCompletedEvent extends BaseEvent {

    private String orderId;

    private String paymentId;

    private String status;
}