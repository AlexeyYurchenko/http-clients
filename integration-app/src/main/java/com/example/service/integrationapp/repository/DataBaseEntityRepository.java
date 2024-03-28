package com.example.service.integrationapp.repository;

import com.example.service.integrationapp.entity.DataBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DataBaseEntityRepository extends JpaRepository<DataBaseEntity, UUID> {
}
