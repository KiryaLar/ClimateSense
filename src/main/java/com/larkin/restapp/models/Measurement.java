package com.larkin.restapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "sensor")
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Temperature is not specified")
    @DecimalMin(value = "-100.0", message = "temperature should be above minus 100 degrees")
    @DecimalMax(value = "100.0", message = "temperature should be below 100 degrees")
    private Double temperature;

    @NotNull(message = "There's not specified about rain")
    private Boolean raining;

    @Column(name = "measured_at")
    private LocalDateTime measuredAt;

    @ManyToOne
    @NotNull(message = "Sensor is not specified")
    private Sensor sensor;

    public Measurement(Double temperature, Boolean raining) {
        this.temperature = temperature;
        this.raining = raining;
    }
}

