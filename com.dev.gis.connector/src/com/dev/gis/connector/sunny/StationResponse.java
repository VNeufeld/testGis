package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="stationResponse")
public class StationResponse extends Response
{
	private int quantity;
	
	private List<Station> stations = new ArrayList<Station>();
	
	/**
	 * state of supllier for oneway case.
	 * e.g  13 - on Request if oneway
	 * 
	 */
	private Integer oneWayStatusSupplier;
	
	
	public int getQuantity() {
		return quantity;
	}
	
	
	@XmlElement(required=false)
	public List<Station> getStations() {
		return stations;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}

	@Override
	public String toString() {
		return "StationResponse [quantity=" + quantity + ", stations=" + stations + "]";
	}


	@XmlElement(required=false)
	public Integer getOneWayStatusSupplier() {
		return oneWayStatusSupplier;
	}


	public void setOneWayStatusSupplier(Integer oneWayStatusSupplier) {
		this.oneWayStatusSupplier = oneWayStatusSupplier;
	}


}
