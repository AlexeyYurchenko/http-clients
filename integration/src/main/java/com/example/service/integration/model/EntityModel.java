package com.example.service.integration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityModel {

    private UUID id;

    private String name;

    private Instant date;

    public static EntityModel createMocModel(String name) {
        return new EntityModel(UUID.randomUUID(),name,Instant.now());
    }
}
