package com.nbb.apps.carreservationv2.base;

public class GeoLocation extends BasicProtocol {
	
	
	private Long country;
	
	private String countryCode;
	
	private Long area;
	
	private Long city;
	
	private String airport;

	
	
	public GeoLocation() {
		// required for protocol handling
	}
	
	public GeoLocation (Long country, Long area, Long city, String airport) {
		this.country = country;
		this.area = area;
		this.city = city;
		this.airport = airport;
	}

	
	public String getAirport() {
		return airport;
	}
	public Long getArea() {
		return area;
	}
	public Long getCity() {
		return city;
	}
	
	public Long getCountry() {
		return country;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	public void setAirport(String airport) {
		this.airport = airport;
	}
	
	public void setArea(Long area) {
		this.area = area;
	}
	
	public void setCity(Long city) {
		this.city = city;
	}
	
	public void setCountry(Long country) {
		this.country = country;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public String toString() {
		return "GeoLocation [country=" + country + ", countryCode="
				+ countryCode + ", area=" + area + ", city=" + city
				+ ", airport=" + airport + "]";
	}
}
