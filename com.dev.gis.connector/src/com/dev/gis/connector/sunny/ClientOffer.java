package com.dev.gis.connector.sunny;

public class ClientOffer {
	public Offer offer;
	public Vehicle vehicle;
	public String otherInformation;
	
	
	
	public ClientOffer(Offer offer) {
		this.offer = offer;
	}



	@Override
	public String toString() {
		return "ClientOffer [offer=" + offer + "]";
	}

}
