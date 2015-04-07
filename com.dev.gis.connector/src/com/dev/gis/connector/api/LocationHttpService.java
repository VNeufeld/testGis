package com.dev.gis.connector.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.LocationSearchResult;

public class LocationHttpService {
	private static Logger logger = Logger.getLogger(LocationHttpService.class);
	
	private final URI uri;
	private final Long operator;
	private final long language;
	

	public LocationHttpService(Long operator, URI uri, int language) {
		this.uri = uri;
		this.operator = operator;
		this.language = language;
		
	}

	public LocationSearchResult joiLocationSearch(String searchString, HitType filter) {
		
		GisHttpClient httpClient = new GisHttpClient();;

		try {
			
			String query = "operator=" + operator + "&lang=" + language
					+ "&search=" + searchString;
			if ( filter != null && filter != HitType.UNINITIALIZED)
				query = query + "&filter="+filter.getOrdinal();
			
			
			URI locationServiceUri = createUri(query);
			logger.info("locationServiceUri = "+locationServiceUri.toString());

			
//			JsonUtils.saveDummyResultAsJson();
			//locationServiceUri = createLocationSearchUri("");
			String response =  httpClient.sendGetRequest(locationServiceUri);
			logger.info("response = "+response);
			
			// dummy
			//response = JsonUtils.createDummyResponse("DummyLocationSearchResult.json");
			
			return JsonUtils.createResponseClassFromJson(response, LocationSearchResult.class);
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
		
	}
	

	private URI createLocationSearchUri(String searchString) {
		URI uri = null; //locationSearchTask.getHaJoiServiceLink();
		
		try {
			uri = new URI("http://www.google.de");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
	}

	protected URI createUri(String query) throws URISyntaxException {
		String path = uri.getPath() + "/location";;

		return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(),path, query, null);

	}

}
