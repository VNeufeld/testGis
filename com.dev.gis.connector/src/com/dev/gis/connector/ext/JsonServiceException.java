package com.dev.gis.connector.ext;

public class JsonServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonServiceException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

}
