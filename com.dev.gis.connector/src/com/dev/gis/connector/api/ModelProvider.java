package com.dev.gis.connector.api;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bpcs.mdcars.protocol.MoneyAmount;
import com.bpcs.mdcars.protocol.Offer;
import com.dev.gis.connector.joi.protocol.PaymentInformation;
import com.dev.gis.connector.joi.protocol.Person;
import com.dev.gis.connector.joi.protocol.VehicleRequestFilter;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.connector.joi.protocol.VehicleResult;

public enum ModelProvider {
	INSTANCE;

	private List<Offer> offers;


	public long cityId;

	public long pageSize;

	public String sessionID;
	
	public long pageNo;
	
	public String airport;
	
	public Calendar pickupDateTime;
	public Calendar dropoffDateTime;
	
	public String languageCode;

	public long languageId;
	
	
	public VehicleRequestFilter  vehicleRequestFilter;
	
	public String lastResponse;

	
	private Person driver;

	private Person customer;
	
	private PaymentInformation  paymentInformation;
	
	private ModelProvider() {
		offers = new ArrayList<Offer>();
		// Image here some fancy database access to read the persons and to
		// put them into the model
		offers.add(createOffer("BMW 7 ",12, 13, "223,32"));
		offers.add(createOffer("Audi 5",12, 13, "167,00"));
		offers.add(createOffer("VW 1 ",12, 13, "556,00"));
		offers.add(createOffer("TOYOTA XXL",12, 13, "444,55"));
		offers.add(createOffer("BMW 5 ",12, 13, "199,99"));
	}

	public List<Offer> getOffers() {
		return offers;
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


	public void update() {
		for ( Offer offer : offers) {
			offer.getPrice().setDecimalAmount(offer.getPrice().getDecimalAmount().add(BigDecimal.valueOf(10)));
		}

		offers.add(createOffer("BMW 7 ",12, 13, "223,32"));
		
	}


	public void update(VehicleResponse response) {
		offers.clear();
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
				
				offers.add(offer);
			}
		}
		
	}

}
