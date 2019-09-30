package com.dev.gis.connector.sunny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingResponse extends Response {

	private boolean confirmed;

	private String bookingId;

	private String supplierBookingNo;

	private MoneyAmount price;
	
	private MoneyAmount expectedPoaTotalPrice;
	
	private MoneyAmount oneWayFeeInSellCurrency;
	
	private MoneyAmount oneWayFee;
	
	private double exchangeRate;

	private MoneyAmount extraPriceInSellCurrency;

	private MoneyAmount extraPrice;
	

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

	public MoneyAmount getExpectedPoaTotalPrice() {
		return expectedPoaTotalPrice;
	}

	public void setExpectedPoaTotalPrice(MoneyAmount expectedPoaTotalPrice) {
		this.expectedPoaTotalPrice = expectedPoaTotalPrice;
	}

	public MoneyAmount getOneWayFeeInSellCurrency() {
		return oneWayFeeInSellCurrency;
	}

	public void setOneWayFeeInSellCurrency(MoneyAmount oneWayFeeInSellCurrency) {
		this.oneWayFeeInSellCurrency = oneWayFeeInSellCurrency;
	}

	public MoneyAmount getOneWayFee() {
		return oneWayFee;
	}

	public void setOneWayFee(MoneyAmount oneWayFee) {
		this.oneWayFee = oneWayFee;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public MoneyAmount getExtraPriceInSellCurrency() {
		return extraPriceInSellCurrency;
	}

	public void setExtraPriceInSellCurrency(MoneyAmount extraPriceInSellCurrency) {
		this.extraPriceInSellCurrency = extraPriceInSellCurrency;
	}

	public MoneyAmount getExtraPrice() {
		return extraPrice;
	}

	public void setExtraPrice(MoneyAmount extraPrice) {
		this.extraPrice = extraPrice;
	}
}
