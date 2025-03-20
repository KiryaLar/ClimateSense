package com.larkin.restapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
public class SensorDto {

    @NotBlank(message = "Sensor name cannot be empty")
    @Length(min = 3, max = 20, message = "Length should be from 3 to 20")
    private String name;
}
