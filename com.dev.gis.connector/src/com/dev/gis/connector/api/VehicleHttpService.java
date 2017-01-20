package com.dev.gis.connector.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.sunny.Address;
import com.dev.gis.connector.sunny.BookingRequest;
import com.dev.gis.connector.sunny.BookingResponse;
import com.dev.gis.connector.sunny.Customer;
import com.dev.gis.connector.sunny.DayAndHour;
import com.dev.gis.connector.sunny.Extra;
import com.dev.gis.connector.sunny.Offer;
import com.dev.gis.connector.sunny.OfferExtras;
import com.dev.gis.connector.sunny.OfferFilter;
import com.dev.gis.connector.sunny.OfferInformation;
import com.dev.gis.connector.sunny.PayPageResponse;
import com.dev.gis.connector.sunny.Payment;
import com.dev.gis.connector.sunny.Person;
import com.dev.gis.connector.sunny.PhoneNumber;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.StationResponse;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.connector.sunny.VerifyCreditCardPaymentResponse;

public class VehicleHttpService {
	private static Logger logger = Logger.getLogger(VehicleHttpService.class);

	
	public static String SUNNY_VEHICLE_REQUEST_PARAM = "/request?ratingView=1&sort=asc&pageSize=";
	public static String SUNNY_NEXT_PAGE_REQUEST_PARAM = "/request/browsepage?page=";
	public static String SUNNY_BROWSE_REQUEST_PARAM = "/request/browse?";
	public static String SUNNY_GET_PICKUP_STATIONS = "/request/pickupstations?";
	public static String SUNNY_RECALCULATE = "/request/recalculate?";
	public static String SUNNY_GET_DROPOFF_STATIONS = "/request/dropoffstations?";
	public static String SUNNY_PAYPAMENT_PAYPAGE = "/payment/paypageurl";
	public static String SUNNY_PAYPAMENT_VERIFY = "/payment/verify";
	public static String SUNNY_BOOKING_DRIVER = "/booking/driver/put";
	public static String SUNNY_BOOKING_PAYMENT = "/booking/payment/put";
	public static String SUNNY_PUT_EXTRAS = "/request/extras/put";
	public static String SUNNY_BOOKING_CUSTOMER = "/booking/customer/put";

	
	private static String varPayerId = "G53SL5V9APQV2";
	
	private final GisHttpClient httpClient ;
	

	private URI getServerURI(String param ) throws URISyntaxException {
		
		String server = SunnyModelProvider.INSTANCE.serverUrl;

		if ( server == null|| server.isEmpty() )
			server = TaskProperties.getTaskProperties().getServerProperty();
		
		URI uri = new URI(server+param);
		logger.info("VehicleHttpService URI : = "+uri.toString());
		return uri;
		
	}

	public VehicleHttpService(GisHttpClient gisHttpClientInstance) {
		this.httpClient = gisHttpClientInstance;
	}

	public VehicleResponse getOffers(VehicleRequest vehicleRequest, boolean dummy, int pageSize, String sessionId) {

		try {
			
			String param = SUNNY_VEHICLE_REQUEST_PARAM+String.valueOf(pageSize);
			if ( StringUtils.isNotBlank(sessionId))
				param = param + "&sessionCode="+sessionId;
					
			URI uri = getServerURI(param);

			String request = JsonUtils.convertRequestToJsonString(vehicleRequest);
			logger.info("http client "+httpClient+ " request = "+request);
			
			String response = null;
			
			dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				//response = JsonUtils.createDummyResponse("SunnyVehicleResponseRatingView");
				//response = JsonUtils.createDummyResponse("SunnyVehicleResponse");
				response = JsonUtils.createDummyResponse("Sunny_PMI_VehicleResponseRatingView");
			else
				response =  httpClient.startPostRequestAsJson(uri, request);
			
			logger.info("response = "+response);
			
			ModelProvider.INSTANCE.lastResponse = response;

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
			URI uri = getServerURI(SUNNY_NEXT_PAGE_REQUEST_PARAM+String.valueOf(pageNo));
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				String response = httpClient.sendGetRequest(uri);
				return  JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			}
			else {
				String response = JsonUtils.createDummyResponse("SunnyVehicleResponsePage2");
				return  JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;

	}
	
	
//	public static BookingResponse verifyOffers(BookingRequest bookingRequest, Offer selectedOffer) {
//		GisHttpClient httpClient = new GisHttpClient();
//
//		try {
//			
//			String link = selectedOffer.getBookLink().toString();
//			int pos = link.indexOf("/vehicleRe");
//			link = link.substring(pos);
//			URI uri = new URI(TaskProperties.getTaskProperties().getServerProperty()+link);
//			
//			logger.info("Verify Request URI = "+uri);
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
	


	public VehicleResponse recalculate(Offer selectedOffer, VehicleRequest vehicleRequest) {
		
		try {
			
			
			String query = "offerId="+selectedOffer.getId().toString();
			
			
			String requestUrl = SUNNY_RECALCULATE + query;

			URI uri = getServerURI(requestUrl);
			
			logger.info("recalculate Request = "+uri);
			
			String request = JsonUtils.convertRequestToJsonString(vehicleRequest);
			logger.info("request = "+request);
			
			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("response = "+response);
			if ( response == null)
				response = " no cars found ";
			
			if (response != null ) 
				return JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}
	
	public PayPageResponse getPypageUrl() {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				
				
				URI uri = getServerURI(SUNNY_PAYPAMENT_PAYPAGE);
				
				logger.info("VehicleHttpService = "+uri.toString());
				
				String response = httpClient.sendGetRequest(uri);
				logger.info("response : "+response);
				return  JsonUtils.createResponseClassFromJson(response, PayPageResponse.class);
			}
			else {
				PayPageResponse resp = new PayPageResponse();
				return  resp;
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public VerifyCreditCardPaymentResponse getPayPageResult() {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				
				
				URI uri = getServerURI(SUNNY_PAYPAMENT_VERIFY);
				
				
				logger.info("VehicleHttpService = "+uri.toString());
				
				String response = httpClient.sendGetRequest(uri);
				logger.info("response : "+response);
				return  JsonUtils.createResponseClassFromJson(response, VerifyCreditCardPaymentResponse.class);
			}
			else {
				VerifyCreditCardPaymentResponse resp = new VerifyCreditCardPaymentResponse();
				return  resp;
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	

	public OfferInformation selectOffer(URI link) {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				URI uriServer = getServerURI("");
				URI uri = new URI (uriServer.getScheme(),"", uriServer.getHost(),uriServer.getPort(), link.getPath(),null, link.getFragment());
				logger.info("httpClient : "+httpClient+ ". select offer : "+uri);
				String response = httpClient.sendGetRequest(uri);
				if  (response == null) {

					return null;
				}
				else {
					logger.info(" response : "+response);
					return  JsonUtils.createResponseClassFromJson(response, OfferInformation.class);
				}
				
			}
			else {
				String response = JsonUtils.createDummyResponse("DummyJoiGetOfferResponse.json");
				return  JsonUtils.createResponseClassFromJson(response, OfferInformation.class);
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	// http://localhost:8080/sunny-joi/joi/request/browse?page=2&pageSize=5&sort=desc

	public VehicleResponse getBrowsePage(int pageNo, int pageSize) {
		try {
			
			String params = SUNNY_BROWSE_REQUEST_PARAM;
			params = params+"page="+pageNo;
			params = params+"&pageSize="+pageSize;
			params = params+"&sort="+"desc";

			URI uri = getServerURI(params);

			
			logger.info("VehicleHttpService = "+uri.toString());
			
//			OfferFilter offerFilter = new OfferFilter();
//			offerFilter.setMaxPrice(BigDecimal.valueOf(500));
			
			OfferFilter offerFilter = SunnyModelProvider.INSTANCE.offerFilter;

			String request = JsonUtils.convertRequestToJsonString(offerFilter);
			logger.info("request = "+request);
			
			String response = null;
			
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
	
	public StationResponse getPickupStations(int type, String location, String offerId) {
		try {

			logger.info("VehicleHttpService getPickupStations = "+type + " offerId "+offerId+ " location "+location);

			
			if ( type == 0)
				type = 6;  // airport
			else if ( type == 1)
				type = 2;   // city
			String query = "offerId="+offerId;
			query +="&type="+type;
			if( type == 6 ) {
				if (StringUtils.isNumeric(location)) {
					query +="&locationId="+location;
				}
				else {
					query +="&apt="+location;
				}
				//query +="&stationLocTypesFilter=APT";
			}
			else
				query +="&locationId="+location;

			logger.info("VehicleHttpService query = "+query);
			
			String requestUrl = SUNNY_GET_PICKUP_STATIONS + query;

			URI suri = getServerURI(requestUrl);
			
			logger.info("VehicleHttpService getPickupStations = "+suri.toString());
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy ) {
				StationResponse stationResponse = new StationResponse();
				Station st = new Station(1234);
				st.setIdentifier("München");
				stationResponse.getStations().add(st);

				st = new Station(9876);
				st.setIdentifier("Tomsk");
				stationResponse.getStations().add(st);
				
				return stationResponse;
			}
			else {
			
				String response = null;
				
				response =  httpClient.sendGetRequest(suri);
				
				logger.info("response = "+response);
				
				if (response != null ) 
					return JsonUtils.createResponseClassFromJson(response, StationResponse.class);
			}
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	public StationResponse getDropOffStations(int type, String location, String offerId, String pickupStationId) {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			
			if ( type == 0)
				type = 6;  // airport
			else if ( type == 1)
				type = 2;   // city
			String query = "offerId="+offerId;
			query +="&type="+type;
			
			if( type == 6 ) {
				if (StringUtils.isNumeric(location)) {
					query +="&locationId="+location;
				}
				else {
					query +="&apt="+location;
				}
			}
			else
				query +="&locationId="+location;
			
			
			query +="&pickupStation="+pickupStationId;
			
			String requestUrl = SUNNY_GET_DROPOFF_STATIONS + query;

			URI uri = getServerURI(requestUrl);
			
			logger.info("VehicleHttpService getDropOffStations URI = "+uri.toString());
			
			if ( dummy ) {
				StationResponse stationResponse = new StationResponse();
				Station st = new Station(1234);
				st.setIdentifier("München");
				stationResponse.getStations().add(st);

				st = new Station(9876);
				st.setIdentifier("Tomsk");
				stationResponse.getStations().add(st);
				
				return stationResponse;
			}

			
			String response = null;
			
			response =  httpClient.sendGetRequest(uri);
			
			logger.info("response = "+response);
			
			if (response != null ) 
				return JsonUtils.createResponseClassFromJson(response, StationResponse.class);
			
		} catch ( IOException e) {
			logger.error(e.getMessage(),e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}

	public String putDriver(Offer offer, Text driverText) {
		try {
			
			URI uri = getServerURI(SUNNY_BOOKING_DRIVER);
			
			
			Person driver = new Person();
			
			driver.setName("TestMüller");
			driver.setFirstName("TestFlorian");
			driver.setGender("2");
			driver.setSalutation("1");
			driver.setComment(" from Bpcs Test ");
			DayAndHour birthday = new DayAndHour("1963-06-17", "00:00");
			driver.setBirthday(birthday);

			String request = JsonUtils.convertRequestToJsonString(driver);
			logger.info("request = "+request);

			String response =  httpClient.startPutRequestAsJson(uri, request);
			
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
	

	public String putPayment(Payment payment) {
		try {
			
			URI uri = getServerURI(SUNNY_BOOKING_PAYMENT);
			

			String request = JsonUtils.convertRequestToJsonString(payment);
			logger.info("request = "+request);

			String response =  httpClient.startPutRequestAsJson(uri, request);
			
			return response;

			
		} catch ( IOException e) {
			logger.error(e.getMessage(),e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		
		return null;
	}
	

	public String putCustomer(Offer selectedOffer, Text customerText) {
		try {
			
			URI uri = getServerURI(SUNNY_BOOKING_CUSTOMER);

			
			Customer customer = new Customer();
			
			
			Person driver = new Person();
			
			driver.setName("TestMüller");
			driver.setFirstName("TestFlorian");
			driver.setGender("2");
			driver.setSalutation("1");
			driver.setComment(" from Bpcs Test APP ");
			DayAndHour birthday = new DayAndHour("1963-06-17", "00:00");
			driver.setBirthday(birthday);
			
			customer.setPerson(driver);
			customer.setCustomerNo("123456");
			
			Address address = new Address();
			address.setCity("München");
			address.setStreet("Schönstr");
			address.setZip("81543");
			address.setCountryId(1);
			address.setCountry("Germany");
			customer.setAddress(address);
			
			customer.setEMail("vv@arcor.de");

			String request = JsonUtils.convertRequestToJsonString(customer);
			logger.info("request = "+request);

			String response =  httpClient.startPutRequestAsJson(uri, request);
			
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

	public BookingResponse bookOffer(Offer selectedOffer, String agencyBookingCode) {
		try {

			String param = "/booking/offer/"+selectedOffer.getId().toString()+"/book";
			
			if ( StringUtils.isNotEmpty(agencyBookingCode))
				param = param + "?agencyBookingCode="+agencyBookingCode;
			
			
			URI uri = getServerURI(param);
			
			String response =  httpClient.sendGetRequest(uri);
			
			BookingResponse booking = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);
			
			return booking;

			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}
	
	public BookingResponse verifyOffer(Offer selectedOffer,String agencyBookingCode) {
		try {

			//String param = "/booking/offer/"+selectedOffer.getId().toString()+"/verify?test=true";
			String param = "/booking/offer/"+selectedOffer.getId().toString()+"/verify";
			if ( StringUtils.isNotEmpty(agencyBookingCode))
				param = param + "?agencyBookingCode="+agencyBookingCode;
			
			
			URI uri = getServerURI(param);
			String response = "";
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				response = JsonUtils.createDummyResponse("SunnyVerifyResponse1.json");
			else
				response =  httpClient.sendGetRequest(uri);
			
			BookingResponse booking = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);
			
			return booking;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}


	public String putExtras(Offer selectedOffer, List<Extra> selectedExtras) {
		try {
			
			URI uri = getServerURI(SUNNY_PUT_EXTRAS);
			
			
			OfferExtras offerExtras = new OfferExtras();
			
			offerExtras.setOfferId(selectedOffer.getId().toString());
			
			for (Extra extra : selectedExtras ) {
				offerExtras.getExtrasList().add(Long.valueOf(extra.getId()));
			}
			
			String request = JsonUtils.convertRequestToJsonString(offerExtras);
			logger.info("request = "+request);

			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			String response = "";
			if ( !dummy)
				response =  httpClient.startPutRequestAsJson(uri, request);
			
			return response;

			
		} catch ( IOException e) {
			logger.error(e.getMessage(),e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		
		return null;
	}

	public BookingRequest getBookingInfo(SunnyOfferDo selectedOffer) {
		try {

			String param = "/booking/offerInfo?offerId="+selectedOffer.getId().toString();
			
			URI uri = getServerURI(param);

			String response =  httpClient.sendGetRequest(uri);
			
			BookingRequest booking = JsonUtils.createResponseClassFromJson(response, BookingRequest.class);
			
			return booking;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}

	
}
