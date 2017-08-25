package com.dev.gis.connector.joi.protocol;

import java.util.HashSet;
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

	private final Set<Long> bodyStyles = new HashSet<Long>();

	private final Set<Long> serviceCatalogs= new HashSet<Long>();

	private final Set<Long> suppliers = new HashSet<Long>();
	
	private Set<Long> cities;

	private Boolean airportStationsOnly;
	
	private final Set<Long> inclusives = new HashSet<Long>();
	
	private final Set<Long> carTypes =  new HashSet<Long>();

	private final Set<Long> paymentTypes = new HashSet<Long>();
	
	
	
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

	public void setCities(Set<Long> cities) {
		this.cities = cities;
	}
	
	public void setStations(Set<Long> stations) {
		this.stations = stations;
	}
	
	public void setVehicleCategories(Set<Long> vehicleCategories) {
		this.vehicleCategories = vehicleCategories;
	}

	public Set<Long> getInclusives() {
		return inclusives;
	}

	public Set<Long> getCarTypes() {
		return carTypes;
	}

	public Set<Long> getPaymentTypes() {
		return paymentTypes;
	}
}