package com.dev.gis.connector.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bpcs.mdcars.json.protocol.CredentialResponse;
import com.bpcs.mdcars.json.protocol.DefectDetailRequest;
import com.bpcs.mdcars.json.protocol.DefectListFilterRequest;
import com.bpcs.mdcars.json.protocol.DefectListResponse;
import com.bpcs.mdcars.json.protocol.DefectResponse;
import com.bpcs.mdcars.json.protocol.DispoPoolListResponse;
import com.bpcs.mdcars.json.protocol.DispoPoolRequest;
import com.bpcs.mdcars.json.protocol.DispoPoolResponse;
import com.bpcs.mdcars.json.protocol.DispositionDetailRequest;
import com.bpcs.mdcars.json.protocol.DispositionListResponse;
import com.bpcs.mdcars.json.protocol.DispositionResponse;
import com.bpcs.mdcars.json.protocol.GetCMPaymentInfoRequest;
import com.bpcs.mdcars.json.protocol.GetCMPaymentInfoResponse;
import com.bpcs.mdcars.json.protocol.GetCustomerResponse;
import com.bpcs.mdcars.json.protocol.GetDispoListRequest;
import com.bpcs.mdcars.json.protocol.ReservationListFilterRequest;
import com.bpcs.mdcars.json.protocol.ReservationListResponse;
import com.bpcs.mdcars.json.protocol.ReservationResponse;
import com.bpcs.mdcars.json.protocol.SetCMPaymentTransactionRequest;
import com.bpcs.mdcars.model.Clerk;
import com.bpcs.mdcars.model.Credential;
import com.bpcs.mdcars.model.DayAndHour;
import com.dev.gis.connector.GisHttpClient;
import com.dev.gis.connector.JsonUtils;
import com.dev.gis.connector.joi.protocol.Address;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.BookingResponse;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Payment;
import com.dev.gis.connector.joi.protocol.PaypalDoCheckoutResponse;
import com.dev.gis.connector.joi.protocol.PaypalSetCheckoutResponse;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.PhoneNumber;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.StationResponse;
import com.dev.gis.connector.joi.protocol.Token;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleRequestFilter;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.connector.joi.protocol.VerifyCreditCardPaymentResponse;

public class ClubMobilHttpService {
	private static Logger logger = Logger.getLogger(ClubMobilHttpService.class);

	public static String CLUBMOBIL_CREDENTIALS = "/credentials";
	public static String CLUBMOBIL_CREDENTIALS_GETTOKEN = "/credentials/getToken";
	public static String CLUBMOBIL_MASTERDATA = "/masterdata";
	public static String CLUBMOBIL_RESERVATION = "/reservation";
	public static String CLUBMOBIL_DISPOSITION = "/disposition";
	public static String CLUBMOBIL_DEFECT = "/defect";

	public static String CLUBMOBIL_PAYMENT = "/payment";
	
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
		
		String server = ClubMobilModelProvider.INSTANCE.serverUrl;

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
		int pos2 = link.indexOf("/offer");
		link = link.substring(0,pos2);
		
		return getServerURI(link+"/pickUp");
	}

	// http://193.30.38.251/web-joi/joi/vehicleRequest/600354792/vehicle/940561578/offer/37c76ed6-0006-48c4-9ff7-bcc381e1004a/book
	private URI getVerifyURI(URI offerLink, String promotionCode ) throws URISyntaxException {
		String link = offerLink.toString();
		int pos = link.indexOf("/vehicleRequest");
		link = link.substring(pos);
		
		link = link+"/book";
		if ( StringUtils.isNotEmpty(promotionCode))
			link = link +"?promotionCode="+promotionCode;

		return getServerURI(link);
	}
	
	private URI getPaypalURI(String link) throws URISyntaxException{
		return getServerURI(link);
	}

	

	public ClubMobilHttpService(GisHttpClient gisHttpClientInstance) {
		this.httpClient = gisHttpClientInstance;
	}

	public VehicleResponse getOffers(VehicleRequest vehicleRequest, boolean dummy, int pageSize, boolean crossOfferFlag) {

		try {
			
			String param = VEHICLE_REQUEST_PARAM+String.valueOf(pageSize);
			int flag = 0;
			if ( crossOfferFlag)
				flag = 1;
			param = param + "&crossOffer="+flag;
			
			if ( vehicleRequest.getFilter() != null)
				param = param + "&remoteFilter=true";
			
			URI uri = getServerURI(param);
			logger.info("get Offers URI : = "+uri.toString());

			String request = JsonUtils.convertRequestToJsonString(vehicleRequest);
			logger.info(" request = "+request);
			
			String response = null;
			
			dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				//response = JsonUtils.createDummyResponse("AdacWebJoiVehicleResponse.json");
				response = JsonUtils.createDummyResponse("AdacWebJoiResponseWithEmptyCrossOffer.json");
			else
				response =  httpClient.startPostRequestAsJson(uri, request);
			
			logger.info("response = "+response);
			
			ModelProvider.INSTANCE.lastResponse = response;
			
			if (response != null ) 
				return JsonUtils.createResponseClassFromJson(response, VehicleResponse.class);
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
		return null;
	}
	
	public VehicleResponse getPage(int pageNo) {
		try {
			VehicleResponse respone = AdacModelProvider.INSTANCE.getVehicleResponse();
			if ( respone == null )
				throw new VehicleServiceException("No vehicle response exist in the Model. Call GetOffer at first");
			Long requestId = respone.getRequestId();
			
			URI uri = getServerURI(VEHICLE_NEXTPAGE_PARAM+String.valueOf(requestId)+"?page="+pageNo);

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
			VehicleResponse respone = AdacModelProvider.INSTANCE.getVehicleResponse();
			if ( respone == null )
				throw new VehicleServiceException("No vehicle response exist in the Model. Call GetOffer at first");
			Long requestId = respone.getRequestId();
			
			URI uri = getServerURI(VEHICLE_NEXTPAGE_PARAM+String.valueOf(requestId)+"?page="+pageNo);

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
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}

	}


	public StationResponse getPickupStations(Offer offer) {
		String response = "PickupStations ";
		try {
			
			URI uri = getPickupStationURI(offer.getLink());
			logger.info("getPickupStations Request = "+uri);
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy)
			{
				response =  httpClient.sendGetRequest(uri);
				logger.info("response = "+response);
				return JsonUtils.createResponseClassFromJson(response, StationResponse.class);
			}
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public StationResponse getDropoffStations(Offer offer, String pickupStationId) {
		String response = "PickupStations ";
		try {
			String link = offer.getLink().toString();
			int pos = link.indexOf("/vehicleRequest");
			link = link.substring(pos);
			int pos2 = link.indexOf("/offer");
			link = link.substring(0,pos2);
			link = link +"/dropOff?pickUpStation="+pickupStationId;
			URI uri = getServerURI(link);
			
			logger.info("getDropofStations Request = "+uri);
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy)
			{
				response =  httpClient.sendGetRequest(uri);
				logger.info("response = "+response);
				return JsonUtils.createResponseClassFromJson(response, StationResponse.class);
			}
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}
	

	public PaypalSetCheckoutResponse getPaypalUrl(String bookingRequestId) {

		try {
			String link = "/booking/"+bookingRequestId+"/payPaypal";
			
			URI uri = getPaypalURI(link);
			
			logger.info("GetPaypal URL Request = "+uri);
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			PaypalSetCheckoutResponse paypalResponse = JsonUtils.createResponseClassFromJson(response, PaypalSetCheckoutResponse.class);

			return paypalResponse;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}


	public PaypalDoCheckoutResponse getPaypalResult(String bookingRequestId, String token) {

		//joi/booking/${varBookingCacheId}/getPaypalResult?payerID=${varPayerId}&token=${varToken}
		try {
			String link = "/booking/"+bookingRequestId+"/getPaypalResult";
			link = link + "?payerID="+varPayerId;
			link = link + "&token="+token;
			link = link + "&test=0";
			URI uri = getPaypalURI(link);
			
			logger.info("GetPaypal URL Request = "+uri);
			
			
			String response =  httpClient.sendGetRequest(uri);
			logger.info("response = "+response);
			
			PaypalDoCheckoutResponse paypalResult = JsonUtils.createResponseClassFromJson(response, PaypalDoCheckoutResponse.class);

			return paypalResult;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
		
	}
	
	
	public BookingResponse verifyOffers(BookingRequest bookingRequest, Offer selectedOffer , String promotionCode) {

		try {
			logger.info("Verify Request URI = "+selectedOffer.getLink());

			URI uri = getVerifyURI(selectedOffer.getLink() , promotionCode);
			logger.info("Verify Request URI = "+uri);

			String request = JsonUtils.convertRequestToJsonString(bookingRequest);
			logger.info("Verify Request = "+request);
			
			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("Verify Response = "+response);
			
			BookingResponse vh = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);

			return vh;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}
	
	public BookingResponse bookOffers(Offer selectedOffer,String bookingRequestId,
			List<Extra> selectedExtras, int paymentType,Customer customer, String promotionCode ) {
		

		try {
			// joi/booking/${varBookingCacheId}/book?validateOnly=false
			BookingRequest bookingRequest = new BookingRequest();
			
			String link = "/booking/"+bookingRequestId+"/book?validateOnly=false";
			
			if ( StringUtils.isNotEmpty(promotionCode))
				link = link +"&promotionCode="+promotionCode;
			
			URI uri = getServerURI(link);
			
			logger.info("Book Request URI = "+uri);
			
			bookingRequest.setCustomer(customer);
			
			Person driver = createDriver();
			
			bookingRequest.setDriver(driver);
			
			if ( paymentType != 0) {
				Payment payment = new Payment();
				payment.setPaymentType(paymentType);  
				bookingRequest.setPayment(payment);
			}
			
			//bookingRequest.setAcceptedAvailability("13");
			bookingRequest.setFlightNo("LH4711");
			bookingRequest.setTransferType(1);
			bookingRequest.setPriceLimit(new MoneyAmount("1000, 00","EUR"));
			
			bookingRequest.setExtras(selectedExtras);

			String request = JsonUtils.convertRequestToJsonString(bookingRequest);
			logger.info("book Request = "+request);
			
			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("book Response = "+response);
			
			BookingResponse vh = JsonUtils.createResponseClassFromJson(response, BookingResponse.class);

			return vh;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}



	private static Person createDriver() {
		Person driver = new Person();
		driver.setName("Meier");
		driver.setFirstName("Anton");
		
		return driver;
	}

	public static Customer createCustomer(String memberNo) {
		Customer customer = new Customer();
		Person person = createDriver();
		customer.setPerson(person);
		Address address = new Address();
		address.setCity("München");
		address.setStreet("Street");
		address.setZip("81543");
		address.setCountry("DE");
		address.setCountryId(Long.valueOf(1));
		customer.setAddress(address);
		customer.setEMail("John-Appleseed@mac.com");
		PhoneNumber ph = new PhoneNumber();
		ph.setPrefix("");
		ph.setExtension("8885555512");
		customer.setPhone(ph);
		
		if ( StringUtils.isNotEmpty(memberNo))
			customer.setExternalCustomerNo(memberNo);
		
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



	public VehicleResult recalculate(Offer selectedOffer, TravelInformation travelInformation , String promotionCode) {
		
		try {
			String link = selectedOffer.getBookLink().toString();
			int pos = link.indexOf("/vehicleRe");
			link = link.substring(pos);
			pos = link.indexOf("/offer");
			link = link.substring(0,pos);
			link = link + "/recalculate";
			
			if ( StringUtils.isNotEmpty(promotionCode))
				link = link +"?promotionCode="+promotionCode;
			
			URI uri = getServerURI(link);
			
			logger.info("recalculate Request = "+uri);
			
			String request = JsonUtils.convertRequestToJsonString(travelInformation);
			logger.info("request = "+request);
			
			String response = null;
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				response = JsonUtils.createDummyResponse("adac.recalculateResponse");
			else
				response =  httpClient.startPostRequestAsJson(uri, request);
			
			logger.info("response = "+response);
			if ( response == null)
				response = " no cars found ";
			
			if (response != null ) 
				return JsonUtils.createResponseClassFromJson(response, VehicleResult.class);
			
		} catch ( IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}
	
	public URI getBSPayPageUrl(String bookingRequestId ) {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				
				String link = "/booking/"+bookingRequestId+"/payCreditCard";
				URI uri = getServerURI(link);

				logger.info("VehicleHttpService = "+uri.toString());
				
				String response = httpClient.sendGetRequest(uri);
				logger.info("response : "+response);
				return  JsonUtils.createResponseClassFromJson(response, URI.class);
			}
			else {
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
	
	public VerifyCreditCardPaymentResponse getBSPayPageResult(String bookingRequestId ) {
		try {
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( !dummy) {
				
				String link = "/booking/"+bookingRequestId+"/getPaymentResult";
				URI uri = getServerURI(link);

				logger.info("VehicleHttpService = "+uri.toString());
				
				String response = httpClient.sendGetRequest(uri);
				logger.info("response : "+response);
				return  JsonUtils.createResponseClassFromJson(response, VerifyCreditCardPaymentResponse.class);
			}
			else {
				VerifyCreditCardPaymentResponse resp = new VerifyCreditCardPaymentResponse();
				return resp;
			}
			
		} catch (IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;

	}
	


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
	
	public StationResponse getStations(long operatorId, int locationType, String locationId, String serchText) {
		String response = "Stations ";
		try {

			String link = "/location/stations";
			link = link + "?operator="+operatorId;
			link = link + "&type="+locationType;
			link = link +"&search="+serchText;
			if (StringUtils.isNotEmpty(locationId)) {
				link = link +"&locationId="+locationId;
			}
			
			URI uri = getServerURI(link);
			
			logger.info("getStations Request = "+uri);
			
			response =  httpClient.sendGetRequest(uri);
			logger.info("getStations response = "+response);
			return JsonUtils.createResponseClassFromJson(response, StationResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public Station getStationInfo(int stationId) {
		String response = "Stations ";
		try {

			String link = "/masterdata/station/"+stationId;
			URI uri = getServerURI(link);
			
			logger.info("getStationInfo Request = "+uri);
			
			response =  httpClient.sendGetRequest(uri);
			logger.info("getStationInfo response = "+response);
			return JsonUtils.createResponseClassFromJson(response, Station.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public CredentialResponse login(Credential credentialRequest) {

		try {
			
			boolean test = false;
			
			if ( test) {
				CredentialResponse response = new CredentialResponse();
				Clerk clerk = new Clerk();
				response.setClerk(clerk);
				response.setStatus(1);
				Credential credential = new Credential();
				credential.setReset(false);
				response.setCredential(credential);
				clerk.setName("Müller");
				clerk.setFirstName("Helmut");
				return response;
			}
			
			String link = CLUBMOBIL_CREDENTIALS+ "/login";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(credentialRequest);
			logger.info(" login request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("login response = "+response);
			return JsonUtils.createResponseClassFromJson(response, CredentialResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public CredentialResponse changePwd(Credential credentialRequest) {

		try {
			
			
			String link = CLUBMOBIL_CREDENTIALS+ "/changePwd";
			

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(credentialRequest);
			logger.info("changePwd request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("changePwd response = "+response);
			return JsonUtils.createResponseClassFromJson(response, CredentialResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public GetCustomerResponse getCustomer(String customerNo) {
		try {
			logger.info("getCustomer GETRequest customerNo = "+customerNo);
			
			
			String link = CLUBMOBIL_MASTERDATA+ "/getCustomer";
			link = link + "?memberNo="+customerNo;

			URI uri = getServerURI(link);

			String response =  httpClient.sendGetRequest(uri);
			logger.info("getCustomer response = "+response);
			return JsonUtils.createResponseClassFromJson(response, GetCustomerResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public ReservationListResponse getReservationList(String criterium) {
		try {
			
			
			String link = CLUBMOBIL_RESERVATION+ "/reservationList";
			
			ReservationListFilterRequest reservationListFilterRequest = new ReservationListFilterRequest();
			reservationListFilterRequest.setDriverName(criterium);
			reservationListFilterRequest.setDateFrom(new DayAndHour("2018-06-12", "10:00"));
			reservationListFilterRequest.setStationId(2345);
			reservationListFilterRequest.setReservationPattern("N-1223");

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(reservationListFilterRequest);
			logger.info("getReservationList request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getReservationList response = "+response);
			return JsonUtils.createResponseClassFromJson(response, ReservationListResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public ReservationResponse getReservation(Integer reservationId) {
		try {
			
			
			String link = CLUBMOBIL_RESERVATION+ "/getReservation?reservationId="+reservationId.toString();
			
			URI uri = getServerURI(link);
			
			logger.info("getReservation reservationId = "+reservationId);

			String response =  httpClient.sendGetRequest(uri);
			
			logger.info("getReservation response = "+response);
			return JsonUtils.createResponseClassFromJson(response, ReservationResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public void putPayment(com.bpcs.mdcars.model.Payment payment) {
		try {
			
			
			String link = CLUBMOBIL_RESERVATION+ "/payment/put";
			

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(payment);
			logger.info("putPayment request = "+request);

			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("putPayment response = "+response);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
	}
	
	public void putCustomer(com.bpcs.mdcars.model.Customer customer) {
		try {
			
			
			String link = CLUBMOBIL_RESERVATION+ "/customer/put";
			

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(customer);
			logger.info("putCustomer request = "+request);

			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("putCustomer response = "+response);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
	}

	
	public ReservationResponse checkOutReservation() {
		try {
			
			
			String link = CLUBMOBIL_RESERVATION+ "/checkOut";
			
			ReservationListFilterRequest reservationListFilterRequest = new ReservationListFilterRequest();

			URI uri = getServerURI(link);
			
			String dummyStr = "test";
			String request = JsonUtils.convertRequestToJsonString(dummyStr);
			logger.info("checkOutReservation request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("checkOutReservation response = "+response);
			return JsonUtils.createResponseClassFromJson(response, ReservationResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DispositionListResponse getDispositionList(GetDispoListRequest getDispoListRequest) {

		try {
			
			String link = CLUBMOBIL_DISPOSITION+ "/dispositionList";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(getDispoListRequest);
			logger.info("getDispositionList request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getDispositionList response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DispositionListResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DispositionResponse assignDisposition(DispositionDetailRequest dispositionDetailRequest) {

		try {
			
			String link = CLUBMOBIL_DISPOSITION+ "/assignDispo";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(dispositionDetailRequest);
			logger.info("assignDispo request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("assignDispo response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DispositionResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}
	
	public DispoPoolListResponse getDispoPoolList(GetDispoListRequest getDispoListRequest) {

		try {
			
			String link = CLUBMOBIL_DISPOSITION+ "/dispoPoolList";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(getDispoListRequest);
			logger.info("getDispoPoolList request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getDispoPoolList response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DispoPoolListResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DispoPoolResponse addDispoToPool(DispoPoolRequest dispoPoolRequest) {
		try {
			
			String link = CLUBMOBIL_DISPOSITION+ "/addDispoPool";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(dispoPoolRequest);
			logger.info("addDispoToPool request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("addDispoToPool response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DispoPoolResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DispoPoolResponse updateDispoToPool(DispoPoolRequest dispoPoolRequest) {
		try {
			
			String link = CLUBMOBIL_DISPOSITION+ "/updateDispoPool";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(dispoPoolRequest);
			logger.info("addDispoToPool request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("addDispoToPool response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DispoPoolResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}
	

	public ExtraResponse getEquipments() {
		ExtraResponse extraResponse =  null;
		try {

			String link = CLUBMOBIL_RESERVATION+ "/equipments";
			URI uri = getServerURI(link);

			logger.info("GetEquipments Request = "+uri);
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				extraResponse = getExtrasDummy();
			else {
				String response =  httpClient.sendGetRequest(uri);
				logger.info("response = "+response);
				extraResponse = JsonUtils.createResponseClassFromJson(response, ExtraResponse.class);
			}
			return extraResponse;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}

	}

	public ExtraResponse getReservationExtras() {
		ExtraResponse extraResponse =  null;
		try {

			String link = CLUBMOBIL_RESERVATION+ "/extras";
			URI uri = getServerURI(link);

			logger.info("GetExtras Request = "+uri);
			
			boolean dummy = TaskProperties.getTaskProperties().isUseDummy();
			if ( dummy)
				extraResponse = getExtrasDummy();
			else {
				String response =  httpClient.sendGetRequest(uri);
				logger.info("response = "+response);
				extraResponse = JsonUtils.createResponseClassFromJson(response, ExtraResponse.class);
			}
			return extraResponse;
			
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}

	}

	public ExtraResponse putExtras(List<Extra> extras) {
		
		ExtraResponse extraResponse =  null;
		
		try {
			
			String link = CLUBMOBIL_RESERVATION+ "/extras/put";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(extras);
			logger.info("putExtras request = "+request);

			String response =  httpClient.startPutRequestAsJson(uri, request);
			logger.info("putExtras response = "+response);
			extraResponse = JsonUtils.createResponseClassFromJson(response, ExtraResponse.class);
			return extraResponse;
		} catch ( Exception e) {
			logger.error(e.getMessage(),e);
			throw new VehicleServiceException(e.getMessage());
		}
	}

	public ReservationResponse checkInReservation() {
		try {
			
			
			String link = CLUBMOBIL_RESERVATION+ "/checkIn";

			URI uri = getServerURI(link);
			
			String dummyStr = "test";
			String request = JsonUtils.convertRequestToJsonString(dummyStr);
			logger.info("checkOutReservation request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("checkOutReservation response = "+response);
			return JsonUtils.createResponseClassFromJson(response, ReservationResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
		
	}

	public DefectListResponse getDefectsList(DefectListFilterRequest defectRequest) {
		try {
			
			String link = CLUBMOBIL_DEFECT+ "/defectList";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(defectRequest);
			logger.info("getDefectList request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getDefectList response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DefectListResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DefectResponse addDefect(DefectDetailRequest defectDetailRequest) {
		try {
			
			String link = CLUBMOBIL_DEFECT+ "/addDefect";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(defectDetailRequest);
			logger.info("getDefectList request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getDefectList response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DefectResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DefectResponse saveDefect(DefectDetailRequest defectDetailRequest) {
		try {
			
			String link = CLUBMOBIL_DEFECT+ "/saveDefect";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(defectDetailRequest);
			logger.info("getDefectList request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getDefectList response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DefectResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public DispositionResponse removeDisposition(DispositionDetailRequest dispoRequest) {
		try {
			
			String link = CLUBMOBIL_DISPOSITION+ "/removeDispo";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(dispoRequest);
			logger.info("removeDispo request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("removeDispo response = "+response);
			return JsonUtils.createResponseClassFromJson(response, DispositionResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public GetCMPaymentInfoResponse getPaymentInfo(GetCMPaymentInfoRequest getPaymentRequest) {
		try {
			
			String link = CLUBMOBIL_PAYMENT+ "/cmPaymentInfo";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(getPaymentRequest);
			logger.info("getPaymentInfo request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("getPaymentInfo response = "+response);
			return JsonUtils.createResponseClassFromJson(response, GetCMPaymentInfoResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	public GetCMPaymentInfoResponse addPaymentInfo(SetCMPaymentTransactionRequest addPaymentRequest) {
		try {
			
			String link = CLUBMOBIL_PAYMENT+ "/cmAddPaymentTransaction";

			URI uri = getServerURI(link);
			
			String request = JsonUtils.convertRequestToJsonString(addPaymentRequest);
			logger.info("addPaymentInfo request = "+request);

			String response =  httpClient.startPostRequestAsJson(uri, request);
			logger.info("addPaymentInfo response = "+response);
			return JsonUtils.createResponseClassFromJson(response, GetCMPaymentInfoResponse.class);
			
		} catch ( IOException e) {
			logger.error(e,e);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		return null;
	}

	
}
