package com.dev.gis.connector.sunny;

public enum PaymentType {

	UNINITIALIZED(-1),
	
	CREDIT_CARD_PAYMENT(1), 
	DEBIT_PAYMENT(2), 
	AGENCY_PAYMENT(3),
	IDEAL_PAYMENT(4),
	MRCASH_PAYMENT(8);
	

	PaymentType(int ordinal) {
		this.ordinal = ordinal;
	}

	int ordinal;

	public int getOrdinal() {
		return ordinal;
	}

	public static PaymentType parseInt(int value) {
		for (PaymentType avail : values()) {
			if (avail.ordinal == value) {
				return avail;
			}
		}
		throw new BadValueException("unknown availability : " + value);
	}

	public static PaymentType parseString(String value) {

		try {
			int i = Integer.parseInt(value);
			return parseInt(i);
		} catch (Exception e) {
			throw new BadValueException("could not interprete value as int" + value);
		}

	}
}
