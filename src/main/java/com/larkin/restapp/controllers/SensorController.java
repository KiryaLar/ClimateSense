package com.larkin.restapp.controllers;

import com.larkin.restapp.dto.MeasurementDto;
import com.larkin.restapp.dto.SensorDto;
import com.larkin.restapp.exceptions.*;
import com.larkin.restapp.services.MeasurementService;
import com.larkin.restapp.services.SensorService;
import com.larkin.restapp.util.SensorErrorResponse;
import com.larkin.restapp.util.ValidationUtil;
import com.larkin.restapp.validators.SensorValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class SensorController {

    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final MeasurementService measurementService;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, MeasurementService measurementService) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.measurementService = measurementService;
    }

    @PostMapping("/sensors/registration")
    public ResponseEntity<HttpStatus> registerSensor(@RequestBody @Valid SensorDto sensorDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ValidationUtil.getErrorsAsString(bindingResult);
            throw new SensorNotCreatedException(errorMessage);
        }

        sensorValidator.validate(sensorDto, bindingResult);

        if (bindingResult.hasErrors()) {
            var errorMessage = bindingResult.getFieldError().getDefaultMessage();
            throw new NotUniqueSensorNameException(errorMessage);
        }

        sensorService.registerSensor(sensorDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/sensor/measurements")
    public List<MeasurementDto> getMeasurementsBySensor(@RequestParam(name = "name") String sensorName) {
        return measurementService.getAllMeasurementsBySensor(sensorName);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(GeneralSensorException e) {
        SensorErrorResponse responseBody = new SensorErrorResponse(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
