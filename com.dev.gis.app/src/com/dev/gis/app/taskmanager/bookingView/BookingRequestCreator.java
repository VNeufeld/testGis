package com.dev.gis.app.taskmanager.bookingView;

import java.util.ArrayList;
import java.util.List;

import com.dev.gis.connector.api.ClubMobilModelProvider;
import com.dev.gis.connector.joi.protocol.Address;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.CMVerifyRequest;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Payment;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.PhoneNumber;

public class BookingRequestCreator {

	public static BookingRequest createBookingRequest(
			Offer offer, List<Extra> selectedExtras, String memberNo) {
		
		BookingRequest bookingRequest = new BookingRequest();

		Customer customer = createCustomer(memberNo);
		
		bookingRequest.setCustomer(customer);
		
		Person driver = createDriver();
		
		bookingRequest.setDriver(driver);
		Payment payment = new Payment();
		payment.setPaymentType(1);
		
		//payment.setPaymentType(PaymentType.PAYPAL_PAYMENT);
		
		//bookingRequest.setAcceptedAvailability("13");
		bookingRequest.setFlightNo("LH4711");
		bookingRequest.setTransferType(1);
		bookingRequest.setPriceLimit(new MoneyAmount("1000, 00","EUR"));
		//bookingRequest.setPayment(payment);
		
		bookingRequest.setExtras(selectedExtras);
		return bookingRequest;
	}
	
	public static CMVerifyRequest createVerifyRequest(
			Offer offer, List<Extra> selectedExtras, String memberNo) {
		
		CMVerifyRequest bookingRequest = new CMVerifyRequest();

//		Customer customer = createCustomer(memberNo);
//		
//		bookingRequest.setCustomer(customer);
//		
//		Person driver = createDriver();
//		
//		bookingRequest.setDriver(driver);
//		Payment payment = new Payment();
//		payment.setPaymentType(1);
		
		//payment.setPaymentType(PaymentType.PAYPAL_PAYMENT);
		
		//bookingRequest.setAcceptedAvailability("13");
//		bookingRequest.setFlightNo("LH4711");
//		bookingRequest.setTransferType(1);
//		bookingRequest.setPriceLimit(new MoneyAmount("1000, 00","EUR"));
		//bookingRequest.setPayment(payment);
		bookingRequest.setCustomer(ClubMobilModelProvider.INSTANCE.customer);
		bookingRequest.setDriver(ClubMobilModelProvider.INSTANCE.driver);
		if ( selectedExtras != null && !selectedExtras.isEmpty()) {
			List<com.bpcs.mdcars.model.Extra> extras = convert(selectedExtras);
			bookingRequest.setExtras(extras);
		}
		return bookingRequest;
	}
	
	private static List<com.bpcs.mdcars.model.Extra> convert(List<Extra> selectedExtras) {
		if ( selectedExtras != null && !selectedExtras.isEmpty()) {
			List<com.bpcs.mdcars.model.Extra> extras = new ArrayList<com.bpcs.mdcars.model.Extra>();
			for ( Extra extra : selectedExtras) {
				com.bpcs.mdcars.model.Extra ex = new com.bpcs.mdcars.model.Extra();
				ex.setId(extra.getId());
				extras.add(ex);
			}
			return extras;
		}
		return null;
	}

	private static Person createDriver() {
		Person driver = new Person();
		driver.setName("Meier");
		driver.setFirstName("Anton");
		
		return driver;
	}

	private static Customer createCustomer(String memberNo) {
		Customer customer = new Customer();
		Person person = createDriver();
		customer.setPerson(person);
		Address address = new Address();
		address.setCity("Muenchen");
		address.setStreet("Street");
		address.setZip("81543");
		address.setCountry("DE");
		address.setCountryId(Long.valueOf(49));
		customer.setAddress(address);
		customer.setExternalCustomerNo(memberNo);
		customer.setEMail("John-Appleseed@mac.com");
		PhoneNumber ph = new PhoneNumber();
		ph.setPrefix("");
		ph.setExtension("8885555512");
		customer.setPhone(ph);
		return customer;
	}

}
