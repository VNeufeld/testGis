package com.dev.gis.connector.sunny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse extends Response {

	private boolean confirmed;

	private String bookingId;

	private String supplierBookingNo;

	private MoneyAmount price;

	@JsonDeserialize(using = AvailabilityJsonDesirializer.class)
	private Availability status;
	
	public String getBookingId() {
		return bookingId;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	public String getSupplierBookingNo() {
		return supplierBookingNo;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public void setPrice(MoneyAmount price) {
		this.price = price;
	}

	public void setSupplierBookingNo(String supplierBookingNo) {
		this.supplierBookingNo = supplierBookingNo;
	}

	public Availability getStatus() {
		return status;
	}

	public void setStatus(Availability status) {
		this.status = status;
	}
}
