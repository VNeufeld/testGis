package com.dev.gis.connector;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.utils.XmlUtils;

public class LocationSearchConnector extends HttpConnectorImpl {
	final LocationSearchTask locationSearchTask;
	
	public LocationSearchConnector(LocationSearchTask locationSearchTask) {
		this.locationSearchTask = locationSearchTask;
		
	}

	@Override
	public LocationSearchResult joiLocationSearch(String searchString) {
		URI uri = createLocationSearchUri(searchString);
		
		try {
			JsonUtils.saveDummyResultAsJson();
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			// dummy
			response = createDummyResponse("DummyLocationSearchResult.json");

			String RESOURCE_FOLDER= "/resource/json";
			String resource = RESOURCE_FOLDER+ "/DummyLocationSearchResult.json";

			XmlUtils.readResource(Activator.getDefault().getBundle().getResource(resource));
			
			return JsonUtils.createResponseClassFromJson(response, LocationSearchResult.class);
			
		} catch ( IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	private String createDummyResponse(String file) {
		String RESOURCE_FOLDER= "/resource/json";
		String resource = new JsonUtils().readResource(RESOURCE_FOLDER+ "/"+file);
		
		return resource;
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
