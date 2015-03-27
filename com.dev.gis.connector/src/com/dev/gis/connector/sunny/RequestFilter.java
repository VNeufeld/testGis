package com.dev.gis.connector.sunny;

public class RequestFilter extends BasicProtocol {

	private long[] stationList;

	private long[] vehicleCategories;

	private long[] bodyStyles;

	private long[] serviceCatalogs;

	private long[] suppliers;
	
	private long[] cities;

	private String[] stationLocTypeCodes;

	
	
	public long[] getBodyStyles() {
		return bodyStyles;
	}

	public long[] getCities() {
		return cities;
	}
	public long[] getServiceCatalogs() {
		return serviceCatalogs;
	}

	public long[] getStationList() {
		return stationList;
	}

	public long[] getSuppliers() {
		return suppliers;
	}

	public long[] getVehicleCategories() {
		return vehicleCategories;
	}

	public void setBodyStyles(long[] bodyShapes) {
		this.bodyStyles = bodyShapes;
	}

	public void setCities(long[] cities) {
		this.cities = cities;
	}

	public void setServiceCatalogs(long[] serviceCatalogs) {
		this.serviceCatalogs = serviceCatalogs;
	}
	
	public void setStationList(long[] stationList) {
		this.stationList = stationList;
	}
	
	public void setSuppliers(long[] suppliers) {
		this.suppliers = suppliers;
	}
	
	public void setVehicleCategories(long[] vehicleCategories) {
		this.vehicleCategories = vehicleCategories;
	}

	public String[] getStationLocTypeCodes() {
		return stationLocTypeCodes;
	}

	public void setStationLocTypeCodes(String[] stationLocTypeCodes) {
		this.stationLocTypeCodes = stationLocTypeCodes;
	}
}
