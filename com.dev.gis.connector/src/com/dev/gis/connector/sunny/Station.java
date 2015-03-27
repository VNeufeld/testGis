package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.List;


public class Station extends GenericLocation {
	
	public Station() {
		super (HitType.STATION);
	}

	public Station(long stationId) {
		super (stationId);
	}
	
	private long supplierId;

	private Long supplierGroupId;
	
	private int stationPriority;
	
	private int supplierPriority;
	
	private String crsCode;
	
	private Long[] assignedCityIds;
	
	private GeoLocation geo;
	
	private Address addres;
	
	private List<StationLocType> stationLocTypes = new ArrayList<StationLocType>();
	
	public Long[] getAssignedCityIds() {
		return assignedCityIds;
	}

	public GeoLocation getGeo() {
		return geo;
	}

	
	public int getStationPriority() {
		return stationPriority;
	}


	public long getSupplierId() {
		return supplierId;
	}
	
	public int getSupplierPriority() {
		return supplierPriority;
	}
	
	public void setAssignedCityIds(Long[] assignedCityIds) {
		this.assignedCityIds = assignedCityIds;
	}
	
	public void setGeo(GeoLocation geo) {
		this.geo = geo;
	}
	
	
	public void setStationPriority(int stationPriority) {
		this.stationPriority = stationPriority;
	}
	
	
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	
	public void setSupplierPriority(int supplierPriority) {
		this.supplierPriority = supplierPriority;
	}

	public Address getAddres() {
		return addres;
	}

	public void setAddres(Address addres) {
		this.addres = addres;
	}

	public List<StationLocType> getStationLocTypes() {
		return stationLocTypes;
	}

	public void setStationLocTypes(List<StationLocType> stationLocTypes) {
		this.stationLocTypes = stationLocTypes;
	}

	public String getCrsCode() {
		return crsCode;
	}

	public void setCrsCode(String crsCode) {
		this.crsCode = crsCode;
	}

	public Long getSupplierGroupId() {
		return supplierGroupId;
	}

	public void setSupplierGroupId(Long supplierGroupId) {
		this.supplierGroupId = supplierGroupId;
	}
}
