package com.dev.gis.connector.sunny;


public class BadValueException extends RuntimeException {

	private static final long serialVersionUID = 4280699799871268289L;

		
	public BadValueException(String message) {
		super (message);
	}
}