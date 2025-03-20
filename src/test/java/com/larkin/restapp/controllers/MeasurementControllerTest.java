package com.larkin.restapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larkin.restapp.dto.MeasurementDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MeasurementControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testAddMeasurement() {
        String url = "/measurements/add";

        Random random = new Random();
        double temp = -100 + (random.nextDouble() * 200);
        temp = Math.round(temp * 100.0) / 100.0;

        Map<String, Object> jsonToAddMeasurement = new HashMap<>();
        jsonToAddMeasurement.put("temperature", temp);
        jsonToAddMeasurement.put("raining", random.nextBoolean());

        Map<String, String> sensorMap = new HashMap<>();
        sensorMap.put("name", "Mega sensor");
        jsonToAddMeasurement.put("sensor", sensorMap);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonToAddMeasurement, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testGetMeasurements() {
        String url = "/measurements";
        ResponseEntity<MeasurementDto[]> response = restTemplate.getForEntity(url, MeasurementDto[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGetRainyDaysCount() throws IOException {
        String url = "/measurements/rainyDaysCount?startDate=2025-01-01&endDate=2025-12-31";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNotNull();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(response.getBody(), Map.class);

        assertThat(jsonMap).containsKey("rainy days");
    }
}