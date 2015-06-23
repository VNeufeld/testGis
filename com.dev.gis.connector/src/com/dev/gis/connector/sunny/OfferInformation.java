package com.dev.gis.connector.sunny;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class OfferInformation {
	
	private Offer offer;

	private SupplierCoditions supplierCoditions;
	
	public OfferInformation() {
	}

	public OfferInformation(Offer offer) {
		this.offer = offer;
	}

	public Offer getOffer() {
		return offer;
	}
	
	@JsonProperty("supplierCoditions")
	public SupplierCoditions getSupplierCoditions() {
		return offer.getSupplierCoditions();
	}


}
