package com.nbb.apps.carreservationv2.base;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;


public class Payment extends BasicProtocol {
	
	private CreditCard card;
	
	private BankAccount account;
	
	private PaymentType paymentType;
	
	
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
}