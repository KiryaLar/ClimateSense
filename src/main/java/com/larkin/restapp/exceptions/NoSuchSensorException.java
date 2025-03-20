package com.larkin.restapp.exceptions;

public class NoSuchSensorException extends GeneralSensorException {
    public NoSuchSensorException(String message) {
        super(message);
    }
}
