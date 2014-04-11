package com.dev.gis.task.execution.api;

import com.bpcs.mdcars.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.VehicleResult;

public class OfferDo extends  com.bpcs.mdcars.protocol.Offer {
	private VehicleResult  model;

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
		
		this.setServiceCatalogCode(vr.getOfferList().get(0).getServiceCatalogCode());
		this.setServiceCatalogId(vr.getOfferList().get(0).getServiceCatalogId());
		
	}

	public VehicleResult getModel() {
		return model;
	}
}
