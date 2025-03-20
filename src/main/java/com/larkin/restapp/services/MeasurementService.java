package com.larkin.restapp.services;

import com.larkin.restapp.dto.MeasurementDto;
import com.larkin.restapp.exceptions.NoSuchSensorException;
import com.larkin.restapp.models.Measurement;
import com.larkin.restapp.models.Sensor;
import com.larkin.restapp.repositories.MeasurementRepository;
import com.larkin.restapp.repositories.SensorRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final ModelMapper modelMapper;
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, ModelMapper modelMapper, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.modelMapper = modelMapper;
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void addMeasurement(MeasurementDto measurementDto) {
        Sensor sensor = sensorRepository.findByName(measurementDto.getSensor().getName())
                .orElseThrow(() -> new NoSuchSensorException("sensor - There is no such sensor"));
        Measurement measurement = convertToEntity(measurementDto);
        measurement.setMeasuredAt(LocalDateTime.now());
        measurement.setSensor(sensor);
        measurementRepository.save(measurement);
    }

    public List<MeasurementDto> getAllMeasurements() {
        return measurementRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public List<MeasurementDto> getAllMeasurementsBySensor(String sensorName) {
        return measurementRepository.findAllBySensor_Name(sensorName)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    private Measurement convertToEntity(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }

    private MeasurementDto convertToDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }

    public long getRainyDaysCount(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return measurementRepository.countByRainingTrueAndMeasuredAtBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
        } else if (startDate != null) {
            return measurementRepository.countByRainingTrueAndMeasuredAtAfter(startDate.atStartOfDay());
        } else if (endDate != null) {
            return measurementRepository.countByRainingTrueAndMeasuredAtBefore(endDate.atTime(23, 59, 59));
        } else {
            return measurementRepository.countByRainingTrue();
        }
    }
}
