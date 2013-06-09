package com.nbb.apps.carreservationv2.conections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.codehaus.jackson.JsonProcessingException;

import android.content.SharedPreferences;

import com.nbb.apps.carreservationv2.CarReservationApplication;
import com.nbb.apps.carreservationv2.base.Offer;

public class GetOfferService extends AbstractHttpService {

	private final String TAG = "GetOfferService";
	private final URI uri;

	public GetOfferService(SharedPreferences preferences)
			throws URISyntaxException {
		super(preferences);
		this.uri = createLocationSearchUri();
	}

	private URI createLocationSearchUri() throws URISyntaxException {
		CarReservationApplication carReservationApplication = new CarReservationApplication()
				.getInstance();
		String id = carReservationApplication.getSelectedOffer().getId()
				.toString();
		///offer/{offer}
		
		String offerPath = path + "/request/offer/"+id;
		
		URI uri = new URI(schema, null, server, port,offerPath,
				 null, null);
		
		return uri;
	}

	public Offer getOffer() throws JsonProcessingException, IOException,
			InterruptedException, ExecutionException, TimeoutException {
		DownloadJsonServiceTask task = new DownloadJsonServiceTask(uri);
		String message = task.getString();
		return createOfferResult(message);

	}

	private Offer createOfferResult(String message)
			throws JsonProcessingException, IOException {

		Offer result = createResponseClassFromJson(message,
				Offer.class);

		return result;
	}

}
