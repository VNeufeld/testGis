package com.dev.gis.connector.sunny;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


public class Payment extends BasicProtocol {
	
	private CreditCard card;
	
	private BankAccount account;
	
	private PaymentType paymentType;
	
    private String    creditCardPaymentReference;
    
    private boolean   paymentConfirmed;

	
	public BankAccount getAccount() {
		return account;
	}
	
	public CreditCard getCard() {
		return card;
	}
	
	@JsonSerialize(using = PaymentTypeJsonSerializer.class)
	public PaymentType getPaymentType() {
		return paymentType;
	}
	
	public void setAccount(BankAccount account) {
		this.account = account;
	}
	
	public void setCard(CreditCard card) {
		this.card = card;
	}
	
	@JsonDeserialize(using = PaymentTypeJsonDesirializer.class)
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getCreditCardPaymentReference() {
		return creditCardPaymentReference;
	}

	public void setCreditCardPaymentReference(String creditCardPaymentReference) {
		this.creditCardPaymentReference = creditCardPaymentReference;
	}

	public boolean isPaymentConfirmed() {
		return paymentConfirmed;
	}

	public void setPaymentConfirmed(boolean paymentConfirmed) {
		this.paymentConfirmed = paymentConfirmed;
	}
}