package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="phone")
@XmlType(propOrder={ "country", "prefix", "extension" } )
public class PhoneNumber extends BasicProtocol {

	private String country = "";

	private String prefix = "";

	private String extension = "";

	
	
	public PhoneNumber() {
	}
	
	public PhoneNumber(String country, String prefix, String extension) {
		this.country = country;
		this.prefix = prefix;
		this.extension = extension;
	}
	
	
	
	@XmlElement(required=false)
	public String getCountry() {
		return country;
	}

	public String getExtension() {
		return extension;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
