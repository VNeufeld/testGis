package com.dev.gis.connector.sunny;

public enum Salutation {
	
	UNINITIALIZED(-1),

	DR(1),
	PROF(2),
	PROF_DR(3);

	int ordinal;

	Salutation(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOrdinal() {
		return ordinal;
	}
}
