package com.dev.gis.connector.joi.protocol;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationResponse extends Response
{
	private int quantity;
	
	private List<Station> stations;
	
	private long remainingCacheSeconds;

	
	
	public int getQuantity() {
		return quantity;
	}
	
	public long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}
	
	public List<Station> getStations() {
		return stations;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setRemainingCacheSeconds(long remainingCacheSeconds) {
		this.remainingCacheSeconds = remainingCacheSeconds;
	}
	
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
	
	public Station getFirstStation() {
		if (stations != null && !stations.isEmpty() )
			return stations.get(0);
		return null;
	}
}
