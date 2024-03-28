package com.example.service.integrationapp.service;

import com.example.service.integrationapp.entity.DataBaseEntity;
import com.example.service.integrationapp.repository.DataBaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseEntityService {

    private final DataBaseEntityRepository repository;

    public List<DataBaseEntity> findAll() {
    return repository.findAll();
    }
}
