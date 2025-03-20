package com.larkin.restapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString(exclude = "measurements")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @NotBlank(message = "Sensor name cannot be empty")
    @Length(min = 3, max = 20, message = "Length should be from 3 to 20")
    private String name;

    @OneToMany(mappedBy = "sensor")
    @JsonIgnore
    List<Measurement> measurements;

    public Sensor(String name) {
        this.name = name;
    }
}
