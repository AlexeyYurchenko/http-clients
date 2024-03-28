package com.example.service.integration.controller;

import com.example.service.integration.model.EntityModel;
import com.example.service.integration.model.UpsertEntityRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/entity")
public class EntityController {

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList() {
        List<EntityModel> entityModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entityModels.add(EntityModel.createMocModel("Model: " + (i + 1)));
        }
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name) {
        return ResponseEntity.ok(EntityModel.createMocModel(name));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.createMocModel(request.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request) {
        return ResponseEntity.ok(new EntityModel(id, request.getName(), Instant.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel> deleteEntityById(@PathVariable UUID id) {
        log.info("Delete entity by ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}