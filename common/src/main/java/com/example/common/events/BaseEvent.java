package com.example.common.events;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent {

    private UUID eventId;

    private Instant createdAt;

    private String eventType;
}