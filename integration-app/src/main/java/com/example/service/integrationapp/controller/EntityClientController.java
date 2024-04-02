package com.example.service.integrationapp.controller;

import com.example.service.integrationapp.clients.OpenFeignClient;
import com.example.service.integrationapp.entity.DataBaseEntity;
import com.example.service.integrationapp.model.EntityModel;
import com.example.service.integrationapp.model.UpsertEntityRequest;
import com.example.service.integrationapp.service.DataBaseEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/client/entity")
public class EntityClientController {

    private final OpenFeignClient client;

    private final DataBaseEntityService service;

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList() {
        return ResponseEntity.ok(service.findAll().stream().map(EntityModel::from).toList()
        );
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<EntityModel> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(EntityModel.from(service.findById(id)));
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name) {
        return ResponseEntity.ok(EntityModel.from(service.findByName(name)));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request) {
        var newEntity = client.createEntity(request);
        var saveEntity = service.create(DataBaseEntity.from(newEntity));
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.from(saveEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request) {
        var updateEntity = client.updateEntity(id, request);
        var updateDbEntity = service.update(id, DataBaseEntity.from(updateEntity));
        return ResponseEntity.ok(EntityModel.from(updateDbEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityModel> deleteEntityById(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}