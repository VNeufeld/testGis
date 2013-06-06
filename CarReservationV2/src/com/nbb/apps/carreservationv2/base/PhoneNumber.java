package com.nbb.apps.carreservationv2.base;

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
