package com.dev.gis.task.execution.api;

import java.util.List;

import com.dev.gis.connector.sunny.*;

public class SunnyOfferDo extends  Offer {
	private String         inclusiveKm = "";
	private String         group = "";
	private String         prepaid = "prepaid";
	
	private TravelInformation travelInformation;

	public SunnyOfferDo ( Offer offer, Vehicle vh) {
		
		this.setName( vh.getManufacturer());
		this.setBookLink(offer.getBookLink());
		this.setSupplierId(offer.getSupplierId());
		long stationId = offer.getPickUpStationId();
		this.setPickUpStationId(stationId);
		String preis = offer.getPrice().getAmount();
		this.setPrice(new MoneyAmount(preis, "EUR"));
		
		if ( "2".equals(offer.getOfferedPayment()))
			prepaid = " poa ";
		
		this.setServiceCatalogCode(offer.getServiceCatalogCode());
		this.setServiceCatalogId(offer.getServiceCatalogId());
		
		for ( Inclusive incl : offer.getInclusives()) {
			if ( incl.getId() == 73) {
				inclusiveKm = incl.getDescription();
			}
		}
		
		group = vh.getACRISS();  
		
	}

	public String getInclusiveKm() {
		return inclusiveKm;
	}

	public String getGroup() {
		return group;
	}

	public String getPrepaid() {
		return prepaid;
	}

	public TravelInformation getTravelInformation() {
		return travelInformation;
	}

	public void setTravelInformation(TravelInformation travelInformation) {
		this.travelInformation = travelInformation;
	}
}
