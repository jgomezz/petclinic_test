package com.tecsup.petclinic.exceptions;

public class SpecialtyNotAssignedException extends Exception {

    private static final long serialVersionUID = 1L;

    public SpecialtyNotAssignedException(String message) {
        super(message);
    }
}
