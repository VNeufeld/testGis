package com.dev.gis.task.connector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.task.execution.locationSearch.impl.LocationSearchTask;

public class LocationSearchConnector  {
	final LocationSearchTask locationSearchTask;
	private static Logger logger = Logger.getLogger(LocationSearchConnector.class);

	
	public LocationSearchConnector(LocationSearchTask locationSearchTask) {
		this.locationSearchTask = locationSearchTask;
		
	}

	public LocationSearchResult joiLocationSearch(String searchString) {
		URI uri = createLocationSearchUri(searchString);
		GisHttpClient httpClient = new GisHttpClient();;

		try {
//			JsonUtils.saveDummyResultAsJson();
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			// dummy
			response = JsonUtils.createDummyResponse("DummyLocationSearchResult.json");
			
			return JsonUtils.createResponseClassFromJson(response, LocationSearchResult.class);
			
		} catch ( IOException e) {
			logger.error(e);
		}
		return null;
		
	}
	

	private URI createLocationSearchUri(String searchString) {
		URI uri = locationSearchTask.getHaJoiServiceLink();
		
		try {
			uri = new URI("http://www.google.de");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return uri;
	}

}
