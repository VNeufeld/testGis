package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="bookingResponse")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse extends Response {

	private boolean confirmed;

	private String bookingId = null;

	private String reservationId = null;
	
	private String supplierBookingNo;

	private MoneyAmount price;

	private PayType[] acceptedPayTypes;
	
	private BookingTotalInfo bookingTotalInfo;
	
	
	@XmlElement(required = false)
	public PayType[] getAcceptedPayTypes() {
		return acceptedPayTypes;
	}

	@XmlElement(required = false)
	public String getBookingId() {
		return bookingId;
	}

	@XmlElement(required = false)
	public MoneyAmount getPrice() {
		return price;
	}

	@XmlElement(required = false)
	public String getSupplierBookingNo() {
		return supplierBookingNo;
	}

	@XmlElement(required = false)
	public boolean isConfirmed() {
		return confirmed;
	}

	public void setAcceptedPayTypes(PayType[] acceptedPayTypes) {
		this.acceptedPayTypes = acceptedPayTypes;
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

	public BookingTotalInfo getBookingTotalInfo() {
		return bookingTotalInfo;
	}

	public void setBookingTotalInfo(BookingTotalInfo bookingTotalInfo) {
		this.bookingTotalInfo = bookingTotalInfo;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
}
