package com.dev.gis.connector.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;

import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.joi.protocol.Address;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.PhoneNumber;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleRequestFilter;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class AdacVehicleHttpService {
	private static Logger logger = Logger.getLogger(AdacVehicleHttpService.class);

	
	public static String SUNNY_VEHICLE_REQUEST_PARAM = "/request?ratingView=1&sort=asc&pageSize=";
	public static String SUNNY_NEXT_PAGE_REQUEST_PARAM = "/request/browsepage?page=";
	public static String SUNNY_BROWSE_REQUEST_PARAM = "/request/browse?";
	public static String SUNNY_GET_PICKUP_STATIONS = "/request/pickupstations?";
	public static String SUNNY_GET_DROPOFF_STATIONS = "/request/dropoffstations?";
	public static String SUNNY_PAYPAMENT_PAYPAGE = "/payment/paypage";
	public static String SUNNY_PAYPAMENT_VERIFY = "/payment/verify";
	public static String SUNNY_BOOKING_DRIVER = "/booking/driver/put";
	public static String SUNNY_PUT_EXTRAS = "/request/extras/put";
	public static String SUNNY_BOOKING_CUSTOMER = "/booking/customer/put";

	public static String VEHICLE_REQUEST_PARAM = "/vehicleRequest?pageSize=";
	public static String VEHICLE_NEXTPAGE_PARAM = "/vehicleRequest/";

	
	
	private static String varPayerId = "G53SL5V9APQV2";
	
	private final GisHttpClient httpClient ;
	

	private URI getServerURI(String param ) throws URISyntaxException {
		
		String server = AdacModelProvider.INSTANCE.serverUrl;

		if ( server == null|| server.isEmpty() )
			server = TaskProperties.getTaskProperties().getServerProperty();
		
		URI uri = new URI(server+param);
		return uri;
		
	}

	//http://localhost:8080/web-joi/joi/vehicleRequest/673406724/vehicle/1468117378/offer/6cadd10a-3c5f-43bb-99b0-02a8e4da7370

	private URI getExtrasURI(URI offerLink) throws URISyntaxException {
		String link = offerLink.toString();
		int pos = link.indexOf("/vehicleRequest");
		link = link.substring(pos);
		return getServerURI(link+"/extras");
	}

	private URI getPickupStationURI(URI offerLink) throws URISyntaxException {
		String link = offerLink.toString();
		int pos = link.indexOf("/vehicleRequest");
		link = link.substring(pos);
		return getServerURI(link+"/pickUp");
	}
	

	public AdacVehicleHttpService(GisHttpClient gisHttpClientInstance) {
		this.httpClient = gisHttpClientInstance;
	}

	public VehicleResponse getOffers(VehicleRequest vehicleRequest, boolean dummy, int pageSize) {

		try {
			
			URI uri = getServerURI(VEHICLE_REQUEST_PARAM+String.valueOf(pageSize));
			logger.info("get Offers URI : = "+uri.toString());

			String request = JsonUtils.convertRequestToJsonString(vehicleRequest);
			logger.info(" request = "+request);
			
			String response = null;
			
			dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				response = JsonUtils.createDummyResponse("AdacWebJoiVehicleResponse.json");
			else
				response =  httpClient.startPostRequestAsJson(uri, request);
			
			logger.info("response = "+response);
			
			if (response != null ) 
				return JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			
		} catch ( IOException e) {
			logger.error(e.getMessage(),e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}
	
	public VehicleResponse getPage(int pageNo) {
		try {
			VehicleResponse respone = ModelProvider.INSTANCE.getVehicleResponse();
			if ( respone == null )
				throw new VehicleServiceException("No vehicle response exist in the Model. Call GetOffer at first");
			Long requestId = respone.getRequestId();
			
			URI uri = getServerURI(VEHICLE_NEXTPAGE_PARAM+String.valueOf(requestId)+"?pageNo="+pageNo);

			logger.info("get page URI : = "+uri.toString());

			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				String response = httpClient.sendGetRequest(uri);

				logger.info("response = "+response);
				return  JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			}
			else {
				String response = JsonUtils.createDummyResponse("AdacWebJoiVehicleResponse.json");
				return  JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;

	}
	
	public VehicleResponse getBrowsePage(int pageNo, VehicleRequestFilter filter) {
		try {
			VehicleResponse respone = ModelProvider.INSTANCE.getVehicleResponse();
			if ( respone == null )
				throw new VehicleServiceException("No vehicle response exist in the Model. Call GetOffer at first");
			Long requestId = respone.getRequestId();
			
			URI uri = getServerURI(VEHICLE_NEXTPAGE_PARAM+String.valueOf(requestId)+"?pageNo="+pageNo);

			logger.info("browse filter page URI : = "+uri.toString());
			
			String request = JsonUtils.convertRequestToJsonString(filter);
			logger.info(" request filter = "+request);


			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				String response = httpClient.startPutRequestAsJson(uri, request);
				logger.info("response = "+response);
				return  JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			}
			else {
				String response = JsonUtils.createDummyResponse("AdacWebJoiVehicleResponse.json");
				return  JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;

	}


	
	public ExtraResponse getExtras(Offer offer) {
		ExtraResponse extraResponse =  null;
		try {

			URI uri = getExtrasURI(offer.getLink());
			logger.info("GetExtra Request = "+uri);
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				extraResponse = getExtrasDummy();
			else {
				String response =  httpClient.sendGetRequest(uri);
				logger.info("response = "+response);
				extraResponse = JsonUtils.createResponseClassFromJson(response, ExtraResponse.class);
			}
			return extraResponse;
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return extraResponse;

	}
	
	public String getPickupStations(Offer offer) {
		String response = "PickupStations ";
		try {
			
			
			URI uri = getPickupStationURI(offer.getLink());
			logger.info("getPickupStations Request = "+uri);
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy)
			{
				response =  httpClient.sendGetRequest(uri);
				logger.info("response = "+response);
			}
			return response;
			
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
	
	
	public static BookingResponse verifyOffers(BookingRequest bookingRequest, Offer selectedOffer) {
		GisHttpClient httpClient = new GisHttpClient();

		try {
			
			String link = selectedOffer.getBookLink().toString();
			int pos = link.indexOf("/vehicleRe");
			link = link.substring(pos);
			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
			
			logger.info("Verify Request URI = "+uri);

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
	
//	public static BookingResponse verifyOffers(Offer offer,) {
//		GisHttpClient httpClient = new GisHttpClient();
//
//		try {
//			
//			BookingRequest bookingRequest = new BookingRequest();
//			String link = offer.getBookLink().toString();
//			int pos = link.indexOf("/vehicleRe");
//			link = link.substring(pos);
//			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
//			
//			logger.info("Verify Request URI = "+uri);
//
//			Customer customer = createCustomer();
//			
//			bookingRequest.setCustomer(customer);
//			
//			Person driver = createDriver();
//			
//			bookingRequest.setDriver(driver);
//			Payment payment = new Payment();
//			payment.setPaymentType("1");
//			
//			//payment.setPaymentType(PaymentType.PAYPAL_PAYMENT);
//			
//			bookingRequest.setAcceptedAvailability("13");
//			bookingRequest.setFlightNo("LH4711");
//			bookingRequest.setTransferType("1");
//			bookingRequest.setPriceLimit(new MoneyAmount("1000, 00","EUR"));
//			//bookingRequest.setPayment(payment);
//			
//			bookingRequest.setExtras(selectedExtras);
//
//			String request = JsonUtils.convertRequestToJsonString(bookingRequest);
//			logger.info("Verify Request = "+request);
//			
//			String response =  httpClient.startPutRequestAsJson(uri, request);
//			logger.info("Verify Response = "+response);
//			
//			BookingResponse vh = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);
//			
//
//			return vh;
//			
//		} catch ( IOException e) {
//			logger.error(e);
//		} catch (URISyntaxException e) {
//			logger.error(e);
//		}
//		return null;
//
//	}


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
		address.setCity("München");
		address.setStreet("Street");
		address.setZip("81543");
		address.setCountry("DE");
		//address.setCountryId(Long.valueOf(49));
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
	
	private static ExtraResponse getExtrasDummy() {

		try {
			
			String response = JsonUtils.createDummyResponse("GetExtrasResponse.json");
			
			logger.info("response = "+response);
			
			ExtraResponse vh = JsonUtils.createResponseClassFromJson(response, ExtraResponse.class);
			

			return vh;
			
		} catch ( IOException e) {
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
	
	public URI getPypageUrl() {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				
				
				URI uri = getServerURI(SUNNY_PAYPAMENT_PAYPAGE);
				
				logger.info("VehicleHttpService = "+uri.toString());
				
				String response = httpClient.sendGetRequest(uri);
				logger.info("response : "+response);
				return  JsonUtils.createResponseClassFromJson(response, URI.class);
			}
			else {
				String response = JsonUtils.createDummyResponse("DummyJoiGetOfferResponse.json");
				return  new URI(TaskProperties.getTaskProperties().getServerProperty());
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

//	public VerifyCreditCardPaymentResponse getPayPageResult() {
//		try {
//			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
//			if ( !dummy) {
//				
//				
//				URI uri = getServerURI(SUNNY_PAYPAMENT_VERIFY);
//				
//				
//				logger.info("VehicleHttpService = "+uri.toString());
//				
//				String response = httpClient.sendGetRequest(uri);
//				logger.info("response : "+response);
//				return  JsonUtils.createResponseClassFromJson(response, VerifyCreditCardPaymentResponse.class);
//			}
//			else {
//				VerifyCreditCardPaymentResponse resp = new VerifyCreditCardPaymentResponse();
//				return  resp;
//			}
//			
//		} catch (IOException e) {
//			logger.error(e,e);
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//
//	}
	

//	public OfferInformation selectOffer(URI link) {
//		try {
//			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
//			if ( !dummy) {
//				URI uriServer = getServerURI("");
//				URI uri = new URI (uriServer.getScheme(),"", uriServer.getHost(),uriServer.getPort(), link.getPath(),null, link.getFragment());
//				logger.info("httpClient : "+httpClient+ ". select offer : "+uri);
//				String response = httpClient.sendGetRequest(uri);
//				if  (response == null) {
//
//					return null;
//				}
//				else {
//					logger.info(" response : "+response);
//					return  JsonUtils.createResponseClassFromJson(response, OfferInformation.class);
//				}
//				
//			}
//			else {
//				String response = JsonUtils.createDummyResponse("DummyJoiGetOfferResponse.json");
//				return  JsonUtils.createResponseClassFromJson(response, OfferInformation.class);
//			}
//			
//		} catch (IOException e) {
//			logger.error(e,e);
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//
//	}

	
	

//	public String putDriver(Offer offer, Text driverText) {
//		try {
//			
//			URI uri = getServerURI(SUNNY_BOOKING_DRIVER);
//			
//			
//			Person driver = new Person();
//			
//			driver.setName("Müller");
//			driver.setFirstName("Florian");
//			driver.setGender("1");
//			driver.setComment(" from Giss APP ");
//			DayAndHour birthday = new DayAndHour("1963-06-17", "00:00");
//			driver.setBirthday(birthday);
//
//			String request = JsonUtils.convertRequestToJsonString(driver);
//			logger.info("request = "+request);
//
//			String response =  httpClient.startPutRequestAsJson(uri, request);
//			
//			return response;
//
////			if (response != null ) 
////				return JsonUtils.createResponseClassFromJson(response, Person.class);
//
//			
//		} catch ( IOException e) {
//			logger.error(e.getMessage(),e);
//		} catch (URISyntaxException e) {
//			logger.error(e);
//		}
//		
//		return null;
//	}

//	public String putCustomer(Offer selectedOffer, Text customerText) {
//		try {
//			
//			URI uri = getServerURI(SUNNY_BOOKING_CUSTOMER);
//
//			
//			Customer customer = new Customer();
//			
//			
//			Person driver = new Person();
//			
//			driver.setName("Müller");
//			driver.setFirstName("Florian");
//			driver.setGender("1");
//			driver.setComment(" from Giss APP ");
//			DayAndHour birthday = new DayAndHour("1963-06-17", "00:00");
//			driver.setBirthday(birthday);
//			
//			customer.setPerson(driver);
//			customer.setCustomerNo("123456");
//			
//			Address address = new Address();
//			address.setCity("München");
//			address.setStreet("Schönstr");
//			address.setZip("81543");
//			address.setCountryId(1);
//			customer.setAddress(address);
//
//			String request = JsonUtils.convertRequestToJsonString(customer);
//			logger.info("request = "+request);
//
//			String response =  httpClient.startPutRequestAsJson(uri, request);
//			
//			return response;
//
////			if (response != null ) 
////				return JsonUtils.createResponseClassFromJson(response, Person.class);
//
//			
//		} catch ( IOException e) {
//			logger.error(e.getMessage(),e);
//		} catch (URISyntaxException e) {
//			logger.error(e);
//		}
//		
//		return null;
//	}

	public String bookOffer(Offer selectedOffer) {
		try {

			String param = "/booking/offer/"+selectedOffer.getId().toString()+"/book";
			
			URI uri = getServerURI(param);
			

			String response =  httpClient.sendGetRequest(uri);
			
			return response;

//			if (response != null ) 
//				return JsonUtils.createResponseClassFromJson(response, Person.class);

			
		} catch ( IOException e) {
			logger.error(e.getMessage(),e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		
		return null;
	}
	
	public String verifyOffer(Offer selectedOffer) {
		try {

			String param = "/booking/offer/"+selectedOffer.getId().toString()+"/verify";
			
			URI uri = getServerURI(param);

			String response =  httpClient.sendGetRequest(uri);
			
			return response;

//			if (response != null ) 
//				return JsonUtils.createResponseClassFromJson(response, Person.class);

			
		} catch ( IOException e) {
			logger.error(e.getMessage(),e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		
		return null;
	}


	
}
