package com.nbb.apps.carreservationv2.base;

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
			int i = Integer.parseInt(value);
			return parseInt(i);
		} catch (Exception e) {
			throw new BadValueException("could not interprete value as int" + value);
		}

	}
	
}
