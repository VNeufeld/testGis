package com.dev.gis.task.execution.api;

import java.util.List;

import com.dev.gis.connector.sunny.*;

public class SunnyOfferDo extends  Offer {
	private String         inclusiveKm = "";
	private String         group = "";
	private String         prepaid = "prepaid";
	private String 		   carDescription;
	
	
	
	private TravelInformation travelInformation;
	
	private final Vehicle vehicle;
	private final Station pickupStation;
	private final Station dropOffStation;

	public SunnyOfferDo ( Offer offer, Vehicle vh, Station pickupStation, Station dropOffStation) {
		
		vehicle  = vh;
		this.pickupStation = pickupStation;
		this.dropOffStation = dropOffStation;
		
		this.setName( offer.getName());
		if ( vh != null)
			carDescription = vh.getManufacturer()+ " "+vh.getType()+ " "+vh.getVehicleModel();
		else 
			carDescription = "????";
		this.setBookLink(offer.getBookLink());
		this.setLink(offer.getLink());
		this.setSupplierId(offer.getSupplierId());
		long stationId = offer.getPickUpStationId();
		this.setPickUpStationId(stationId);
		String preis = offer.getPrice().getAmount();
		this.setPrice(new MoneyAmount(preis, "EUR"));
		this.setInclusives(offer.getInclusives());
		
		if ( "2".equals(offer.getOfferedPayment()))
			prepaid = " poa ";
		this.setId(offer.getId());
		this.setRating(offer.getRating());
		
		this.setServiceCatalogCode(offer.getServiceCatalogCode());
		this.setServiceCatalogId(offer.getServiceCatalogId());
		
		if ( offer.getInclusives() != null) {
			for ( Inclusive incl : offer.getInclusives()) {
				
				if ( "MIL".equals(incl.getItemClassCode())) {
					inclusiveKm = incl.getDescription();
				}
			}
		}
		if ( vh != null)
			group = vh.getACRISS();
		else
			group = offer.getName();
		
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

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void addOfferInformation(OfferInformation offerInformation) {
		this.setExtras(offerInformation.getOffer().getExtras());
		this.setInclusives(offerInformation.getOffer().getInclusives());
		this.setServiceCatalogCode(offerInformation.getOffer().getServiceCatalogCode());
		//this.getSupplierCoditions().(offerInformation.getOffer().getSupplierCoditions());
		
		
	}

	public Station getPickupStation() {
		return pickupStation;
	}

	public Station getDropOffStation() {
		return dropOffStation;
	}

	public String getCarDescription() {
		return carDescription;
	}
}
