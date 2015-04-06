package com.dev.gis.connector.api;

import java.net.URI;
import java.net.URISyntaxException;

public class JoiHttpServiceFactory {

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
		
		VehicleHttpService  service = new  VehicleHttpService();
		
		
		return service;
	}
}
