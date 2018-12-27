package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferFilterTemplate {
	
	
	private List<ObjectValuePair> fuelPolicy = new ArrayList<ObjectValuePair>();;

	private List<ObjectValuePair> stationLocTypes = new ArrayList<ObjectValuePair>();;

	private List<ObjectValuePair> bodyStyles = new ArrayList<ObjectValuePair>();

	private List<ObjectValuePair> fuelTypes = new ArrayList<ObjectValuePair>();
	
	private List<ObjectValuePair> suppliers = new ArrayList<ObjectValuePair>();

	private Map<String, List<ObjectValuePair>> inclusives = new HashMap<String,List<ObjectValuePair>>();
	
	private List<ObjectValuePair> stationTransfers = new ArrayList<ObjectValuePair>();
	
	private List<ObjectValuePair> carTypes = new ArrayList<ObjectValuePair>();

	
	private ObjectValuePair automatic;
	private ObjectValuePair aircondition;
	
	private MoneyAmount minPrice;
	
	private MoneyAmount maxPrice;
	
	
	public List<ObjectValuePair> getStationLocTypes() {
		return stationLocTypes;
	}
	public void setStationLocTypes(List<ObjectValuePair> stationLocTypes) {
		this.stationLocTypes = stationLocTypes;
	}
	public ObjectValuePair getAutomatic() {
		return automatic;
	}
	public void setAutomatic(ObjectValuePair automatic) {
		this.automatic = automatic;
	}
	public List<ObjectValuePair> getBodyStyles() {
		return bodyStyles;
	}
	public void setBodyStyles(List<ObjectValuePair> bodyStyles) {
		this.bodyStyles = bodyStyles;
	}
	public List<ObjectValuePair> getFuelTypes() {
		return fuelTypes;
	}
	public void setFuelTypes(List<ObjectValuePair> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}
	public List<ObjectValuePair> getFuelPolicy() {
		return fuelPolicy;
	}
	public void setFuelPolicy(List<ObjectValuePair> fuelPolicy) {
		this.fuelPolicy = fuelPolicy;
	}
	public List<ObjectValuePair> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<ObjectValuePair> suppliers) {
		this.suppliers = suppliers;
	}
	public MoneyAmount getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(MoneyAmount minPrice) {
		this.minPrice = minPrice;
	}
	public MoneyAmount getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(MoneyAmount maxPrice) {
		this.maxPrice = maxPrice;
	}
	public ObjectValuePair getAircondition() {
		return aircondition;
	}
	public void setAircondition(ObjectValuePair aircondition) {
		this.aircondition = aircondition;
	}
	public Map<String, List<ObjectValuePair>> getInclusives() {
		return inclusives;
	}
	public void setInclusives(Map<String, List<ObjectValuePair>> inclusives) {
		this.inclusives = inclusives;
	}
	public List<ObjectValuePair> getStationTransfers() {
		return stationTransfers;
	}
	public void setStationTransfers(List<ObjectValuePair> stationTransfers) {
		this.stationTransfers = stationTransfers;
	}
	public List<ObjectValuePair> getCarTypes() {
		return carTypes;
	}
	public void setCarTypes(List<ObjectValuePair> carTypes) {
		this.carTypes = carTypes;
	}

}
