package com.dev.gis.app.model.service.adac;

import com.dev.gis.app.model.service.IPaypalService;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;

public class PaypalService implements IPaypalService {
	

	private final AdacVehicleHttpService service;

	public PaypalService() {
		super();
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();
		service = serviceFactory.getAdacVehicleJoiService();
		
	}
	
	@Override
	public PaypalSetCheckoutResponse retriveUri(String bookingRequestId) {
		PaypalSetCheckoutResponse response = service.getPaypalUrl(bookingRequestId);
		return response;
		
	}

	@Override
	public PaypalDoCheckoutResponse getPaypalResult(String bookingRequestId,String token) {
		PaypalDoCheckoutResponse response = service
				.getPaypalResult(bookingRequestId,token);
		return response;
	}

}
