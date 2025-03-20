package com.larkin.restapp.repositories;

import com.larkin.restapp.dto.MeasurementDto;
import com.larkin.restapp.models.Measurement;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    long countByRainingTrueAndMeasuredAtBefore(LocalDateTime measuredAtBefore);

    long countByRainingTrueAndMeasuredAtAfter(LocalDateTime measuredAtAfter);

    long countByRainingTrueAndMeasuredAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    long countByRainingTrue();

    List<Measurement> findAllBySensor_Name(String sensorName);
}
