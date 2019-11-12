package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlEnumValue;


public enum Availability {
	
	@XmlEnumValue(value="-1") 
	UNINITIALIZED(-1),

	@XmlEnumValue(value="0") 
	ON_REQUEST(0),
	@XmlEnumValue(value="1") 
	UNAVAILABLE(1),
	@XmlEnumValue(value="13") 
	AVAILABLE(13);

	private int ordinal;

	Availability(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal () {
		return ordinal;
	}
}
