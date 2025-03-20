package com.larkin.restapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class SensorErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
