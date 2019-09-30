package com.nbb.apps.carreservationv2.base;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class VehicleGroup extends BasicProtocol {

	private Long id;
	private URI link;

	private String name;

	private List<Offer> offerList = new ArrayList<Offer>();

	private int nrOffers;

	
	
	public VehicleGroup() {
	}
	
	public VehicleGroup(String name) {
		this.name = name;
	}
	
	
	
	public void addOffer (Offer offer) {
		offerList.add(offer);
	}

	public Long getId() {
		return id;
	}
	
	public URI getLink() {
		return link;
	}
	
	public String getName() {
		return name;
	}

	public int getNrOffers() {
		return nrOffers;
	}

	public List<Offer> getOfferList() {
		return offerList;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLink(URI link) {
		this.link = link;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNrOffers(int nrOffers) {
		this.nrOffers = nrOffers;
	}

	public void setOfferList(List<Offer> offerList) {
		this.offerList = offerList;
		nrOffers = offerList.size();
	}
}