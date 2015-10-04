package com.dev.gis.connector.sunny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error extends BasicProtocol {

	public final static int ERROR_TYPE_JOI_ERROR = 5;
	
	public final static int ERROR_NO_VEHICLE_NOT_FOUND = 25001;
	public final static String ERROR_TEXT_VEHICLE_NOT_FOUND = "Fahrzeug nicht bekannt";
	
	public final static int ERROR_NO_OFFER_NOT_FOUND = 25002;
	public final static String ERROR_TEXT_OFFER_NOT_FOUND = "Angebot nicht bekannt";

	public final static int ERROR_NO_EXTRA_NOT_FOUND = 25002;
	public final static String ERROR_TEXT_EXTRA_NOT_FOUND = "Extra nicht bekannt";

	public final static int ERROR_NO_REQUEST_NOT_FOUND = 25003;
	public final static String ERROR_TEXT_REQUEST_NOT_FOUND = "Request nicht bekannt";

	public static final int ERROR_WRONG_SIZE = 25004;
	public static final String ERROR_TEXT_WRONG_SIZE = "Falsche Größe der Ergebnismenge";

	public static final int ERROR_ABSTRACT_LOCATION_NO_POSSIBLE = 25005;
	public static final String ERROR_TEXT_ABSTRACT_LOCATION_NO_POSSIBLE = "Nur zu konkreten Stationen kann die Öffnungszeit angegeben werden";
	
	private int errorType;

	private String errorText;

	private int errorNumber;
	
	public Error() {
		// TODO Auto-generated constructor stub
	}
	
	public Error (int errorNumber, int errorType, String errorText) {
		this.errorNumber = errorNumber;
		this.errorType = errorType;
		this.errorText = errorText;
	}

	
	
	public int getErrorNumber() {
		return errorNumber;
	}

	public String getErrorText() {
		return errorText;
	}

	public int getErrorType() {
		return errorType;
	}

	public void setErrorNumber(int errorNumber) {
		this.errorNumber = errorNumber;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
}
