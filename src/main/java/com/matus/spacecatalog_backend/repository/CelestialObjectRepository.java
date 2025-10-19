package com.matus.spacecatalog_backend.repository;

import com.matus.spacecatalog_backend.entity.CelestialObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CelestialObjectRepository extends JpaRepository<CelestialObject, Long> {
    boolean existsByObjectName(String objectName);
    boolean existsByObjectDesignation(String objectDesignation);
    Optional<CelestialObject> findByObjectName(String objectName);
    Optional<CelestialObject> findByObjectDesignation(String objectDesignation);

    Page<CelestialObject> findAllByObjectType(String objectType, Pageable pageable);
    Page<CelestialObject> findAllByDiscoveryYearBetween(Integer from, Integer to, Pageable pageable);
    Page<CelestialObject> findAllByShortDescriptionContainingIgnoreCase(String q, Pageable pageable);
    Page<CelestialObject> findAllByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    List<CelestialObject> findTop10ByOrderByDistanceFromSunAuAsc();
    List<CelestialObject> findTop5ByObjectTypeOrderByObjectMassToEarthDesc(String objectType);

}
