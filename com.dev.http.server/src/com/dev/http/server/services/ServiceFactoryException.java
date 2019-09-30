package com.dev.http.server.services;

public class ServiceFactoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceFactoryException(String message, Exception e) {
		super (message, e);
	}

}
