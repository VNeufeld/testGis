package com.dev.gis.connector.joi.protocol;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleRequestFilter")
public class VehicleRequestFilter extends BasicProtocol {

 	private Set<Long> stations;

	private Set<Long> vehicleCategories;

	private Set<Long> bodyStyles;

	private Set<Long> serviceCatalogs;

	private Set<Long> suppliers;
	
	private Set<Long> cities;

	private Boolean airportStationsOnly;

	
	
	@XmlElement(required=false)
	public Set<Long> getBodyStyles() {
		return bodyStyles;
	}

	@XmlElement(required=false)
	public Set<Long> getCities() {
		return cities;
	}

	@XmlElement(required=false)
	public Set<Long> getServiceCatalogs() {
		return serviceCatalogs;
	}

	@XmlElement(required=false)
	public Set<Long> getStations() {
		return stations;
	}

	@XmlElement(required=false)
	public Set<Long> getSuppliers() {
		return suppliers;
	}

	@XmlElement(required=false)
	public Set<Long> getVehicleCategories() {
		return vehicleCategories;
	}

	@XmlElement(required=false)
	public Boolean isAirportStationsOnly() {
		return airportStationsOnly;
	}

	public void setAirportStationsOnly(Boolean airportStationsOnly) {
		this.airportStationsOnly = airportStationsOnly;
	}

	public void setBodyStyles(Set<Long> bodyShapes) {
		this.bodyStyles = bodyShapes;
	}

	public void setCities(Set<Long> cities) {
		this.cities = cities;
	}

	public void setServiceCatalogs(Set<Long> serviceCatalogs) {
		this.serviceCatalogs = serviceCatalogs;
	}
	
	public void setStations(Set<Long> stations) {
		this.stations = stations;
	}
	
	public void setSuppliers(Set<Long> suppliers) {
		this.suppliers = suppliers;
	}
	
	public void setVehicleCategories(Set<Long> vehicleCategories) {
		this.vehicleCategories = vehicleCategories;
	}
}