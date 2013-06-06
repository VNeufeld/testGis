package com.nbb.apps.carreservationv2.base;



public enum Availability {
	
	UNINITIALIZED(-1),

	ON_REQUEST(0),
	UNAVAILABLE(1),
	AVAILABLE(13);

	private int ordinal;

	Availability(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal () {
		return ordinal;
	}
	
	public static Availability parseInt (int value) {
		for (Availability avail : values()) {
			if (avail.ordinal == value) {
				return avail;
			}
		}
		throw new BadValueException("unknown availability : " + value);
	}
	
	public static Availability parseString (String value) {
		try {
			int i = Integer.parseInt(value);
			return parseInt(i);
		} catch (Exception e) {
			throw new BadValueException("could not interprete value as int" +value);
		}
		
	}
	
}
