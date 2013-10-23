package com.nbb.apps.carreservationv2.base;

import java.math.BigDecimal;

public class Address extends BasicProtocol{

	private String signee;
	
	private String street;

	private String country;

	private Integer countryId;

	private String zip;

	private String city;
	
	private BigDecimal latitude;

	private BigDecimal longitude;
	
	
	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public String getSignee() {
		return signee;
	}

	public String getStreet() {
		return street;
	}

	public String getZip() {
		return zip;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public void setSignee(String signee) {
		this.signee = signee;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}
