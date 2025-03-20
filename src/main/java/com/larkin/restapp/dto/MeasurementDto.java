package com.larkin.restapp.dto;

import com.larkin.restapp.models.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDto {

    @NotNull(message = "Temperature is not specified")
    @DecimalMin(value = "-100.0", message = "temperature should be above minus 100 degrees")
    @DecimalMax(value = "100.0", message = "temperature should be below 100 degrees")
    private Double temperature;

    @NotNull(message = "There's not specified about rain")
    private Boolean raining;

    @NotNull(message = "Sensor is not specified")
    private Sensor sensor;
}
