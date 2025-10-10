package com.matus.spacecatalog_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "celestial_object")
public class PlanetaryObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String objectName;

    @Column(nullable = false)
    private String objectType;

    @Column(nullable = false)
    private String objectDesignation;

    @Column(nullable = false)
    private Integer discoveryYear;

    @Column(nullable = false)
    private Double distanceFromSunAu;

    @Column(nullable = false)
    private Double objectSpeedKmS;

    @Column(nullable = false)
    private Double objectMassToEarth;

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private String imageMainUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
