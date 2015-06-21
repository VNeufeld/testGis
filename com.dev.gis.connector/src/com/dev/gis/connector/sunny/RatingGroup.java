package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.List;

public class RatingGroup {
	
	private Header header;

	private List<Offer> offers = new ArrayList<Offer>();
	
	public static class Header {
		private Long stationId;
		private Long supplierId;
		private Long carCategoryId;
		private String name;
		
		public Long getStationId() {
			return stationId;
		}
		public void setStationId(Long stationId) {
			this.stationId = stationId;
		}
		public Long getSupplierId() {
			return supplierId;
		}
		public void setSupplierId(Long supplierId) {
			this.supplierId = supplierId;
		}
		public Long getCarCategoryId() {
			return carCategoryId;
		}
		public void setCarCategoryId(Long carCategoryId) {
			this.carCategoryId = carCategoryId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}

	public RatingGroup() {
		
	}
	
	public RatingGroup(Offer offer) {
		header = new Header();
		header.carCategoryId = offer.getCarCategoryId();
		header.stationId = offer.getPickUpStationId();
		header.supplierId = offer.getSupplierId();
		header.name = offer.getName();
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public void addOffer(Offer offer) {
		offers.add(offer);
		
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

}
