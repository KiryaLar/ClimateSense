package com.larkin.restapp.controllers;

import com.larkin.restapp.dto.MeasurementDto;
import com.larkin.restapp.exceptions.GeneralSensorException;
import com.larkin.restapp.exceptions.MeasureNotAddedException;
import com.larkin.restapp.services.MeasurementService;
import com.larkin.restapp.util.SensorErrorResponse;
import com.larkin.restapp.util.ValidationUtil;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MeasurementController {
    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @PostMapping("/measurements/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDto measurementDto,
                                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = ValidationUtil.getErrorsAsString(bindingResult);
            throw new MeasureNotAddedException(errorMessage);
        }

        measurementService.addMeasurement(measurementDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/measurements")
    public List<MeasurementDto> getMeasurements() {
        return measurementService.getAllMeasurements();
    }

    @GetMapping("/measurements/rainyDaysCount")
    public ResponseEntity<Map<String, Object>> getRainyDaysCount(
            @RequestParam(name = "startDate",required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<String, Object> response = new HashMap<>();
        long count = measurementService.getRainyDaysCount(startDate, endDate);

        response.put("rainy days", count);
        response.put("startDate", startDate != null ? startDate.toString() : "all-time");
        response.put("endDate", endDate != null ? endDate.toString() : "all-time");

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(GeneralSensorException e) {
        SensorErrorResponse responseBody = new SensorErrorResponse(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
