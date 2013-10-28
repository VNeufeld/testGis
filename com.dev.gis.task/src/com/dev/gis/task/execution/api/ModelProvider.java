package com.dev.gis.task.execution.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.bpcs.mdcars.protocol.MoneyAmount;
import com.bpcs.mdcars.protocol.Offer;

public enum ModelProvider {
	INSTANCE;

	private List<Offer> offers;

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
	
	private static Offer createOffer(String name, int supplierId, int stationId, String price) {
		Offer offer = new Offer();
		offer.setName(name);
		offer.setPickUpStationId(stationId);
		offer.setPrice(new MoneyAmount(price, "EUR"));
		offer.setSupplierId(supplierId);
		return offer;
	}

	public void update() {
		for ( Offer offer : offers) {
			offer.getPrice().setDecimalAmount(offer.getPrice().getDecimalAmount().add(BigDecimal.valueOf(10)));
		}

		offers.add(createOffer("BMW 7 ",12, 13, "223,32"));
		
	}

}
