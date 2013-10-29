package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlEnumValue;


public enum Gender {

	@XmlEnumValue(value="-1") 
	UNINITIALIZED(-1),

	@XmlEnumValue(value="1") 
	MRS (1),
	@XmlEnumValue(value="2") 
	MR(2);
	
	Gender (int ordinal) {
		this.ordinal = ordinal;
	}
	
	int ordinal;
	
	public int getOrdinal (){
		return ordinal;
	}
}
