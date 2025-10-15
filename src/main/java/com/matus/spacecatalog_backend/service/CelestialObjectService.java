package com.matus.spacecatalog_backend.service;


import com.matus.spacecatalog_backend.entity.CelestialObject;
import com.matus.spacecatalog_backend.repository.CelestialObjectRepository;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public CelestialObject create( CelestialObject object) {

        if(object.getObjectName() == null || object.getObjectName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object name cant be null or empty");
        }

        repository.findByObjectName(object.getObjectName()).ifPresent(existing -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Celestial name already exists");
        });

        if(object.getObjectDesignation() != null || !object.getObjectDesignation().isBlank()){
            repository.findByObjectDesignation(object.getObjectDesignation()).ifPresent(existing -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Celestial designation already exists");
            });
        }

       return repository.save(object);
    }

    @Transactional(readOnly = true)
    public Page<CelestialObject> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public CelestialObject findById(Long id) {

        CelestialObject existingObject = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Celestial object not found by ID"));

        return existingObject;
    }

    @Transactional
    public void delete(Long id) {
        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found for delete");
        }

        repository.deleteById(id);
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public Page<CelestialObject> findByType(String objectType, Pageable pageable){

        if(objectType == null || objectType.isBlank()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object type cant be null or empty");
        }

        return repository.findAllObjectType(objectType, pageable);
    }

}
