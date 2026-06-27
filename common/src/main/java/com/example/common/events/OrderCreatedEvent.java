package com.example.common.events;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent extends BaseEvent {

    private UUID orderId;

    private UUID userId;

    private BigDecimal amount;

    private List<String> items;
}