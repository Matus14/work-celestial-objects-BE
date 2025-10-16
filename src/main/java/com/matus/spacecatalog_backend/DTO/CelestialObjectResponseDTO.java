package com.matus.spacecatalog_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class CelestialObjectResponseDTO {

    private Long id;
    private String objectName;
    private String objectType;
    private String objectDesignation;
    private Integer discoveryYear;
    private Double distanceFromSunAu;
    private Double objectSpeedKmS;
    private Double objectMassToEarth;
    private String shortDescription;
    private String imageMainUrl;
    private LocalDateTime createdAt;

}
