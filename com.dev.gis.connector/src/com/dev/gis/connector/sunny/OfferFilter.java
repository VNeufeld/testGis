package com.dev.gis.connector.sunny;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class OfferFilter {
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private String[] stationLocTypeCodes;
	
	private Long[] transferCategories;
	
	// instead off fuelPolicy use inclusivesFilter
	//private String[] fuelPolicy;
	private Long[] fuelTypes;
	private Long[] bodyStyles;
	private Boolean aircondition;
	private Boolean automatic;
	private Long[] suppliers;
	private Long carGroupId;
	private Long commissionTypeId;
	private Long serviceCatalogId;
	
	private final Set<Long> inclusives = new HashSet<Long>();	
	
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String[] getStationLocTypeCodes() {
		return stationLocTypeCodes;
	}
	public void setStationLocTypeCodes(String[] stationLocTypeCodes) {
		this.stationLocTypeCodes = stationLocTypeCodes;
	}
	
	public Boolean getAircondition() {
		return aircondition;
	}
	public void setAircondition(Boolean aircondition) {
		this.aircondition = aircondition;
	}
	public Boolean getAutomatic() {
		return automatic;
	}
	public void setAutomatic(Boolean automatic) {
		this.automatic = automatic;
	}
	public Long[] getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(Long[] suppliers) {
		this.suppliers = suppliers;
	}
	public Long[] getBodyStyles() {
		return bodyStyles;
	}
	public void setBodyStyles(Long[] bodyStyles) {
		this.bodyStyles = bodyStyles;
	}
	public Long[] getFuelTypes() {
		return fuelTypes;
	}
	public void setFuelTypes(Long[] fuelTypes) {
		this.fuelTypes = fuelTypes;
	}
	public Long getCommissionTypeId() {
		return commissionTypeId;
	}
	public void setCommissionTypeId(Long commissionTypeId) {
		this.commissionTypeId = commissionTypeId;
	}
	public Long getCarGroupId() {
		return carGroupId;
	}
	public void setCarGroupId(Long carGroupId) {
		this.carGroupId = carGroupId;
	}
	public Long getServiceCatalogId() {
		return serviceCatalogId;
	}
	public void setServiceCatalogId(Long serviceCatalogId) {
		this.serviceCatalogId = serviceCatalogId;
	}
	public Set<Long> getInclusives() {
		return inclusives;
	}
	public Long[] getTransferCategories() {
		return transferCategories;
	}
	public void setTransferCategories(Long[] transferCategories) {
		this.transferCategories = transferCategories;
	}
	

}
