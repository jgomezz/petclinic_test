package com.tecsup.petclinic.exceptions;

/**
 *
 * @author jgomezm
 *
 */
public class VisitNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public VisitNotFoundException(String message) {
		super(message);
	}
}
