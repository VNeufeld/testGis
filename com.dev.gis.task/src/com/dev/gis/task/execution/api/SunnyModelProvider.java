package com.dev.gis.task.execution.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import com.bpcs.mdcars.protocol.Hit;
import com.bpcs.mdcars.protocol.HitGroup;
import com.bpcs.mdcars.protocol.HitType;
import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.bpcs.mdcars.protocol.MoneyAmount;
import com.bpcs.mdcars.protocol.Offer;
import com.dev.gis.connector.joi.protocol.Extra;
import com.dev.gis.connector.joi.protocol.ExtraResponse;
import com.dev.gis.connector.joi.protocol.PaymentInformation;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;

public enum SunnyModelProvider {
	INSTANCE;

	private List<Hit> locationSearchHits = new ArrayList<Hit>();

	
	private List<Extra> extraDos = new ArrayList<Extra>();
	
	
	private Person driver;

	private Person customer;
	
	private PaymentInformation  paymentInformation;
	
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
		List<VehicleResult> results = response.getResultList();
		for ( VehicleResult vr : results) {
			if ( vr.getOfferList().size() > 0 ) {
				
				OfferDo offer = new OfferDo(vr);
				
			}
		}
		
	}
	
	public void updateExtras(ExtraResponse response) {
		extraDos.clear();
		for ( Extra vr : response.getExtras()) {
				
			Extra extra = new Extra();
			extra.setName(vr.getName());
			extra.setCode(vr.getCode());
			extra.setId(vr.getId());
			extra.setPrice(vr.getPrice());
				
			extraDos.add(extra);
		}
		
	}
	

	public void update(VehicleResponse response) {
		//offers.clear();
		List<VehicleResult> results = response.getResultList();
		for ( VehicleResult vr : results) {
			if ( vr.getOfferList().size() > 0 ) {
				URI bookLink = vr.getOfferList().get(0).getBookLink();
				long supplierId = vr.getOfferList().get(0).getSupplierId();
				long stationId = -1;
				if ( vr.getPickUpLocation() != null )
					stationId = vr.getPickUpLocation().getStationId();
				String preis = "";
				if ( vr.getOfferList().get(0).getPrice() != null)
					preis = vr.getOfferList().get(0).getPrice().getAmount();
				
				String servCatCode = vr.getOfferList().get(0).getServiceCatalogCode();
				Long servCatId = vr.getOfferList().get(0).getServiceCatalogId();
						
				Offer offer = createOffer(vr.getVehicle().getManufacturer(),supplierId, stationId, preis,servCatCode,servCatId);
				offer.setBookLink(bookLink);
				
				//offers.add(offer);
			}
		}
		
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

}
