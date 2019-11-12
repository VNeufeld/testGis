package com.nbb.apps.carreservationv2.conections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class AbstractHttpService {

	protected final String server;
	protected final String path;
	protected final int port;
	protected final String operator;
	protected final String language;
	protected final String schema = "http";

	public AbstractHttpService(SharedPreferences preferences) {
		this.server = preferences.getString("server", "192.168.178.25");
		this.path = preferences.getString("path",
				"/ha_spring_joi/joi");
		this.port = Integer.parseInt(preferences.getString("port", "7080"));
		this.operator = preferences.getString("operator", "1");
		this.language = preferences.getString("language", "en");
	}

	protected URI createUri(String query) throws URISyntaxException {

		String locationPath = path + "/location";
		URI uri = new URI(schema, null, server, port, locationPath, query, null);

		return uri;
	}

	protected URI createVehicleUri(String query) throws URISyntaxException {

		String vehiclePath = path + "/request";
		
		URI uri = new URI(schema, null, server, port,vehiclePath,
				 query, null);

		return uri;
	}

	protected static <T> T createResponseClassFromJson(String jsonString,
			Class<T> claszz) throws JsonProcessingException, IOException   {

		ObjectMapper mapper = new ObjectMapper();
		T result = mapper.readValue(jsonString, claszz);
		return result;
	}

	protected String convertRequestToJsonString(Object request) 
			throws JsonProcessingException, IOException {

		ObjectMapper mapper = new ObjectMapper();

		String result = mapper.writeValueAsString(request);

		return result;
	}

	class GetOfferServiceTask extends AsyncTask<URI, Void, String> {
		private final String TAG = "GetOfferServiceTask";
		final URI uri;
		final String request;

		public GetOfferServiceTask(URI uri, String jsonRequest) {
			this.uri = uri;
			this.request = jsonRequest;
		}

		@Override
		protected String doInBackground(URI... params) {
			String message = null;
			GisHttpClient httpClient = new GisHttpClient();
			try {
				message = httpClient.sendPostRequest(uri, request);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;
		}

		public String getResult() throws InterruptedException,
				ExecutionException, TimeoutException {
			Log.i(TAG, "execute service to uri : " + this.uri);
			execute(this.uri);
			String result = get(600, TimeUnit.SECONDS);
			Log.i(TAG, "result of service : " + result);
			return result;
		}

	}

	class DownloadJsonServiceTask extends AsyncTask<URI, Void, String> {
		private final String TAG = "DownloadJsonServiceTask";

		final URI uri;

		public DownloadJsonServiceTask(URI uri) {
			this.uri = uri;
		}

		public String getString() throws InterruptedException, ExecutionException, TimeoutException {
			Log.i(TAG, "execute service to uri : " + this.uri);
			execute(this.uri);
			String result = get(30, TimeUnit.SECONDS);
			Log.i(TAG, "result of service : " + result);
			return result;
		}

		private String loadStringFromNetwork(URI url) {
			String message = null;
			GisHttpClient httpClient = new GisHttpClient();
			try {
				message = httpClient.sendGetRequest(uri);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return message;
		}

		@Override
		protected String doInBackground(URI... params) {
			return loadStringFromNetwork(params[0]);
		}

	}

}
