//package com.larkin.restapp.validators;
//
//import com.larkin.restapp.dto.MeasurementDto;
//import com.larkin.restapp.models.Measurement;
//import com.larkin.restapp.models.Sensor;
//import com.larkin.restapp.services.MeasurementService;
//import com.larkin.restapp.services.SensorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.validation.Errors;
//import org.springframework.validation.Validator;
//
//import java.util.Optional;
//
//@Component
//public class MeasurementValidator implements Validator {
//
//    private final SensorService sensorService;
//
//    @Autowired
//    public MeasurementValidator(SensorService sensorService) {
//        this.sensorService = sensorService;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return clazz.equals(MeasurementDto.class);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        MeasurementDto measurementDto = (MeasurementDto) target;
//        var maybeSensor = sensorService.findByName(measurementDto.getSensor().getName());
//        if (maybeSensor.isEmpty()) {
//            errors.rejectValue("sensor", "", "There is no such sensor");
//        }
//    }
//}
