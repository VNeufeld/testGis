package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlEnumValue;


public enum Salutation {
	
	@XmlEnumValue(value="-1") 
	UNINITIALIZED(-1),

	@XmlEnumValue(value="1") 
	DR(1),
	@XmlEnumValue(value="2") 
	PROF(2),
	@XmlEnumValue(value="3") 
	PROF_DR(3);

	int ordinal;

	Salutation(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}
}
