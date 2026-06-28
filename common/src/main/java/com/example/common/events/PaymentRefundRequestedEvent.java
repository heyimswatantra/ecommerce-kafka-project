package com.example.common.events;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRefundRequestedEvent extends BaseEvent {

    private UUID paymentRefundId;

    private UUID orderId;

    private UUID paymentId;

    private String reason;

    private Instant createdAt;
}
