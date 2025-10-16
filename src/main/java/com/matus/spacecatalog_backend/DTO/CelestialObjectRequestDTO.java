package com.matus.spacecatalog_backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class CelestialObjectRequestDTO {

    @NotBlank(message = "Object name is required")
    private String objectName;

    @NotBlank(message = "Object type is required")
    private String objectType;

    @NotBlank(message = "Object designation is required")
    private String objectDesignation;

    @NotNull(message = "Object discovery year is required")
    @Min(value = 1200, message = "Discovery year must be after year 1200")
    @Max(value = 2030, message = "Discovery year must be before year 2030")
    private Integer discoveryYear;

    @NotNull(message = "Object distance from the Sun (Au) is required")
    private Double distanceFromSunAu;

    @NotNull(message = "Object speed (km/s) is required")
    private Double objectSpeedKmS;

    @NotNull(message = "Object mass relative to the Earth is required")
    private Double objectMassToEarth;

    @NotBlank(message = "Object description is required")
    @Size(max = 300, message = "Description must not exceed 300 cahracters")
    private String shortDescription;

    @NotBlank(message = "Image URL is required")
    private String imageMainUrl;


}
