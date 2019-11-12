package com.dev.gis.connector.sunny;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "validateCCResponse")
public class VerifyCreditCardPaymentResponse extends Response {

	private static final long serialVersionUID = 6349473317634468076L;

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
