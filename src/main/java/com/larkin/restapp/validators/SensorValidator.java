package com.larkin.restapp.validators;

import com.larkin.restapp.dto.SensorDto;
import com.larkin.restapp.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(SensorDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDto sensorDto = (SensorDto) target;
        var maybeSensor = sensorService.findByName(sensorDto.getName());
        if (maybeSensor.isPresent()) {
            errors.rejectValue("name", "", "Sensor with name " + sensorDto.getName() + " already exists");
        }
    }
}
