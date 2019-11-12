package com.nbb.apps.carreservationv2.conections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.JsonProcessingException;

import android.content.SharedPreferences;
import android.util.Log;

import com.nbb.apps.carreservationv2.CarReservationApplication;
import com.nbb.apps.carreservationv2.base.Hit;
import com.nbb.apps.carreservationv2.base.VehicleRequest;
import com.nbb.apps.carreservationv2.base.VehicleResponse;

public class SearchOfferService extends AbstractHttpService {
	private final String TAG = "SearchOfferService";
	private final URI uri;
	private final String jsonRequestBody;

	public SearchOfferService(SharedPreferences preferences) throws URISyntaxException, JsonProcessingException, IOException {
		super(preferences);
		this.uri = createGetVehicleUri();
		this.jsonRequestBody = createRequest();
		
	}

	public VehicleResponse getOffers() throws JsonProcessingException, IOException, InterruptedException, ExecutionException, TimeoutException {
		
		GetOfferServiceTask task = new GetOfferServiceTask(uri, jsonRequestBody);
		String message = task.getResult();
		return createResult(message);

	}

	private String createRequest() throws JsonProcessingException, IOException {
		
		CarReservationApplication carReservationApplication = new CarReservationApplication().getInstance();
		
		Hit location = carReservationApplication.getSelectedHit();
		Calendar puDate = carReservationApplication.getPuDate();

		VehicleRequest request = new TestUtil().createVehicleRequest(location,puDate,super.language,super.operator);
		
		return convertRequestToJsonString(request);
	}

	private VehicleResponse createResult(String message)
			throws JsonProcessingException, IOException {
		VehicleResponse r = createResponseClassFromJson(message,
				VehicleResponse.class);

		return r;
	}

	private URI createGetVehicleUri() throws URISyntaxException {
		/**
		 * http://localhost:7080/ha_spring_joi/joi/location/test?operator=1&lang
		 * =1&search=m%C3%BCncnzz
		 */

		String query = "operator=" + operator + "&lang=" + language;
		return createVehicleUri(query);
	}

	public URI getUri() {
		return uri;
	}

	public String getJsonRequestBody() {
		return jsonRequestBody;
	}

}
