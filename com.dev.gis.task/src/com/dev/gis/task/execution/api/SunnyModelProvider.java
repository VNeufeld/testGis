package com.dev.gis.task.execution.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.dev.gis.connector.sunny.*;


public enum SunnyModelProvider {
	INSTANCE;

	private List<Hit> locationSearchHits = new ArrayList<Hit>();
	
	private List<SunnyOfferDo> offerDos = new ArrayList<SunnyOfferDo>();

	private List<SunnyOfferDo> recommendations = new ArrayList<SunnyOfferDo>();
	
	private List<Extra> extras = new ArrayList<Extra>();

	private List<Inclusive> inclusives = new ArrayList<Inclusive>();
	
	private List<Station> pickupStations = new ArrayList<Station>();

	private List<Station> dropoffStations = new ArrayList<Station>();
	
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
		offerDos.clear();
		List<Offer> results = response.getAllOffers();
		response.getVehicles();
		
		
		for ( Offer offer : results) {
			Vehicle vh = foundVehicle(offer.getVehicleId(),response.getVehicles());
			Station pickupStation = findStation(offer.getPickUpStationId(), response);
			Station dropOffStation = findStation(offer.getDropOffStationId(), response);
			SunnyOfferDo offerDo = new SunnyOfferDo(offer, vh, pickupStation, dropOffStation);
			
			offerDos.add(offerDo);
		}
	}
	
private Station findStation(long pickUpStationId, VehicleResponse response) {

		for ( Station station : response.getTexts().getStationList()) {
			if ( station.getId() == pickUpStationId )
				return station;
		}
	
		return null;
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




	public List<Hit> getLocationSearchHits() {
		return locationSearchHits;
	}


	public List<SunnyOfferDo> getOfferDos() {
		return offerDos;
	}


	public void updateInclusives(SunnyOfferDo offer) {
		this.inclusives.clear();
		if ( offer.getInclusives() != null)
			this.inclusives.addAll(offer.getInclusives());
		
	}


	public List<Inclusive> getInclusives() {
		return inclusives;
	}


	public void updateExtras(SunnyOfferDo offer) {
		this.extras.clear();
		if ( offer.getExtras() != null)
			this.extras.addAll(offer.getExtras());
		
	}


	public List<Extra> getExtras() {
		return extras;
	}


	public void updateRecmmendations(VehicleResponse response) {
		recommendations.clear();
			
		List<Offer> results = response.getRecommendations();
		if ( results == null)
			return;
		
		for ( Offer offer : results) {
			Vehicle vh = foundVehicle(offer.getVehicleId(),response.getVehicles());
			Station pickupStation = findStation(offer.getPickUpStationId(), response);
			Station dropOffStation = findStation(offer.getDropOffStationId(), response);
			SunnyOfferDo offerDo = new SunnyOfferDo(offer, vh, pickupStation, dropOffStation);
			
			recommendations.add(offerDo);
		}
		
	}


	public List<SunnyOfferDo> getRecommendations() {
		return recommendations;
	}

	public void updatePickupStations(StationResponse response) {
		this.pickupStations.clear();
		if ( response.getStations() != null)
			this.pickupStations.addAll(response.getStations());
		
	}

	public void updateDropoffStations(StationResponse response) {
		this.dropoffStations.clear();
		if ( response.getStations() != null)
			this.dropoffStations.addAll(response.getStations());
		
	}
	
	public List<Station> getPickupStations() {
		return pickupStations;
	}


	public List<Station> getDropoffStations() {
		return dropoffStations;
	}

}
