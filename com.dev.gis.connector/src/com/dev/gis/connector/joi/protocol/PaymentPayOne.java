package com.dev.gis.connector.joi.protocol;

import com.dev.gis.connector.api.AdacModelProvider;

public class PaymentPayOne {
	public long getBookingRequestId() {
		return bookingRequestId;
	}
	public void setBookingRequestId(long bookingRequestId) {
		this.bookingRequestId = bookingRequestId;
	}
	public String getpCardPan() {
		return pCardPan;
	}
	public void setpCardPan(String pCardPan) {
		this.pCardPan = pCardPan;
	}
	public String gettCardPan() {
		return tCardPan;
	}
	public void settCardPan(String tCardPan) {
		this.tCardPan = tCardPan;
	}
	public String getCardTypeString() {
		return cardTypeString;
	}
	public void setCardTypeString(String cardTypeString) {
		this.cardTypeString = cardTypeString;
	}
	public String getCardExpire() {
		return cardExpire;
	}
	public void setCardExpire(String cardExpire) {
		this.cardExpire = cardExpire;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	long bookingRequestId;
	String pCardPan;
	String tCardPan;
	String cardTypeString;
	String cardExpire;
	String owner;
}
