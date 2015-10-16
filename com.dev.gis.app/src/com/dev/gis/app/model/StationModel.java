package com.dev.gis.app.model;

public class StationModel {
	
	private Long id;
	private String name;
	private String supplier;
	
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

}
