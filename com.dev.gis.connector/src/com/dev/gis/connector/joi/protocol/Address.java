package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="address")
@XmlType(propOrder = {"signee", "street", "zip", "city", "country", "countryId" })
public class Address extends BasicProtocol{

	private String signee;
	
	private String street;

	private String country;

	private Long countryId;

	private String zip;

	private String city;

	
	
	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	@XmlElement(required=false)
	public Long getCountryId() {
		return countryId;
	}

	@XmlElement(required=false)
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

	public void setCountryId(Long countryId) {
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
}
