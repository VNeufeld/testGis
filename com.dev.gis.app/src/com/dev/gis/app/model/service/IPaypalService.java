package com.dev.gis.app.model.service;

import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;


public interface IPaypalService {
	
	PaypalSetCheckoutResponse  retriveUri(String bookingRequestId);

	PaypalDoCheckoutResponse getPaypalResult(String bookingRequestId,String token);

}
