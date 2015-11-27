package com.dev.gis.connector.api;

import java.util.UUID;

import com.bpcs.mdcars.protocol.MoneyAmount;
import com.dev.gis.connector.joi.protocol.Inclusive;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.Supplier;
import com.dev.gis.connector.joi.protocol.TravelInformation;
import com.dev.gis.connector.joi.protocol.VehicleResult;

public class OfferDo extends  Offer {
	private VehicleResult  model;
	private String         inclusiveKm = "";
	private String         group = "";
	private String         prepaid = "prepaid";
	private MoneyAmount    price;
	
	private Offer   offer;

	private long pickUpStationId;
	private long dropOffStationId;
	
	private Station pickupStation;

	private Station dropoffStation;
	
	private Supplier supplier;
	
	private String name;
	
	private TravelInformation travelInformation;

	public OfferDo ( VehicleResult vr) {
		model = vr;
		this.setName( vr.getVehicle().getManufacturer());
		
		this.offer = vr.getOfferList().get(0);
		
		this.setLink(vr.getOfferList().get(0).getLink());
		this.setBookLink(vr.getOfferList().get(0).getBookLink());
		this.setSupplierId(vr.getOfferList().get(0).getSupplierId());
		long stationId = -1;
		if ( vr.getPickUpLocation() != null )
			stationId = vr.getPickUpLocation().getStationId();
		this.setPickUpStationId(stationId);
		if ( vr.getDropOffLocation() != null)
			this.setDropOffStationId(vr.getDropOffLocation().getStationId());
		
		if ( vr.getOfferList().get(0).getPrice() != null)
			this.setPrice(vr.getOfferList().get(0).getPrice());

		UUID id = new UUID(0l, 0l);
		if ( vr.getOfferList().get(0).getId() != null)
			id = vr.getOfferList().get(0).getId();
		this.setId(id);
		
		if ( "PAY_ON_ARRIVAL".equals(vr.getOfferList().get(0).getOfferedPayment()))
			prepaid = " poa ";
		
		this.setServiceCatalogCode(vr.getOfferList().get(0).getServiceCatalogCode());
		this.setServiceCatalogId(vr.getOfferList().get(0).getServiceCatalogId());
		
		for ( Inclusive incl : vr.getOfferList().get(0).getInclusives()) {
			if ( incl.getItemClassCode().equals("KM")) {
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



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPickUpStationId() {
		return pickUpStationId;
	}

	public void setPickUpStationId(long pickUpStationId) {
		this.pickUpStationId = pickUpStationId;
	}

	public long getDropOffStationId() {
		return dropOffStationId;
	}

	public void setDropOffStationId(long dropOffStationId) {
		this.dropOffStationId = dropOffStationId;
	}

	public Offer getOffer() {
		return offer;
	}

	public Station getPickupStation() {
		return pickupStation;
	}

	public Station getDropoffStation() {
		return dropoffStation;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public void setPickupStation(Station pickupStation) {
		this.pickupStation = pickupStation;
	}

	public void setDropoffStation(Station dropoffStation) {
		this.dropoffStation = dropoffStation;
	}

}
