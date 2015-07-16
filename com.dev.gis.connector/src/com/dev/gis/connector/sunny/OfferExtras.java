package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.List;

public class OfferExtras extends BasicProtocol {

	private String offerId;
	private List<Long> extrasList = new ArrayList<Long>();
	
	public String getOfferId() {
		return offerId;
	}
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	public List<Long> getExtrasList() {
		return extrasList;
	}

}
