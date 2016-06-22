package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferFilterTemplate {
	

	private List<ObjectValuePair> stationLocTypes = new ArrayList<ObjectValuePair>();;

	private List<FilterObject> bodyStyles = new ArrayList<FilterObject>();

	private List<FilterObject> serviceCatalogs = new ArrayList<FilterObject>();
	
	private List<ObjectValuePair> fuelTypes = new ArrayList<ObjectValuePair>();
	
	private Map<String,List<FilterObject>> inclusivesFilter = new HashMap<String,List<FilterObject>>();
	
	private List<FilterObject> stations = new ArrayList<FilterObject>();
	
	private List<FilterObject> suppliers = new ArrayList<FilterObject>();

	private List<FilterObject> carTypes = new ArrayList<FilterObject>();
	
	private FilterObject automatic = new FilterObject("automatic",new Long(0));
	private FilterObject aircondition = new FilterObject("aircondition",new Long(0));
	
	private List<FilterObject> status = new ArrayList<FilterObject>();
	
	
	private MoneyAmount minPrice;
	
	private MoneyAmount maxPrice;
	
	
	public List<ObjectValuePair> getStationLocTypes() {
		return stationLocTypes;
	}
	public void setStationLocTypes(List<ObjectValuePair> stationLocTypes) {
		this.stationLocTypes = stationLocTypes;
	}
	
	public FilterObject getAutomatic() {
		return automatic;
	}

	public List<ObjectValuePair> getFuelTypes() {
		return fuelTypes;
	}
	public void setFuelTypes(List<ObjectValuePair> fuelTypes) {
		this.fuelTypes = fuelTypes;
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
	
	public final List<FilterObject> getStations() {
		return stations;
	}

	public List<FilterObject> getSuppliers() {
		return suppliers;
	}
	

	public List<FilterObject> getBodyStyles() {
		return bodyStyles;
	}
	public FilterObject getAircondition() {
		return aircondition;
	}
	public List<FilterObject> getStatus() {
		return status;
	}
	public Map<String, List<FilterObject>> getInclusivesFilter() {
		return inclusivesFilter;
	}
	public void setInclusivesFilter(Map<String, List<FilterObject>> inclusivesFilter) {
		this.inclusivesFilter = inclusivesFilter;
	}
	public void setBodyStyles(List<FilterObject> bodyStyles) {
		this.bodyStyles = bodyStyles;
	}
	public void setStations(List<FilterObject> stations) {
		this.stations = stations;
	}
	public void setSuppliers(List<FilterObject> suppliers) {
		this.suppliers = suppliers;
	}
	public void setAutomatic(FilterObject automatic) {
		this.automatic = automatic;
	}
	public void setAircondition(FilterObject aircondition) {
		this.aircondition = aircondition;
	}
	public void setStatus(List<FilterObject> status) {
		this.status = status;
	}
	public List<FilterObject> getServiceCatalogs() {
		return serviceCatalogs;
	}
	public void setServiceCatalogs(List<FilterObject> serviceCatalogs) {
		this.serviceCatalogs = serviceCatalogs;
	}

	public List<FilterObject> getCarTypes() {
		return carTypes;
	}
	public void setCarTypes(List<FilterObject> carTypes) {
		this.carTypes = carTypes;
	}
	
}
