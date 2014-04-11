package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="payment")
public class Payment extends BasicProtocol {
	
	private CreditCard card;
	
	private BankAccount account;
	
	String paymentType;
	
	private PaypalTransactionInfo paypalTransactionInfo;
	
	
	@XmlElement(required=false)
	public BankAccount getAccount() {
		return account;
	}
	
	@XmlElement(required=false)
	public CreditCard getCard() {
		return card;
	}
	
	public String getPaymentType() {
		return paymentType;
	}
	
	@XmlElement(required=false)
	public PaypalTransactionInfo getPaypalTransactionInfo() {
		return paypalTransactionInfo;
	}
	
	public void setAccount(BankAccount account) {
		this.account = account;
	}
	
	public void setCard(CreditCard card) {
		this.card = card;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void setPaypalTransactionInfo(PaypalTransactionInfo paypalTransactionInfo) {
		this.paypalTransactionInfo = paypalTransactionInfo;
	}
}