package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlEnumValue;


public enum PayType {

	@XmlEnumValue(value="-1") 
	UNINITIALIZED(-1),

	@XmlEnumValue(value="1") 
	PREPAID (1),
	@XmlEnumValue(value="2") 
	PAY_ON_ARRIVAL(2);
	
	PayType (int ordinal) {
		this.ordinal = ordinal;
	}
	
	int ordinal;
	
	public int getOrdinal (){
		return ordinal;
	}
}
