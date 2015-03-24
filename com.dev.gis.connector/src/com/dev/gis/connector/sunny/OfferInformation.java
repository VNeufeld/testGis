package com.nbb.apps.carreservationv2.base;

import java.util.List;


public class OfferInformation {
	
	private Offer offer;
	
	public OfferInformation() {
	}

	public OfferInformation(Offer offer) {
		this.offer = offer;
	}

	public Offer getOffer() {
		return offer;
	}

	public List<Pair<String,String>> getSupplierCoditions() {
		if ( offer != null && offer.getSupplierCoditions() != null)
				return offer.getSupplierCoditions().getSupplierConditions();
		return null;
	}


}
