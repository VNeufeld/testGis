package com.dev.gis.task.execution.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.joi.protocol.Address;
import com.dev.gis.connector.joi.protocol.Administration;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Payment;
import com.dev.gis.connector.joi.protocol.PaymentType;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class JoiVehicleConnector {
	private static Logger logger = Logger.getLogger(JoiVehicleConnector.class);

	public static VehicleResponse getOffers(VehicleRequest vehicleRequest) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+TaskProperties.VEHICLE_REQUEST_PARAM);
			
			Administration admin = createAdministrator();

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

	private static Administration createAdministrator() {
		Administration admin = new Administration();
		
		admin.setLanguage(TaskProperties.LANGUAGE_CODE);
		admin.setOperator(TaskProperties.getTaskProperties().getOperator());
		admin.setSalesChannel(TaskProperties.SALES_CHANNEL);
		admin.setCalledFrom(TaskProperties.CALLED_FROM);
		admin.setBroker(false);
		return admin;
	}
	
	public static ExtraResponse getExtras(Offer offer) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			String link = offer.getBookLink().toString();
			int pos = link.indexOf("/vehicleRe");
			link = link.substring(pos);
			link = link.replace("/book","/extras");
			//URI uri = new URI("http://localhost:8080/joi/vehicleRequest?pageSize=200");
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("GetExtra Request = "+uri);
			
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			ExtraResponse extraResponse = JsonUtils.createResponseClassFromJson(response, ExtraResponse.class);
			

			return extraResponse;
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;

	}
	public static PaypalSetCheckoutResponse getPaypalUrl(Offer offer, String bookingRequestId) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			String link = "/booking/"+bookingRequestId+"/payPaypal";
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("GetPaypal URL Request = "+uri);
			
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			PaypalSetCheckoutResponse paypalResponse = JsonUtils.createResponseClassFromJson(response, PaypalSetCheckoutResponse.class);

			return paypalResponse;
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
		
	}
	
	
	public static BookingResponse verifyOffers(Offer offer, List<Extra> selectedExtras) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			
			BookingRequest bookingRequest = new BookingRequest();
			String link = offer.getBookLink().toString();
			int pos = link.indexOf("/vehicleRe");
			link = link.substring(pos);
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("Verify Request URI = "+uri);

			Customer customer = new Customer();
			Person person = new Person();
			person.setName("Meier");
			person.setFirstName("Anton");
			customer.setPerson(person);
			Address address = new Address();
			address.setCity("München");
			address.setStreet("Street");
			address.setZip("81543");
			address.setCountry("DE");
			address.setCountryId(Long.valueOf(49));
			customer.setAddress(address);
			
			bookingRequest.setCustomer(customer);
			
			Person driver = new Person();
			driver.setName("Meier");
			driver.setFirstName("Anton");
			
			bookingRequest.setDriver(driver);
			Payment payment = new Payment();
			payment.setPaymentType("1");
			
			//payment.setPaymentType(PaymentType.PAYPAL_PAYMENT);
			
			bookingRequest.setAcceptedAvailability("13");
			bookingRequest.setFlightNo("LH4711");
			bookingRequest.setTransferType("1");
			bookingRequest.setPriceLimit(new MoneyAmount("1000, 00","EUR"));
			//bookingRequest.setPayment(payment);
			
			bookingRequest.setExtras(selectedExtras);

			String request = JsonUtils.convertRequestToJsonString(bookingRequest);
			logger.info("Verify Request = "+request);
			
			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("Verify Response = "+response);
			
			BookingResponse vh = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);
			

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
