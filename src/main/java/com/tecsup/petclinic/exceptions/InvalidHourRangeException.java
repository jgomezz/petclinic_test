package com.tecsup.petclinic.exceptions;

public class InvalidHourRangeException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidHourRangeException(String message) {
        super(message);
    }
}
