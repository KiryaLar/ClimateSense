package com.larkin.restapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larkin.restapp.dto.MeasurementDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SensorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testRegisterSensor() {
        String url = "/sensors/registration";

        Map<String, String> jsonToRegisterSensor = new HashMap<>();
        jsonToRegisterSensor.put("name", "Ultra sensor");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToRegisterSensor, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetMeasurementsBySensor() {
        String url = "/sensor/measurements?name=Mega sensor";

        ResponseEntity<MeasurementDto[]> response = restTemplate.getForEntity(url, MeasurementDto[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        List<MeasurementDto> measurements = List.of(response.getBody());
        assertThat(measurements).isNotEmpty();
    }
}