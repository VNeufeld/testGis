package com.dev.gis.connector.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.dev.gis.connector.GisHttpClient;

public class JoiHttpServiceFactory {

	private static Logger logger = Logger.getLogger(JoiHttpServiceFactory.class);

	private static GisHttpClient httpClient = null;
	
	private static GisHttpClient getGisHttpClientInstance() {
		if ( httpClient == null)
			httpClient = new GisHttpClient();
		
		return httpClient;
		
	}


	public ILocationService   getLocationJoiService() {
		try {
			Long operator = TaskProperties.getTaskProperties().getOperator();
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty());
			int language = TaskProperties.getTaskProperties().getLanguage()+1;
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			ILocationService locationHttpService = null;
			if ( dummy )
			{
				locationHttpService = new DummyLocationService(operator, uri, language); 
			}

			else
				locationHttpService = new LocationHttpService(operator,uri, language);
			
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
