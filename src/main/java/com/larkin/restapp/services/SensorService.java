package com.larkin.restapp.services;

import com.larkin.restapp.dto.SensorDto;
import com.larkin.restapp.models.Sensor;
import com.larkin.restapp.repositories.SensorRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void registerSensor(SensorDto sensorDto) {
        var sensor = convertToEntity(sensorDto);
        sensorRepository.save(sensor);
    }

    private Sensor convertToEntity(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }
}
