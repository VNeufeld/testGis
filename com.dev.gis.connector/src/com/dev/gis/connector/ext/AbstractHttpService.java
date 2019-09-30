package com.dev.gis.connector.ext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



public class AbstractHttpService {

	protected  int TIMEOUT_DEFAULT_SECONDS = 30;

	protected  String server;
	protected  String path;
	protected  int port;
	protected  String operator;
	protected  String language;
	protected  String schema = "http";

	public AbstractHttpService() {
//		this.server = preferences.getString("server", "192.168.178.25");
//		this.path = preferences.getString("path",
//				"/ha_spring_joi/joi");
//		this.port = Integer.parseInt(preferences.getString("port", "7080"));
//		this.operator = preferences.getString("operator", "1");
//		this.language = preferences.getString("language", "en");
	}
	
	protected URI createUri(String path,String query) throws URISyntaxException {
		
		return new URI(schema, null, server, port,path,
				 query, null);

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
	
}
