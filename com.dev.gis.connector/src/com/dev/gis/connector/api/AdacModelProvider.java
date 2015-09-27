package com.dev.gis.connector.api;

import java.util.ArrayList;
import java.util.List;

import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;
import com.dev.gis.connector.sunny.OfferFilter;


public enum AdacModelProvider {
	INSTANCE;
	
	private final List<OfferDo> offerDos = new ArrayList<OfferDo>();

	private final List<OfferDo> crossOffers = new ArrayList<OfferDo>();
	
	private VehicleResponse vehicleResponse;


	private List<Extra> extras = new ArrayList<Extra>();
	
	public String agencyNo;

	public String languageCode;

	public String crossOfferOperator;
	
	public long languageId;
	
	public long operatorId;
	
	public String serverUrl;

	public OfferFilter  offerFilter;
	
	public boolean crossOfferFlag = false;;
	
	public int module = 1; 
	
	public long cityId;

	public long dropoffCityId;
	
	public String airport;
	
	public String dropoffAirport;
	
	public String supplierFilter;

	public String servcatFilter;
	
	public String stationFilter;

	public void updateExtras(ExtraResponse response) {
		extras.clear();
		extras.addAll(response.getExtras());
	}


	public List<Extra> getExtras() {
		return extras;
	}

	public void updateResponse(VehicleResponse response) {
		offerDos.clear();
		crossOffers.clear();
		if ( response == null)
			return;
		
		this.vehicleResponse = response;
		
		List<VehicleResult> results = response.getResultList();
		for ( VehicleResult vr : results) {
			if ( vr.getOfferList().size() > 0 ) {
				
				OfferDo offer = new OfferDo(vr);
				
				offerDos.add(offer);
			}
		}
		
		if ( response.getCrossOfferResultList() != null && response.getCrossOfferResultList().size() > 0 ) {
			results = response.getCrossOfferResultList();
			for ( VehicleResult vr : results) {
				if ( vr.getOfferList().size() > 0 ) {
					OfferDo offer = new OfferDo(vr);
					crossOffers.add(offer);
				}
			}
			
		}
		
	}
	
	public void clearView() {
		offerDos.clear();
		crossOffers.clear();
		this.vehicleResponse = null;
	}


	public List<OfferDo> getOfferDos() {
		return offerDos;
	}


	public List<OfferDo> getCrossOffers() {
		return crossOffers;
	}


	public VehicleResponse getVehicleResponse() {
		return vehicleResponse;
	}

	
}
