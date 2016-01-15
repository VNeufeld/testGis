package com.dev.gis.connector.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dev.gis.connector.joi.protocol.BodyStyleText;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Station;
import com.dev.gis.connector.joi.protocol.Supplier;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;


public enum AdacModelProvider {
	INSTANCE;
	
	private final List<OfferDo> offerDos = new ArrayList<OfferDo>();

	private final List<OfferDo> crossOffers = new ArrayList<OfferDo>();
	
	private VehicleResponse vehicleResponse;

	private OfferDo selectedOffer;

	private List<Extra> extras = new ArrayList<Extra>();
	
	private final List<Extra> selectedExtras = new ArrayList<Extra>();
	
	private String bookingRequestId;
	
	public String agencyNo;

	public String memberNo;

	public String paymentType;
	
	public String languageCode;

	public String crossOfferOperator;
	
	public long languageId;
	
	public long operatorId;
	
	public String serverUrl;
	
	public boolean crossOfferFlag = false;;
	
	public int module = 1; 
	
	public long cityId;

	public long dropoffCityId;
	
	public String airport;
	
	public String dropoffAirport;
	
	public String supplierFilter;

	public String servcatFilter;
	
	public String stationFilter;
	
	public long selectedPickupStationId;
	public long selectedDropoffStationId;


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
		Map<Long, Supplier> suppliers = createSupplierMap(response);
		Map<Long, Station> stations = createStationMap(response);
		Map<Long, BodyStyleText> bodyStyles = createBodyStyleTextMap(response);
		
		
		for ( VehicleResult vr : results) {
			if ( vr.getOfferList().size() > 0 ) {
				
				//OfferDo offer = new OfferDo(vr);
				OfferDo offer = createOffer(vr,suppliers, stations, bodyStyles);
				
				offerDos.add(offer);
			}
		}
		
		if ( response.getCrossOfferResultList() != null && response.getCrossOfferResultList().size() > 0 ) {
			results = response.getCrossOfferResultList();
			for ( VehicleResult vr : results) {
				if ( vr.getOfferList().size() > 0 ) {
					OfferDo offer = createOffer(vr,suppliers, stations, bodyStyles);
					crossOffers.add(offer);
				}
			}
			
		}
		
	}
	
	private Map<Long, Station> createStationMap(VehicleResponse response) {
		Map<Long, Station> stations = new HashMap<Long, Station>(); 
		for ( Station station : response.getTexts().getStationList()) {
			stations.put(station.getId(), station);	
		}
		return stations;
	}

	private Map<Long, Station> createSupplierGroupMap(VehicleResponse response) {
		Map<Long, Station> stations = new HashMap<Long, Station>(); 
		for ( Station station : response.getTexts().getStationList()) {
			stations.put(station.getId(), station);	
		}
		return stations;
	}
	private Map<Long, BodyStyleText> createBodyStyleTextMap(VehicleResponse response) {
		Map<Long, BodyStyleText> stations = new HashMap<Long, BodyStyleText>(); 
		for ( BodyStyleText bd : response.getTexts().getBodyStyleList()) {
			stations.put(bd.getId(), bd);	
		}
		return stations;
	}
	

	private OfferDo createOffer(VehicleResult vr, Map<Long, Supplier> suppliers, Map<Long, Station> stations, Map<Long, BodyStyleText> bodyStyles) {
		OfferDo offer = new OfferDo(vr);
		offer.setPickupStation(stations.get(offer.getPickUpStationId()));
		offer.setDropoffStation(stations.get(offer.getDropOffStationId()));
		offer.setSupplier(suppliers.get(offer.getSupplierId()));
		BodyStyleText bdt = bodyStyles.get(offer.getModel().getVehicle().getBodyStyle());
		if ( bdt != null)
			offer.getModel().getVehicle().setBodyStyleText(bdt);
		
		return offer;
	}


	private Map<Long, Supplier> createSupplierMap(VehicleResponse response) {
		Map<Long, Supplier> suppliers = new HashMap<Long, Supplier>(); 
		for ( Supplier supplier : response.getTexts().getSupplierList()) {
			suppliers.put(supplier.getId(), supplier);	
		}
		return suppliers;
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


	public OfferDo getSelectedOffer() {
		return selectedOffer;
	}


	public void setSelectedOffer(OfferDo selectedOffer) {
		this.selectedOffer = selectedOffer;
	}


	public List<Extra> getSelectedExtras() {
		return selectedExtras;
	}


	public String getBookingRequestId() {
		return bookingRequestId;
	}


	public void setBookingRequestId(String bookingRequestId) {
		this.bookingRequestId = bookingRequestId;
	}

	
}
