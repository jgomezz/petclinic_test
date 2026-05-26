package com.tecsup.petclinic.exceptions;
/**
 * Excepción personalizada lanzada cuando no se encuentra un dueño (Owner)
 * en la base de datos en operaciones de consulta o eliminación.
 */
public class OwnerNotFoundException extends RuntimeException {
    /**
     * Constructor por defecto.
     */
    public OwnerNotFoundException() {
        super("No se encontrarón dueño(s) con los criterios específicados");
    }

    /**
     * Constructor con mensaje personalizado.
     * @param message Mensaje de detalle de la excepción
     */
    public OwnerNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa original.
     * @param message Mensaje de detalle de la excepción
     * @param cause Excepción que causó este error
     */
    public OwnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}