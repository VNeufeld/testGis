package com.dev.gis.connector.ext;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String detailMessage) {
		super(detailMessage);
	}

}
