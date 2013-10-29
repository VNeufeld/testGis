package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlEnumValue;


public enum PaymentType {

	@XmlEnumValue(value="-1") 		UNINITIALIZED(-1),

	@XmlEnumValue(value="1")		CREDIT_CARD_PAYMENT (1),
	@XmlEnumValue(value="2")		DEBIT_PAYMENT (2),
	@XmlEnumValue(value="3")		INCOICE_PAYMENT(3),
	@XmlEnumValue(value="4")		REMITTANCE(4),
	@XmlEnumValue(value="8")		PAYPAL_PAYMENT(8);
	
	PaymentType (int ordinal) {
		this.ordinal = ordinal;
	}
	
	int ordinal;
	
	public int getOrdinal (){
		return ordinal;
	}
}
