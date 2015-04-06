package com.dev.gis.task.execution.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.dev.gis.connector.sunny.*;


public enum SunnyModelProvider {
	INSTANCE;

	private List<Hit> locationSearchHits = new ArrayList<Hit>();
	
	private List<SunnyOfferDo> offerDos = new ArrayList<SunnyOfferDo>();
	
	private List<Extra> extraDos = new ArrayList<Extra>();
	
	
	private Person driver;

	private Person customer;
	
	//private PaymentInformation  paymentInformation;
	
	private SunnyModelProvider() {
		Hit hit = new Hit();
		hit.setIdentifier("München");
		hit.setId(123l);
		hit.setType(HitType.CITY);
		
		locationSearchHits.add(hit);
	}

	
	private static Offer createOffer(String name, long supplierId, long stationId, String price) {
		Offer offer = new Offer();
		offer.setName(name);
		offer.setPickUpStationId(stationId);
		offer.setPrice(new MoneyAmount(price, "EUR"));
		offer.setSupplierId(supplierId);
		return offer;
	}
	
	private static Offer createOffer(String name, long supplierId, long stationId, String price, String servCatCode, long servCatId) {
		Offer offer = new Offer();
		offer.setName(name);
		offer.setPickUpStationId(stationId);
		offer.setPrice(new MoneyAmount(price, "EUR"));
		offer.setSupplierId(supplierId);
		offer.setServiceCatalogCode(servCatCode);
		offer.setServiceCatalogId(servCatId);;
		return offer;
	}

	public void updateHits(LocationSearchResult result) {
		locationSearchHits.clear();
		for ( HitGroup gr : result.getGroupedList()) {
			for( Hit hit : gr.getHitGroup()) {
				locationSearchHits.add(hit);
			}
		}
		
	}

	public void updateOffers(VehicleResponse response) {
		
		List<Offer> results = response.getAllOffers();
		response.getVehicles();
		
		for ( Offer offer : results) {
			Vehicle vh = foundVehicle(offer.getVehicleId(),response.getVehicles());
			SunnyOfferDo offerDo = new SunnyOfferDo(offer, vh);
			
			offerDos.add(offerDo);
		}
	}
	
//	public void updateExtras(ExtraResponse response) {
//		extraDos.clear();
//		for ( Extra vr : response.getExtras()) {
//				
//			Extra extra = new Extra();
//			extra.setName(vr.getName());
//			extra.setCode(vr.getCode());
//			extra.setId(vr.getId());
//			extra.setPrice(vr.getPrice());
//				
//			extraDos.add(extra);
//		}
//		
//	}


	private Vehicle foundVehicle(long vehicleId, List<Vehicle> vehicles) {
		for ( Vehicle v : vehicles)
			if( v.getId() == vehicleId)
				return v;
		return null;
	}


	public List<Extra> getExtraDos() {
		return extraDos;
	}

	public void setExtraDos(List<Extra> extraDos) {
		this.extraDos = extraDos;
	}


	public List<Hit> getLocationSearchHits() {
		return locationSearchHits;
	}


	public List<SunnyOfferDo> getOfferDos() {
		return offerDos;
	}

}
