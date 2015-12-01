package com.dev.gis.connector.joi.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyCreditCardPaymentResponse extends Response {

	private boolean valid;
	
	private CreditCard card;


	

	public CreditCard getCard() {
		return card;
	}

	public boolean isValid() {
		return valid;
	}
	
	public void setCard(CreditCard card) {
		this.card = card;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
