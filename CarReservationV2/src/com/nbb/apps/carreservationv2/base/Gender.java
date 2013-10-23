package com.nbb.apps.carreservationv2.base;



public enum Gender {

	UNINITIALIZED(-1),

	MRS (1),
	MR(2);
	
	Gender (int ordinal) {
		this.ordinal = ordinal;
	}
	
	int ordinal;
	
	public int getOrdinal (){
		return ordinal;
	}
}
