package com.dev.gis.task.execution.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.bpcs.mdcars.protocol.HitType;
import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;

public class LocationSearchConnector  {
	
	protected  String server;
	protected  String path;
	protected  int port = 8080;
	protected  String operator = "1";
	protected  String language = "1";
	protected String schema = "http";
	LocationSearchTask locationSearchTask;
	private static Logger logger = Logger.getLogger(LocationSearchConnector.class);

	
	public LocationSearchConnector(LocationSearchTask locationSearchTask) {
		this.locationSearchTask = locationSearchTask;
		
	}
	
	public LocationSearchConnector() {
		try {
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty());
			
			server = uri.getHost();
			path = uri.getPath() + "/location";;
			
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	public LocationSearchResult joiLocationSearch(String searchString, HitType filter, long operator, long language) {
		

		GisHttpClient httpClient = new GisHttpClient();;

		try {
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty());
			
			String query = "operator=" + operator + "&lang=" + language
					+ "&search=" + searchString;
			
			
			URI locationServiceUri = createUri(query);
			logger.info("locationServiceUri = "+locationServiceUri.toString());

			
//			JsonUtils.saveDummyResultAsJson();
			locationServiceUri = createLocationSearchUri("");
			String response =  httpClient.sendGetRequest(locationServiceUri);
			logger.info("response = "+response);
			
			// dummy
			response = JsonUtils.createDummyResponse("DummyLocationSearchResult.json");
			
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
		
		return new URI(schema, null, server, port,path,
				 query, null);

	}

}
