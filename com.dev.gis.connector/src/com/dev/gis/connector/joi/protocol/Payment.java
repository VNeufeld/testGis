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
	
	Integer paymentType;
	
	private PaypalTransactionInfo paypalTransactionInfo;
	
	
	@XmlElement(required=false)
	public BankAccount getAccount() {
		return account;
	}
	
	@XmlElement(required=false)
	public CreditCard getCard() {
		return card;
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


	public void setPaypalTransactionInfo(PaypalTransactionInfo paypalTransactionInfo) {
		this.paypalTransactionInfo = paypalTransactionInfo;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
}