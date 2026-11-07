package com.tecsup.petclinic.exceptions;

public class DuplicateVetSpecialtyException extends Exception {

    private static final long serialVersionUID = 1L;

    public DuplicateVetSpecialtyException(String message) {
        super(message);
    }
}
