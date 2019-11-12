package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "paypalPayment")
public class PaypalTransactionInfo extends BasicProtocol {
	private String payerId;
	private String transactionId;
	private String payAmount;
	
	public PaypalTransactionInfo() {
	}
	
	public PaypalTransactionInfo(String payerId, String transactionId,String payAmount) {
		super();
		this.payerId = payerId;
		this.transactionId = transactionId;
		this.payAmount = payAmount;
	}

	public String getPayAmount() {
		return payAmount;
	}
	public String getPayerId() {
		return payerId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

}
