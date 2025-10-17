package com.matus.spacecatalog_backend.controller;


import com.matus.spacecatalog_backend.DTO.CelestialObjectRequestDTO;
import com.matus.spacecatalog_backend.DTO.CelestialObjectResponseDTO;
import com.matus.spacecatalog_backend.entity.CelestialObject;
import com.matus.spacecatalog_backend.service.CelestialObjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/celestialobjects")
public class CelestialObjectController {

    @Autowired
    private CelestialObjectService service;

    @PostMapping
    public CelestialObjectResponseDTO create(@Valid @RequestBody CelestialObjectRequestDTO request){
        return service.create(request);
    }


    @GetMapping
    public Page<CelestialObjectResponseDTO> findAll(Pageable pageable){
        return service.findAll(pageable);
    }

    @GetMapping("/type")
    public Page<CelestialObjectResponseDTO> findByType(@RequestParam String objectType, Pageable pageable){
        return service.findType(objectType, pageable);
    }

    @GetMapping("/{id}")
    public CelestialObjectResponseDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @PutMapping("/{id}")
    public CelestialObjectResponseDTO update(@PathVariable Long id,
                                                   @Valid @RequestBody CelestialObjectRequestDTO updatedData){
        return service.updateObject(id,updatedData);
    }

}
