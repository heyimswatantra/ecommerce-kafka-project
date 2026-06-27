package com.example.common.events;

import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReservedEvent extends BaseEvent {

    private UUID orderId;

    private String status;
}