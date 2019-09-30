package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleResponse")
@XmlType(propOrder = {"requestId", "link", "errors", "sessionId", "remainingCacheSeconds", "resultList", "summary", "texts", "offerFilterTemplate" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleResponse extends Response {

	private VehicleSummary summary;

	private List<VehicleResult> resultList = new ArrayList<>();
	
	private GuiTextElements texts;

	private long remainingCacheSeconds = -1;
	
	private OfferFilterTemplate  offerFilterTemplate;

	private List<VehicleResult> crossOfferResultList = new ArrayList<>();
	

	public VehicleResult accessVehicle(long vehicleId) {
		for (VehicleResult vehicle : resultList) {
			if (vehicle.getId() == vehicleId)
				return vehicle;
		}
		return null;
	}

	public List<VehicleResult> getResultList() {
		return resultList;
	}

	@XmlElement(required=false)
	public VehicleSummary getSummary() {
		return summary;
	}

	public GuiTextElements getTexts() {
		return texts;
	}

	public void setResultList(List<VehicleResult> resultList) {
		this.resultList = resultList;
	}
	
	public void setSummary(VehicleSummary summary) {
		this.summary = summary;
	}
	
	public void setTexts(GuiTextElements texts) {
		this.texts = texts;
	}

	public long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}

	public void setRemainingCacheSeconds(long remainingCacheSeconds) {
		this.remainingCacheSeconds = remainingCacheSeconds;
	}

	public void sort() {
		// sort vehicles by member price
		Collections.sort(resultList, new Comparator<VehicleResult>() {

			@Override
			public int compare(VehicleResult rLeft, VehicleResult rRight) {
				return rLeft.accessMemberPrice().compareTo(rRight.accessMemberPrice());
			}
		});

		// sort the inclusives
		// iterate through result list
		for(VehicleResult vehicleResult : getResultList()) {

			// set member offer as first offer, which assumes, that the member price is the lowest price
			Collections.sort(vehicleResult.getOfferList(), new Comparator<Offer>() {

				@Override
				public int compare(Offer oLeft, Offer oRight) {
					return oLeft.getPrice().compareTo(oRight.getPrice());
				}
			});
		
			// iterate through
			for(Offer offer : vehicleResult.getOfferList()) {
				// sort the inclusives

				Collections.sort(offer.getInclusives(), new Comparator<Inclusive>() {

					@Override
					public int compare(Inclusive o1, Inclusive o2) {
						if(o1.getDisplayPriority() <= o2.getDisplayPriority())
							return -1;
						return 1;
					}
				});
			}
		}
	}

	public OfferFilterTemplate getOfferFilterTemplate() {
		return offerFilterTemplate;
	}

	public void setOfferFilterTemplate(OfferFilterTemplate offerFilterTemplate) {
		this.offerFilterTemplate = offerFilterTemplate;
	}

	public List<VehicleResult> getCrossOfferResultList() {
		return crossOfferResultList;
	}

	public void setCrossOfferResultList(List<VehicleResult> crossOfferResultList) {
		this.crossOfferResultList = crossOfferResultList;
	}
}