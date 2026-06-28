package com.example.common.events;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseEvent {

    private UUID eventId;

    private UUID uniqueTxnId;

    private Instant createdAt;

    private String eventType;
}