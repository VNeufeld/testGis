package com.nbb.apps.carreservationv2.base;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonDeserialize;



public class Hit extends BasicProtocol {

	private String identifier;

	private String aptCode;

	private long id;

	private HitType type;

	private URI link;

	private Set<Hit> subResult = new HashSet<Hit>();

	private Long countryId = null;

	private String country;
	
	public static Hit createHit(int id, String name) {
		Hit hit = new Hit();
		hit.setId(id);
		hit.setIdentifier(name);
		hit.setType(HitType.CITY);
		return hit;
		
	}

	
	
	public void addSubResult (Hit hit) {
		this.subResult.add(hit);
	}

	public String getCountry() {
		return country;
	}

	public Long getCountryId() {
		return countryId;
	}

	public long getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public URI getLink() {
		return link;
	}

	public Set<Hit> getSubResult() {
		return subResult;
	}

	public HitType getType() {
		return type;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setSubResult(Set<Hit> subResult) {
		this.subResult = subResult;
	}

	@JsonDeserialize(using = HitTypeJsonDesirializer.class)
	public void setType(HitType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return identifier + ", aptCode=" + aptCode
				+ ", " + type + " , country=" + country + "]";
	}



	public String getAptCode() {
		return aptCode;
	}



	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
}
