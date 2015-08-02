package com.dev.gis.connector.api;

import java.util.UUID;

import com.bpcs.mdcars.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.Inclusive;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleResult;

public class OfferDo extends  com.bpcs.mdcars.protocol.Offer {
	private VehicleResult  model;
	private String         inclusiveKm = "";
	private String         group = "";
	private String         prepaid = "prepaid";
	
	private TravelInformation travelInformation;

	public OfferDo ( VehicleResult vr) {
		model = vr;
		this.setName( vr.getVehicle().getManufacturer());
		this.setBookLink(vr.getOfferList().get(0).getBookLink());
		this.setSupplierId(vr.getOfferList().get(0).getSupplierId());
		long stationId = -1;
		if ( vr.getPickUpLocation() != null )
			stationId = vr.getPickUpLocation().getStationId();
		this.setPickUpStationId(stationId);
		String preis = "";
		if ( vr.getOfferList().get(0).getPrice() != null)
			preis = vr.getOfferList().get(0).getPrice().getAmount();
		this.setPrice(new MoneyAmount(preis, "EUR"));

		UUID id = new UUID(0l, 0l);
		if ( vr.getOfferList().get(0).getId() != null)
			id = vr.getOfferList().get(0).getId();
		this.setId(id);

		
		if ( "2".equals(vr.getOfferList().get(0).getOfferedPayment()))
			prepaid = " poa ";
		
		this.setServiceCatalogCode(vr.getOfferList().get(0).getServiceCatalogCode());
		this.setServiceCatalogId(vr.getOfferList().get(0).getServiceCatalogId());
		
		for ( Inclusive incl : vr.getOfferList().get(0).getInclusives()) {
			if ( incl.getId() == 73) {
				inclusiveKm = incl.getDescription();
			}
		}
		
		group = vr.getVehicle().getVehicleGroup().getName() + "_" + vr.getVehicle().getVehicleGroup().getId();  
		
		travelInformation = new TravelInformation();
		
		travelInformation.setPickUpLocation(vr.getPickUpLocation());
		travelInformation.setDropOffLocation(vr.getDropOffLocation());
		
		
		
	}

	public VehicleResult getModel() {
		return model;
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
