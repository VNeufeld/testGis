package com.nbb.apps.carreservationv2.base;

public class CreditCard extends BasicProtocol {

	private String cardNumber = "";

	private String cardVendor = "";

	private String cardType = "";

	private String cardMonth = "";

	private String cardYear = "";

	private String cardAliasNo = "";

	private int cardCvc;

	private String ownerName = "";

	private String cardTresorNo = "";



	public String getCardAliasNo() {
		return cardAliasNo;
	}

	public int getCardCvc() {
		return cardCvc;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getCardTresorNo() {
		return cardTresorNo;
	}

	public String getCardType() {
		return cardType;
	}

	public String getCardVendor() {
		return cardVendor;
	}

	public String getCardYear() {
		return cardYear;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setCardAliasNo(String cardAliasNo) {
		this.cardAliasNo = cardAliasNo;
	}

	public void setCardCvc(int cardCvc) {
		this.cardCvc = cardCvc;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public void setCardNumber(String cardNuString) {
		this.cardNumber = cardNuString;
	}

	public void setCardTresorNo(String cardTresorNo) {
		this.cardTresorNo = cardTresorNo;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setCardVendor(String cardVendor) {
		this.cardVendor = cardVendor;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}