package com.dev.http.server.services;

import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

public class ResponseValidator {
	public static final String GET_COUNTRIES_BY_OPERATOR_ID = "11001";
	
	private static Logger LOG = Logger.getLogger(ResponseValidator.class);
	
	private static Map<String, String> demandedObjectResponseXsdFiles;

	static{
		demandedObjectResponseXsdFiles = new Hashtable<String, String>();
		demandedObjectResponseXsdFiles.put(GET_COUNTRIES_BY_OPERATOR_ID, "GetCountriesByOperatorResponse/GetCountriesByOperatorResponse.xsd");
	};

	public static void validateResponseIfRequested(String xmlResponse) {
		
	
	}
	


}
