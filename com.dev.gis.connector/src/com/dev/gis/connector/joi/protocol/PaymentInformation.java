package com.dev.gis.connector.joi.protocol;

public class PaymentInformation {
	
	private String paymentType;
	
	private boolean isProvided;

	
	
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public boolean isProvided() {
		return isProvided;
	}

	public void setProvided(boolean isProvided) {
		this.isProvided = isProvided;
	}
}