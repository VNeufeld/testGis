package com.dev.gis.connector.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev.gis.connector.sunny.Extra;
import com.dev.gis.connector.sunny.Hit;
import com.dev.gis.connector.sunny.HitGroup;
import com.dev.gis.connector.sunny.HitType;
import com.dev.gis.connector.sunny.Inclusive;
import com.dev.gis.connector.sunny.LocationSearchResult;
import com.dev.gis.connector.sunny.MoneyAmount;
import com.dev.gis.connector.sunny.Offer;
import com.dev.gis.connector.sunny.OfferFilter;
import com.dev.gis.connector.sunny.Person;
import com.dev.gis.connector.sunny.Station;
import com.dev.gis.connector.sunny.StationResponse;
import com.dev.gis.connector.sunny.Supplier;
import com.dev.gis.connector.sunny.Text;
import com.dev.gis.connector.sunny.Vehicle;
import com.dev.gis.connector.sunny.VehicleResponse;


public enum SunnyModelProvider {
	INSTANCE;

	private List<Hit> locationSearchHits = new ArrayList<Hit>();
	
	private List<SunnyOfferDo> offerDos = new ArrayList<SunnyOfferDo>();

	private List<SunnyOfferDo> recommendations = new ArrayList<SunnyOfferDo>();
	
	private List<Extra> extras = new ArrayList<Extra>();

	private List<Inclusive> inclusives = new ArrayList<Inclusive>();
	
	private List<Station> pickupStations = new ArrayList<Station>();

	private List<Station> dropoffStations = new ArrayList<Station>();
	
	public String agencyNo;
	
	public String supplierFilter;

	public String servcatFilter;

	public String stationFilter;
	
	public long operatorId;
	
	public String serverUrl;
	
	public VehicleResponse  currentResponse;

	public OfferFilter  offerFilter;
	
	private Person driver;

	private Person customer;
	
	public long cityId;
	
	public String airport;

	
	//private PaymentInformation  paymentInformation;
	
	private SunnyModelProvider() {
		Hit hit = new Hit();
		hit.setIdentifier("München");
		hit.setId(123l);
		hit.setType(HitType.CITY);
		
		locationSearchHits.add(hit);
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
		recommendations.clear();

		if (response == null )
			return;
		
		currentResponse = response;
		
		offerDos.addAll(createOffersDo(response.getAllOffers(), response));
		
		recommendations.addAll(createOffersDo(response.getRecommendations(), response));

	}
	
	private List<SunnyOfferDo> createOffersDo(List<Offer> results, VehicleResponse response) {
		Map<Long, Supplier> suppliers = createSupplierMap(response);
		List<SunnyOfferDo> offers = new ArrayList<SunnyOfferDo>();
		for ( Offer offer : results) {
			Vehicle vh = foundVehicle(offer.getVehicleId(),response.getVehicles());
			setBodyStyle(vh,response);
			
			Station pickupStation = findStation(offer.getPickUpStationId(),suppliers, response);
			Station dropOffStation = findStation(offer.getDropOffStationId(),suppliers, response);
			SunnyOfferDo offerDo = new SunnyOfferDo(offer, vh, pickupStation, dropOffStation);
			
			
			offers.add(offerDo);
		}
		return offers;
	}

	
	private void setBodyStyle(Vehicle vh, VehicleResponse response) {
		if ( response.getTexts() != null && response.getTexts().getBodyStyleMap() != null ) {
			Text text = response.getTexts().getBodyStyleMap().get(vh.getBodyStyle());
			if ( text != null)
				vh.setBodyStyleText(text.getText());
		}
		
	}


	private Station findStation(long pickUpStationId, Map<Long, Supplier> suppliers, VehicleResponse response) {
		for ( Station station : response.getTexts().getStationList()) {
			if ( station.getId() == pickUpStationId ) {
				station.setSupplier(suppliers.get(station.getSupplierId()));
				return station;
			}
		}
	
		return null;
	}


	private Map<Long, Supplier> createSupplierMap(VehicleResponse response) {
		Map<Long, Supplier> suppliers = new HashMap<Long, Supplier>(); 
		for ( Supplier supplier : response.getTexts().getSupplierList()) {
			suppliers.put(supplier.getId(), supplier);	
		}
		return suppliers;
	}


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
