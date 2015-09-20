package com.dev.gis.connector.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public class AdacLocationHttpService   extends AbstractJoiService implements ILocationService{
	private static Logger logger = Logger.getLogger(AdacLocationHttpService.class);
	
	

	public AdacLocationHttpService(Long operator, URI uri, long language) {
		super(operator,uri, language);
		
	}

	public LocationSearchResult joiLocationSearch(String searchString, HitType filter, String country) {
		
		GisHttpClient httpClient = new GisHttpClient();;

		try {
			String query = createLocationQuery(searchString, filter, country);
			
			URI locationServiceUri = createUri(query);
			logger.info("locationServiceUri = "+locationServiceUri.toString());

			
			String response =  httpClient.sendGetRequest(locationServiceUri);
			logger.info("response = "+response);
			
			return JsonUtils.createResponseClassFromJson(response, LocationSearchResult.class);
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
		
	}


}
