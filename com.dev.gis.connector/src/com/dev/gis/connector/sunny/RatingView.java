package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class RatingView extends BasicProtocol{
	
	private List<RatingGroup> ratingGroups = new ArrayList<RatingGroup>();


	public void addGroup(RatingGroup teaser) {
		ratingGroups.add(teaser);
	}


	public List<RatingGroup> getRatingGroups() {
		return ratingGroups;
	}


	public void setRatingGroups(List<RatingGroup> ratingGroups) {
		this.ratingGroups = ratingGroups;
	}
	
	@XmlTransient
	public List<Offer> getAllOffers() {
		List<Offer> offers = new ArrayList<Offer>();
		for ( RatingGroup group : ratingGroups) {
			offers.addAll(group.getOffers() );
		}
		return offers;
	}

}
