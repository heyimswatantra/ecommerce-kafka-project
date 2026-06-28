package com.example.inventory.service;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProcessedEventStore {

    private final Set<UUID> processed =
            ConcurrentHashMap.newKeySet();

    public boolean alreadyProcessed(UUID eventId) {

        return !processed.add(eventId);
    }
}
