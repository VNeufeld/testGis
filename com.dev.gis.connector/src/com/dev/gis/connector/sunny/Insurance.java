package com.dev.gis.connector.sunny;

import java.io.Serializable;

public class Insurance extends BasicProtocol  implements Serializable {

	private static final long serialVersionUID = -7417764413415129458L;

	private int id;

	private MoneyAmount price;
	
	private boolean inclusivie;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	public void setPrice(MoneyAmount price) {
		this.price = price;
	}

	public boolean isInclusivie() {
		return inclusivie;
	}

	public void setInclusivie(boolean inclusivie) {
		this.inclusivie = inclusivie;
	}
}