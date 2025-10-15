package com.matus.spacecatalog_backend.service;


import com.matus.spacecatalog_backend.entity.CelestialObject;
import com.matus.spacecatalog_backend.repository.CelestialObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CelestialObjectService {

    @Autowired
    private CelestialObjectRepository repository;

    public CelestialObject create( CelestialObject object) {
       return repository.save(object);
    }

    public Page<CelestialObject> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<CelestialObject> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public CelestialObject updateObject (Long id, CelestialObject updatedData){

        CelestialObject existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celestial object not found"));

        if(!Objects.equals(existing.getObjectName(), updatedData.getObjectName())) {
            repository.findByObjectName(updatedData.getObjectName()).ifPresent(other -> {
                if(!other.getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Object name already exists");
                }
            });
        }

        if(!Objects.equals(existing.getObjectDesignation(), updatedData.getObjectDesignation())
                && updatedData.getObjectDesignation() != null
                && !updatedData.getObjectDesignation().isBlank()) {
            repository.findByObjectDesignation(updatedData.getObjectDesignation()).ifPresent(other -> {
                if(!other.getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Object designation already exists");
                }
            });
        }

        existing.setObjectName(updatedData.getObjectName());
        existing.setObjectType(updatedData.getObjectType());
        existing.setObjectDesignation(updatedData.getObjectDesignation());
        existing.setDiscoveryYear(updatedData.getDiscoveryYear());
        existing.setDistanceFromSunAu(updatedData.getDistanceFromSunAu());
        existing.setObjectSpeedKmS(updatedData.getObjectSpeedKmS());
        existing.setObjectMassToEarth(updatedData.getObjectMassToEarth());
        existing.setShortDescription(updatedData.getShortDescription());
        existing.setImageMainUrl(updatedData.getImageMainUrl());

        return repository.save(existing);

    }

    public Page<CelestialObject> findByType(String objectType, Pageable pageable){
        return repository.findAllObjectType(objectType, pageable);
    }
    
}
