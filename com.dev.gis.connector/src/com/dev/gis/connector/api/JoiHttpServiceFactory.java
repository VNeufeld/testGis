package com.dev.gis.connector.api;

import java.net.URI;
import java.net.URISyntaxException;

import com.dev.gis.connector.GisHttpClient;

public class JoiHttpServiceFactory {
	
	private static GisHttpClient httpClient = null;
	
	private static GisHttpClient getGisHttpClientInstance() {
		if ( httpClient == null)
			httpClient = new GisHttpClient();
		
		return httpClient;
		
	}


	public LocationHttpService   getLocationJoiService() {
		try {
			Long operator = TaskProperties.getTaskProperties().getOperator();
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty());
			
			int language = TaskProperties.getTaskProperties().getLanguage();
	
			LocationHttpService locationHttpService = new LocationHttpService(operator,uri, language);
			
			return locationHttpService;
		}
		catch(URISyntaxException ex) {
			
		}
		return null;

	}

	public VehicleHttpService getVehicleJoiService() {
		
		VehicleHttpService  service = new  VehicleHttpService(getGisHttpClientInstance());
		
		
		return service;
	}
}
