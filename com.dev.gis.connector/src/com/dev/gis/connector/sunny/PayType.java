package com.dev.gis.connector.sunny;

public enum PayType {

	UNINITIALIZED(-1),
	PREPAID (1),
	PAY_ON_ARRIVAL(2);
	
	PayType (int ordinal) {
		this.ordinal = ordinal;
	}
	
	int ordinal;
	
	public int getOrdinal (){
		return ordinal;
	}
	
	public static PayType parseInt(int value) {
		for (PayType avail : values()) {
			if (avail.ordinal == value) {
				return avail;
			}
		}
		throw new BadValueException("unknown PayType : " + value);
	}
	
	public static PayType parseString(String value) {

		try {
			if (value.equals("2") )
				return PAY_ON_ARRIVAL;
			else if (value.equals("1") )
				return PREPAID;
			return PAY_ON_ARRIVAL;
		} catch (Exception e) {
			return PAY_ON_ARRIVAL;
		}

	}
	
}
