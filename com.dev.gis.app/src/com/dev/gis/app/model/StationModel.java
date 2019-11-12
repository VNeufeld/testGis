package com.dev.gis.app.model;

import com.dev.gis.connector.joi.protocol.Station;

public class StationModel {
	
	private Long id;
	private String name;
	private String supplier;
	private Long supplierId;
	
	private Station station;
	
	public StationModel(long id, String identifier) {
		this.id = id;
		this.name = identifier;
	}
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}

}
