package com.dev.gis.task.execution.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.joi.protocol.Administration;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class JoiVehicleConnector {
	private static Logger logger = Logger.getLogger(JoiVehicleConnector.class);

	public static VehicleResponse getOffers(VehicleRequest vehicleRequest) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			URI uri = new URI("http://localhost:8080/joi/vehicleRequest?pageSize=20");
			Administration admin = new Administration();
			
			admin.setLanguage("de-DE");
			admin.setOperator(1);
			admin.setSalesChannel(7);
			admin.setCalledFrom(9);
			admin.setBroker(false);

			vehicleRequest.setAdministration(admin);
			

			String request = JsonUtils.convertRequestToJsonString(vehicleRequest);
			logger.info("request = "+request);
			
//			String request = JsonUtils.createDummyResponse("DummyJoiVehicleRequest.json");
			
			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("response = "+response);
			
			VehicleResponse vh = JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			

			return vh;
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;

	}

	public static VehicleResponse getOffersDummy() {

		try {
			
			String response = JsonUtils.createDummyResponse("DummyJoiVehicleResponse.json");
			
			logger.info("response = "+response);
			
			VehicleResponse vh = JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			

			return vh;
			
		} catch ( IOException e) {
			logger.error(e);
		}
		return null;

	}
	
}
