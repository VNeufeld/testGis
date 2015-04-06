package com.dev.gis.connector.sunny;

public class Location extends BasicProtocol {

	private Station station;
	
	private Long stationId = null;

	private GeoLocation geo;

	private String airport;
	
	
	
	public Location () {
	}
	
	public Location (Station station, GeoLocation geo, String airport) {
		this.station = station;
		if (station != null)
			this.stationId = station.getId();
		this.geo = geo;
		this.airport = airport;
	}

	
	
	public String getAirport() {
		return airport;
	}

	public GeoLocation getGeo() {
		return geo;
	}
	public Station getStation() {
		return station;
	}
	public Long getStationId() {
		return stationId;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public void setGeo(GeoLocation geo) {
		this.geo = geo;
	}
	
	public void setStation(Station station) {
		this.station = station;
	}
	
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	@Override
	public String toString() {
		return "Location [station=" + station + ", stationId=" + stationId
				+ ", geo=" + geo + ", airport=" + airport + "]";
	}

	public void setCityId(Long valueOf) {
		if ( geo == null)
			geo = new GeoLocation();
		geo.setCity(valueOf);
		
	}
}