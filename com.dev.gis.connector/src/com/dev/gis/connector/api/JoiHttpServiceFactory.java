package com.dev.gis.connector.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.dev.gis.connector.GisHttpClient;

public class JoiHttpServiceFactory {

	private static Logger logger = Logger.getLogger(JoiHttpServiceFactory.class);

	private static GisHttpClient httpClient = null;
	

	public JoiHttpServiceFactory() {
	}
	

	private static GisHttpClient getGisHttpClientInstance() {
		if ( httpClient == null)
			httpClient = new GisHttpClient();
		
		return httpClient;
		
	}
	private URI getServerURI() throws URISyntaxException {
		
		String server = SunnyModelProvider.INSTANCE.serverUrl;

		if ( server == null|| server.isEmpty() )
			server = TaskProperties.getTaskProperties().getServerProperty();
		
		URI uri = new URI(server);
		logger.info("VehicleHttpService URI : = "+uri.toString());
		return uri;
		
	}


	public ILocationService   getLocationJoiService(long operator) {
		try {
			URI uri = getServerURI();
			
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
	public AdacVehicleHttpService getAdacVehicleJoiService() {
		
		return  new  AdacVehicleHttpService(getGisHttpClientInstance());
		
	}
	

	public VehicleHttpService getVehicleJoiService() {
		
		return  new  VehicleHttpService(getGisHttpClientInstance());
		
	}
}
