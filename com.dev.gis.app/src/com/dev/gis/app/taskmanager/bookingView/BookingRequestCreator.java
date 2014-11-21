package com.dev.gis.app.taskmanager.bookingView;

import java.util.List;

import com.dev.gis.connector.joi.protocol.Address;
import com.dev.gis.connector.joi.protocol.BookingRequest;
import com.dev.gis.connector.joi.protocol.Customer;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Payment;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.PhoneNumber;

public class BookingRequestCreator {

	public static BookingRequest createBookingRequest(
			Offer offer, List<Extra> selectedExtras) {
		
		BookingRequest bookingRequest = new BookingRequest();

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
		return bookingRequest;
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
		address.setCity("München");
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

}
