package com.nbb.apps.carreservationv2.conections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.JsonProcessingException;

import android.content.SharedPreferences;

import com.nbb.apps.carreservationv2.base.LocationSearchResult;

public class LocationSearchService extends AbstractHttpService {
	private final String TAG = "LocationSearchService";
	private final URI uri;

	public LocationSearchService(SharedPreferences preferences,String pattern) throws URISyntaxException {
		super(preferences);
		this.uri = createLocationSearchUri(pattern);
	}

	public LocationSearchResult getLocation() throws JsonProcessingException, IOException, InterruptedException, ExecutionException, TimeoutException {
		
		DownloadJsonServiceTask task = new DownloadJsonServiceTask(uri);
		String message = task.getString();
		return createLocationSearchResult(message);

	}

	private URI createLocationSearchUri(String searchString)
			throws URISyntaxException {
		/**
		 * http://localhost:7080/ha_spring_joi/joi/location/test?operator=1&lang
		 * =1&search=m%C3%BCncnzz
		 */

		String query = "operator=" + operator + "&lang=" + language
				+ "&search=" + searchString;
		return createUri(query);
	}

	private LocationSearchResult createLocationSearchResult(String message) throws JsonProcessingException, IOException
			 {

		LocationSearchResult result = createResponseClassFromJson(message,
				LocationSearchResult.class);
		
		return result;
	}

	public URI getUri() {
		return uri;
	}

}
