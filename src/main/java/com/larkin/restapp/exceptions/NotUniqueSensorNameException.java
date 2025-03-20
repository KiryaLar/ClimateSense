package com.larkin.restapp.exceptions;

public class NotUniqueSensorNameException extends GeneralSensorException {
    public NotUniqueSensorNameException(String message) {
        super(message);
    }
}
