package com.nbb.apps.carreservationv2.conections;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.nbb.apps.carreservationv2.base.Address;
import com.nbb.apps.carreservationv2.base.Administration;
import com.nbb.apps.carreservationv2.base.Agency;
import com.nbb.apps.carreservationv2.base.BankAccount;
import com.nbb.apps.carreservationv2.base.CreditCard;
import com.nbb.apps.carreservationv2.base.Customer;
import com.nbb.apps.carreservationv2.base.DayAndHour;
import com.nbb.apps.carreservationv2.base.GeoLocation;
import com.nbb.apps.carreservationv2.base.Hit;
import com.nbb.apps.carreservationv2.base.HitType;
import com.nbb.apps.carreservationv2.base.Insurance;
import com.nbb.apps.carreservationv2.base.Location;
import com.nbb.apps.carreservationv2.base.OfferFilter;
import com.nbb.apps.carreservationv2.base.Payment;
import com.nbb.apps.carreservationv2.base.PaymentType;
import com.nbb.apps.carreservationv2.base.Person;
import com.nbb.apps.carreservationv2.base.PhoneNumber;
import com.nbb.apps.carreservationv2.base.TravelInformation;
import com.nbb.apps.carreservationv2.base.VehicleRequest;


public class TestUtil {
	private static Random random = new Random(System.currentTimeMillis());
//	private final DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("HH:mm");
//	private final DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
	public static final SimpleDateFormat TIME_FORMAT_OUT = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat DATE_FORMAT_OUT = new SimpleDateFormat("yyyy-MM-dd");

	
	public Person createDriver() {
		Person driver = new Person();
		driver.setFirstName("Jonny");
		driver.setName("Müller_"+random.nextInt());
		//driver.setGender(Gender.MR);
		DayAndHour ah = new DayAndHour();
		ah.setDate("1965-02-11");
		ah.setTime("15:40");
		
		driver.setBirthday(ah);
		//driver.setComment("bitte unter nummer 0049172 / 111117778");
		//driver.setComment(" ");
		return driver;

	}
	public static Payment createPayment() {
		Payment payment = new Payment();
		
		BankAccount bankAccount = new BankAccount();
		payment.setAccount(bankAccount);
		
		bankAccount.setAccount("123433212234");
		bankAccount.setBankCode("70020000");
		bankAccount.setBankName("HVB");
		bankAccount.setOwnerName("Meier");
		
		payment.setPaymentType(PaymentType.INVOICE_PAYMENT);
		
		CreditCard creditCard = new CreditCard();
		creditCard.setCardCvc(123);
		creditCard.setCardMonth("11");
		creditCard.setCardYear("2015");
		creditCard.setCardNumber("987654321234");
		creditCard.setCardType("Mastercard");
		creditCard.setCardVendor("X");
		payment.setCard(creditCard);
		
		return payment;
	}

	
	public static Customer createTestCustomer() {
		Customer customer = new Customer();
		
		customer.setEMail("vn@bpcs.com");
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCountry("0049");
		phoneNumber.setPrefix("89");
		phoneNumber.setExtension("427420");
		customer.setPhone(phoneNumber);
		
		Address address = new Address();
		address.setCity("München");
		address.setCountry("Germany");
		address.setCountryId(1);
		address.setStreet("Schönstr");
		address.setZip("81543");
		
		customer.setEMail("vn@bpcs.com");
		customer.setAddress(address);
		
		Person person = new Person();
		person.setName("Müller");
		person.setFirstName("John");

		DayAndHour birthday = new DayAndHour();
		birthday.setDate("1963-02-11");
		birthday.setTime("00:00");
		
		person.setBirthday(birthday);

		customer.setPerson(person);
		return customer;
		
	}

	public VehicleRequest createVehicleRequest(Hit location, Calendar puDate, String language, String operator) {

		VehicleRequest request = new VehicleRequest();
		
		createAdministrator(request,operator,language);
		
		// Customer
		//createCustomer(request);
		
		createAgency(request);
		// service catalog ID
		//request.setServiceCatalogId("4");
		
//		request.setPayment(PayType.PREPAID);

		
		TravelInformation travelInformation = new TravelInformation();
		
		Location pickUpLocation = new Location();
		
		if ( location.getType() == HitType.CITY) {
			GeoLocation geoLocation = new GeoLocation();
			geoLocation.setCity(location.getId());
			pickUpLocation.setGeo(geoLocation);
		} else if ( location.getType() == HitType.AIRPORT) {
			pickUpLocation.setAirport("PMI");
		}
		
		Calendar pickupDate = puDate;

		DayAndHour pickUp = new DayAndHour();
		pickUp.setDate(DATE_FORMAT_OUT.format(pickupDate.getTime()));
		pickUp.setTime("15:40");
		
		travelInformation.setPickUpTime(pickUp);
		
		Calendar dropoffDate = Calendar.getInstance();
		dropoffDate.setTime(pickupDate.getTime());
		dropoffDate.add(Calendar.DAY_OF_MONTH, 7);
		
		DayAndHour ah = new DayAndHour();
		ah.setDate(DATE_FORMAT_OUT.format(dropoffDate.getTime()));
		ah.setTime(TIME_FORMAT_OUT.format(dropoffDate.getTime()));
		travelInformation.setDropOffTime(ah);
		
		Location dropoffLocation = new Location();

		travelInformation.setDropOffLocation(dropoffLocation);
		travelInformation.setPickUpLocation(pickUpLocation);
		
		request.setTravel(travelInformation);
		
		return request;
	}

	private void createAgency(VehicleRequest request) {
		Agency agency = new Agency();
		
//		<com:AgencyId>188233</com:AgencyId>
//		<com:AgencyNo>79223</com:AgencyNo>
//		<com:TerminalId>003421002</com:TerminalId>
//		<com:TravelAgentBooking>0</com:TravelAgentBooking>
		
		agency.setAgencyId(Long.valueOf(18381));
		agency.setTravelAgentId(Long.valueOf(130046));
		
//		agency.setAgencyNo(79223);
//		agency.setTerminalId(3421002);
//		agency.setTravelAgentBooking(false);
		request.setAgency(agency);
		
	}

	/**
	 * @param request
	 * @param language 
	 * @param operator 
	 */
	private void createAdministrator(VehicleRequest request, String operator, String language) {
		Administration administration = new Administration();
		administration.setBroker(true);
		administration.setBrokerId("12");
		administration.setCalledFrom(5);
		administration.setLanguage(language);
		administration.setProvider("WEB");
		administration.setProviderId(15);
		
		administration.setOperator(Integer.valueOf(operator));
		administration.setSalesChannel(3);
		request.setAdministration(administration);
	}

	/**
	 * @param request
	 */
	private void createCustomer(VehicleRequest request) {
		Customer customer = new Customer();
		
		customer.setCustomerNo("56789");
		customer.setEMail("vn@bpcs.com");
		
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setCountry("0049");
		phoneNumber.setPrefix("89");
		phoneNumber.setExtension("427420");
		customer.setMobile(phoneNumber);
		
		Address address = new Address();
		address.setCity("M�nchen");
		address.setCountry("Germany");
		
		customer.setEMail("vn@bpcs.com");
		customer.setAddress(address);
		request.setCustomer(customer);
	}

	public static List<Insurance> createInsurancesDummy() {
		List<Insurance> result = new ArrayList<Insurance>();
		Insurance insurance = new Insurance();
		insurance.setId(123);
		//insurance.setPrice(new MoneyAmount("123","EUR"));
		result.add(insurance);

		insurance = new Insurance();
		insurance.setId(125);
		//insurance.setPrice(new MoneyAmount("125,36","EUR"));
		result.add(insurance);
		
		return result;
	}


	
	public static OfferFilter createOfferFilter() {
		
		OfferFilter filter = new OfferFilter();
		filter.setBodyStyles(new Long[] { 1l });
		return filter;
	}

}
