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
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.PhoneNumber;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class JoiVehicleConnector {
	private static Logger logger = Logger.getLogger(JoiVehicleConnector.class);
	
	private static String varPayerId = "G53SL5V9APQV2";

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

	public static PaypalDoCheckoutResponse getPaypalResult(Offer offer, String bookingRequestId, String token) {
		GisHttpClient httpClient = new GisHttpClient();

		//joi/booking/${varBookingCacheId}/getPaypalResult?payerID=${varPayerId}&token=${varToken}
		try {
			String link = "/booking/"+bookingRequestId+"/getPaypalResult";
			link = link + "?payerID="+varPayerId;
			link = link + "&token="+token;
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("GetPaypal URL Request = "+uri);
			
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			PaypalDoCheckoutResponse paypalResult = JsonUtils.createResponseClassFromJson(response, PaypalDoCheckoutResponse.class);

			return paypalResult;
			
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

			Customer customer = createCustomer();
			
			bookingRequest.setCustomer(customer);
			
			Person driver = createDriver();
			
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

	private static Person createDriver() {
		Person driver = new Person();
		driver.setName("Meier");
		driver.setFirstName("Anton");
		
		return driver;
	}

	private static Customer createCustomer() {
		Customer customer = new Customer();
		Person person = createDriver();
		customer.setPerson(person);
		Address address = new Address();
		address.setCity("M�nchen");
		address.setStreet("Street");
		address.setZip("81543");
		address.setCountry("DE");
		address.setCountryId(Long.valueOf(49));
		customer.setAddress(address);
		customer.setExternalCustomerNo("111111111");
		customer.setEMail("John-Appleseed@mac.com");
		PhoneNumber ph = new PhoneNumber();
		ph.setPrefix("");
		ph.setExtension("8885555512");
		customer.setPhone(ph);
		return customer;
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

	public static BookingResponse bookOffers(Offer selectedOffer,String bookingRequestId,
			List<Extra> selectedExtras) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			// joi/booking/${varBookingCacheId}/book?validateOnly=false
			BookingRequest bookingRequest = new BookingRequest();
			
			String link = "/booking/"+bookingRequestId+"/book?validateOnly=false";
			
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("Book Request URI = "+uri);

			Customer customer = createCustomer();
			
			bookingRequest.setCustomer(customer);
			
			Person driver = createDriver();
			
			bookingRequest.setDriver(driver);
			
			
			Payment payment = new Payment();
			payment.setPaymentType("8");   // Paypal
			bookingRequest.setPayment(payment);
			
			bookingRequest.setAcceptedAvailability("13");
			bookingRequest.setFlightNo("LH4711");
			bookingRequest.setTransferType("1");
			bookingRequest.setPriceLimit(new MoneyAmount("1000, 00","EUR"));
			
			bookingRequest.setExtras(selectedExtras);

			String request = JsonUtils.convertRequestToJsonString(bookingRequest);
			logger.info("book Request = "+request);
			
			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("book Response = "+response);
			
			BookingResponse vh = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);
			

			return vh;
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}

	public static String recalculate(Offer selectedOffer, TravelInformation travelInformation) {
		GisHttpClient httpClient = new GisHttpClient();
		
		try {
			String link = selectedOffer.getBookLink().toString();
			int pos = link.indexOf("/vehicleRe");
			link = link.substring(pos);
			pos = link.indexOf("/offer");
			link = link.substring(0,pos);
			link = link + "/recalculate";
			//URI uri = new URI("https://avm-mobile.adac.de/test/vehicleRequest/1162967122/vehicle/77345424/recalculate");
			// http://localhost:8080/joi/vehicleRequest/1127497613/vehicle/899766814/offer/fc3763ab-b892-4f60-b3f7-fbe72fe91525/recalculate
			
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("recalculate Request = "+uri);
			
			
			String request = JsonUtils.convertRequestToJsonString(travelInformation);
			logger.info("request = "+request);
			
			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("response = "+response);
			if ( response == null)
				response = " no cars found ";
			
			return response;
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}

	public static String getPickupStations(Offer selectedOffer) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			String link = selectedOffer.getBookLink().toString();
			int pos = link.indexOf("/vehicleRe");
			link = link.substring(pos);
			pos = link.indexOf("/offer");
			link = link.substring(0,pos);
			link = link + "/pickUp";

			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("getPickupStations Request = "+uri);
			
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);

			return response;
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}
	
}
