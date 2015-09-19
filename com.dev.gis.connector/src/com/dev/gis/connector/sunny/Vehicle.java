package com.dev.gis.connector.sunny;

import java.net.URI;


public class Vehicle extends Response {
	
	private long id;

	private Long supplierId;
	
	private Long categoryId;
	
	private Long groupId;
	

	private String manufacturer;
	private String vehicleModel;
	private String type;
	private String fuelType;
	private String ACRISS;
	private String supplierCategory;
	private String commonCarInformation;
	private String companyName;
	private String picture;
	
	private String bodyStyleText;

	private Integer nrDoors;
	private Integer nrSeats;

	private boolean aircondition = false;
	private boolean automatic = false;
	private boolean snowTires = false;
	private boolean allWheel = false;
	private boolean navigationSystem = false;
	private boolean stationWagon = false;
	private boolean convertible = false;
	
	private long bodyStyle;
	private Long typeId;
	private Short nrAdults;
	private Short nrChildren;
	private Long fuelTypeId;
	

	private Integer nrLargeLuggage;
	private Integer nrSmallLuggage;
	private Integer trunkSize;

	private Long PS;

	private URI vehicleSmallImage;
	private URI vehicleLargeImage;
	private URI link;
	

	public URI getLink() {
		return link;
	}

	public String getManufacturer() {
		return manufacturer;
	}



	public Integer getNrLargeLuggage() {
		return nrLargeLuggage;
	}

	public Integer getNrSmallLuggage() {
		return nrSmallLuggage;
	}

	public Long getPS() {
		return PS;
	}


	public URI getVehicleLargeImage() {
		return vehicleLargeImage;
	}

	public String getVehicleModel() {
		return vehicleModel;
	}

	public URI getVehicleSmallImage() {
		return vehicleSmallImage;
	}

	public boolean isAircondition() {
		return aircondition;
	}

	public boolean isAllWheel() {
		return allWheel;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public boolean isSnowTires() {
		return snowTires;
	}
	
	public void setAircondition(boolean aircondition) {
		this.aircondition = aircondition;
	}

	public void setAllWheel(boolean allWheel) {
		this.allWheel = allWheel;
	}

	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}

	
	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	

	public void setNrLargeLuggage(Integer nrLargeLuggage) {
		this.nrLargeLuggage = nrLargeLuggage;
	}


	public void setNrSmallLuggage(Integer nrSmallLuggage) {
		this.nrSmallLuggage = nrSmallLuggage;
	}

	public void setPS(Long pS) {
		PS = pS;
	}

	public void setSnowTires(boolean snowTires) {
		this.snowTires = snowTires;
	}


	public void setVehicleLargeImage(URI vehicleLargeImage) {
		this.vehicleLargeImage = vehicleLargeImage;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

	public void setVehicleSmallImage(URI vehicleSmallImage) {
		this.vehicleSmallImage = vehicleSmallImage;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", supplierId=" + supplierId
				+ ", categoryId="
				+ categoryId + ", manufacturer=" + manufacturer
				+ ", vehicleModel=" + vehicleModel + ", aircondition="
				+ aircondition + ", nrDoors=" + nrDoors + ", automatic="
				+ automatic + ", nrSeats=" + nrSeats + ", bodyStyle="
				+ bodyStyle + ", vehicleSmallImage=" + vehicleSmallImage
				+ ", vehicleLargeImage=" + vehicleLargeImage + ", link=" + link
				+ ", nrSmallLuggage=" + nrSmallLuggage 
				+ "]";
	}


	public boolean isNavigationSystem() {
		return navigationSystem;
	}

	public void setNavigationSystem(boolean navigationSystem) {
		this.navigationSystem = navigationSystem;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getACRISS() {
		return ACRISS;
	}

	public void setACRISS(String aCRISS) {
		ACRISS = aCRISS;
	}

	public String getSupplierCategory() {
		return supplierCategory;
	}

	public void setSupplierCategory(String supplierCategory) {
		this.supplierCategory = supplierCategory;
	}

	public String getCommonCarInformation() {
		return commonCarInformation;
	}

	public void setCommonCarInformation(String commonCarInformation) {
		this.commonCarInformation = commonCarInformation;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Short getNrAdults() {
		return nrAdults;
	}

	public void setNrAdults(Short nrAdults) {
		this.nrAdults = nrAdults;
	}

	public Short getNrChildren() {
		return nrChildren;
	}

	public void setNrChildren(Short nrChildren) {
		this.nrChildren = nrChildren;
	}

	public boolean isStationWagon() {
		return stationWagon;
	}

	public void setStationWagon(boolean stationWagon) {
		this.stationWagon = stationWagon;
	}

	public boolean isConvertible() {
		return convertible;
	}

	public void setConvertible(boolean convertible) {
		this.convertible = convertible;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Integer getTrunkSize() {
		return trunkSize;
	}

	public void setTrunkSize(Integer trunkSize) {
		this.trunkSize = trunkSize;
	}


	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getNrDoors() {
		return nrDoors;
	}

	public void setNrDoors(Integer nrDoors) {
		this.nrDoors = nrDoors;
	}

	public Integer getNrSeats() {
		return nrSeats;
	}

	public void setNrSeats(Integer nrSeats) {
		this.nrSeats = nrSeats;
	}


	public Long getFuelTypeId() {
		return fuelTypeId;
	}

	public void setFuelTypeId(Long fuelTypeId) {
		this.fuelTypeId = fuelTypeId;
	}


	public long getBodyStyle() {
		return bodyStyle;
	}

	public void setBodyStyle(long bodyStyle) {
		this.bodyStyle = bodyStyle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBodyStyleText() {
		return bodyStyleText;
	}

	public void setBodyStyleText(String bodyStyleText) {
		this.bodyStyleText = bodyStyleText;
	}

}