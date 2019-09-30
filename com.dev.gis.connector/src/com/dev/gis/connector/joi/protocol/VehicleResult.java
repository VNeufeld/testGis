package com.dev.gis.connector.joi.protocol;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleResult")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleResult extends Response {

	private long id;

	private URI link;
	
	private URI recalculateLink;

	private Location pickUpLocation;

	private Location dropOffLocation;

	private VehicleDescription vehicle;

	private List<Offer> offerList = new ArrayList<>();
	
	private GuiTextElements texts;
	
	private String module;
	
	private Long remainingCacheSeconds;
	
	private String crossOfferLink;


	
	public Offer accessOffer(String offerId) {
		for (Offer offer : offerList) {
			if (offer.getId().toString().equals(offerId))
				return offer;
		}
		return null;
	}
	
	public MoneyAmount accessMemberPrice (){
		for (Offer offer : offerList) {
			if (offer.isMember())
				return offer.getPrice();
		}
		if (offerList.size() > 0) {
			return offerList.get(0).getPrice();
		}
		return null;
	}

	public Location getDropOffLocation() {
		return dropOffLocation;
	}

	public long getId() {
		return id;
	}

	public URI getLink() {
		return link;
	}

	public String getModule() {
		return module;
	}

	public List<Offer> getOfferList() {
		return offerList;
	}

	public Location getPickUpLocation() {
		return pickUpLocation;
	}

	public URI getRecalculateLink() {
		return recalculateLink;
	}

	@XmlElement(required=false)
	public Long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}
	
	@XmlElement(required=false)
	public GuiTextElements getTexts() {
		return texts;
	}
	
	public VehicleDescription getVehicle() {
		return vehicle;
	}
	
	public void setDropOffLocation(Location dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setModule(String module) {
		this.module = module;
	}
	
	public void setOfferList(List<Offer> offerList) {
		this.offerList = offerList;
	}
	
	public void setPickUpLocation(Location pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}
	
	public void setRecalculateLink(URI recalculateLink) {
		this.recalculateLink = recalculateLink;
	}
	
	public void setRemainingCacheSeconds(Long remainingCacheSeconds) {
		this.remainingCacheSeconds = remainingCacheSeconds;
	}
	
	public void setTexts(GuiTextElements texts) {
		this.texts = texts;
	}
	
	public void setVehicle(VehicleDescription vehicle) {
		this.vehicle = vehicle;
	}

	public String getCrossOfferLink() {
		return crossOfferLink;
	}

	public void setCrossOfferLink(String crossOfferLink) {
		this.crossOfferLink = crossOfferLink;
	}
}