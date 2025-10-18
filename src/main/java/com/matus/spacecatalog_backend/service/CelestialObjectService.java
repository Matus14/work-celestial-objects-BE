package com.matus.spacecatalog_backend.service;


import com.matus.spacecatalog_backend.DTO.CelestialObjectRequestDTO;
import com.matus.spacecatalog_backend.DTO.CelestialObjectResponseDTO;
import com.matus.spacecatalog_backend.entity.CelestialObject;
import com.matus.spacecatalog_backend.repository.CelestialObjectRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class CelestialObjectService {

    @Autowired
    private CelestialObjectRepository repository;


    @Transactional
    public CelestialObjectResponseDTO create(CelestialObjectRequestDTO request) {

        String name = request.getObjectName() != null ? request.getObjectName().trim() : null;
        String designation = request.getObjectDesignation() != null ? request.getObjectDesignation().trim() : null;
        String type = request.getObjectType() != null ? request.getObjectType().trim() : null;
        String shortDesc = request.getShortDescription() != null ? request.getShortDescription().trim() : null;
        String imageUrl = request.getImageMainUrl() != null ? request.getImageMainUrl().trim() : null;

        if(name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cant be null or empty");
        }

        repository.findByObjectName(name).ifPresent(e -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
        });

        if(designation != null && !designation.isBlank()) {
            repository.findByObjectDesignation(designation).ifPresent(e -> {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Designation already exists");
            });
        }


        CelestialObject entity= CelestialObject.builder()
                .objectName(name)
                .objectDesignation(designation)
                .objectType(type)
                .discoveryYear(request.getDiscoveryYear())
                .distanceFromSunAu(request.getDistanceFromSunAu())
                .objectSpeedKmS(request.getObjectSpeedKmS())
                .objectMassToEarth(request.getObjectMassToEarth())
                .shortDescription(shortDesc)
                .imageMainUrl(imageUrl)
                .build();

        CelestialObject saved = repository.save(entity);
        return toDto(saved);
    }


    @Transactional
    public Page<CelestialObjectResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDto);
    }


    public CelestialObjectResponseDTO findById(Long id) {

        CelestialObject findOne= repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found"));

        return toDto(findOne);
    }


    public void delete(Long id) {

        if(!repository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found for delete");
        }

        repository.deleteById(id);
    }

    public Page<CelestialObjectResponseDTO> findType(String objectType, Pageable pageable){

        if(objectType == null || objectType.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Type cant be null or empty");
        }
        return repository.findAllByObjectType(objectType.trim(), pageable).map(this::toDto);
    }

    public CelestialObjectResponseDTO updateObject(Long id, CelestialObjectRequestDTO updatedData){

        CelestialObject existing = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Object not found"));

        String newName = updatedData.getObjectName() != null ? updatedData.getObjectName().trim() : null ;
        String newDesignation = updatedData.getObjectDesignation() != null ? updatedData.getObjectDesignation().trim() : null;
        String newType = updatedData.getObjectType() != null ? updatedData.getObjectType().trim() : null;
        String newShortDesc = updatedData.getShortDescription() != null ? updatedData.getShortDescription().trim() : null;
        String newImageUrl = updatedData.getImageMainUrl() != null ? updatedData.getImageMainUrl().trim() : null;

        if(!Objects.equals(existing.getObjectName(), newName)){
            repository.findByObjectName(newName).ifPresent(e -> {
                if(!e.getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Name already exists");
                }
            });
        }

        if(!Objects.equals(existing.getObjectDesignation(), newDesignation)) {
            repository.findByObjectDesignation(newDesignation).ifPresent(e -> {
                if(!e.getId().equals(id)){
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Designation already exists");
                }
            });
        }

        existing.setObjectName(newName);
        existing.setObjectType(newType);
        existing.setObjectDesignation(newDesignation);
        existing.setDiscoveryYear(updatedData.getDiscoveryYear());
        existing.setDistanceFromSunAu(updatedData.getDistanceFromSunAu());
        existing.setObjectSpeedKmS(updatedData.getObjectSpeedKmS());
        existing.setObjectMassToEarth(updatedData.getObjectMassToEarth());
        existing.setShortDescription(newShortDesc);
        existing.setImageMainUrl(newImageUrl);

        CelestialObject saved = repository.save(existing);
        return toDto(saved);
    }

    private CelestialObjectResponseDTO toDto(CelestialObject c) {
        return new CelestialObjectResponseDTO(
                c.getId(),
                c.getObjectName(),
                c.getObjectType(),
                c.getObjectDesignation(),
                c.getDiscoveryYear(),
                c.getDistanceFromSunAu(),
                c.getObjectSpeedKmS(),
                c.getObjectMassToEarth(),
                c.getShortDescription(),
                c.getImageMainUrl(),
                c.getCreatedAt()
        );
    }
}
