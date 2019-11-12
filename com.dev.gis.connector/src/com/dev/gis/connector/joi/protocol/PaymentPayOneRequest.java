package com.dev.gis.connector.joi.protocol;

public class PaymentPayOneRequest {
	public Administration getAdmin() {
		return admin;
	}
	public void setAdmin(Administration admin) {
		this.admin = admin;
	}
	public long getBookingId() {
		return bookingId;
	}
	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}
	public String getPseudoCardPan() {
		return pseudoCardPan;
	}
	public void setPseudoCardPan(String pseudoCardPan) {
		this.pseudoCardPan = pseudoCardPan;
	}
	public String getTruncatedCardPan() {
		return truncatedCardPan;
	}
	public void setTruncatedCardPan(String truncatedCardPan) {
		this.truncatedCardPan = truncatedCardPan;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardExpireDate() {
		return cardExpireDate;
	}
	public void setCardExpireDate(String cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}
	public String getCardOwner() {
		return cardOwner;
	}
	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}
	Administration admin;
	long bookingId;
	String pseudoCardPan;
	String truncatedCardPan;
	String cardType;
	String cardExpireDate;
	String cardOwner;
}
