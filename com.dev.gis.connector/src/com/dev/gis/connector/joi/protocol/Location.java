package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "location")
@XmlType(propOrder =
	{ "station", "stationId", "airport", "cityId", "area", "countryId", "countryCode" })
public class Location extends BasicProtocol {

	private Station station = null;
	
	private long stationId;

	private String airport;
	
	private Long cityId = null;
	
	private Long countryId = null;
	
	private String countryCode;
	
	private Long area = null;

	
	public Location () {
	}
	
	public Location (Station station, String airport) {
		this.station = station;
		stationId = -1; // Invalid
		if (station != null)
			this.stationId = station.getId();
		this.airport = airport;
	}

	
	
	@XmlElement(required=false)
	public String getAirport() {
		return airport;
	}

	@XmlElement(required=false)
	public Long getArea() {
		return area;
	}

	@XmlElement(required=false)
	public Long getCityId() {
		return cityId;
	}

	@XmlElement(required=false)
	public Long getCountryId() {
		return countryId;
	}
	
	@XmlElement(required=false)
	public String getCountryCode() {
		return countryCode;
	}
	
	@XmlElement(required=false)
	public Station getStation() {
		return station;
	}

	public long getStationId() {
		return stationId;
	}
	
	public void setAirport(String airport) {
		this.airport = airport;
	}
	
	public void setArea(Long area) {
		this.area = area;
	}
	
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}
	
	public void setStationId(long stationId) {
		this.stationId = stationId;
	}
}