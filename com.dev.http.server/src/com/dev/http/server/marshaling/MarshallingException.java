package com.dev.http.server.marshaling;



public class MarshallingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MarshallingException(String message, Exception e) {
		super (message, e);
	}
}