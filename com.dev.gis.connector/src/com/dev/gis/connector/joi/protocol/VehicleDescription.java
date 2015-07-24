package com.dev.gis.connector.joi.protocol;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleDescription")
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleDescription extends BasicProtocol {

	private boolean aircondition;
	
	private boolean automatic;
	
	private long bodyStyle;

	private BodyStyleText bodyStyleText;

	private String cargoHeight;

	private String cargoLength;

	private String cargoVolume;

	private String cargoWidth;

	private long categoryId;

	private String driverLicense;
	
	private int driverMinAge;

	private String licenseMinTime;

	private String loadInformation;
	
	private List<String> cargoInformation;

	private String manufacturer;
	
	private int nrDoors;

	private int nrLargeLuggage;
	
	private int nrSeats;
	
	private int nrSmallLuggage;
	
	
	private long supplierId;
	
	private VehicleGroup vehicleGroup;
	private URI vehicleLargeImage;
	private String vehicleModel;
	private URI vehicleSmallImage;
	private String weightTotal;
	private String weightUse;


	
	

	public long getBodyStyle() {
		return bodyStyle;
	}

	@XmlElement(required=false)
	public BodyStyleText getBodyStyleText() {
		return bodyStyleText;
	}

	@XmlElement(required=false)
	public String getCargoHeight() {
		return cargoHeight;
	}

	public List<String> getCargoInformation() {
		return cargoInformation;
	}

	@XmlElement(required=false)
	public String getCargoLength() {
		return cargoLength;
	}

	@XmlElement(required=false)
	public String getCargoVolume() {
		return cargoVolume;
	}

	@XmlElement(required=false)
	public String getCargoWidth() {
		return cargoWidth;
	}

	public long getCategoryId() {
		return categoryId;
	}

	@XmlElement(required=false)
	public String getDriverLicense() {
		return driverLicense;
	}

	public int getDriverMinAge() {
		return driverMinAge;
	}

	@XmlElement(required=false)
	public String getLicenseMinTime() {
		return licenseMinTime;
	}

	@XmlElement(required=false)
	public String getLoadInformation() {
		return loadInformation;
	}

	@XmlElement(required=false)
	public String getManufacturer() {
		return manufacturer;
	}

	public int getNrDoors() {
		return nrDoors;
	}

	public int getNrLargeLuggage() {
		return nrLargeLuggage;
	}

	public int getNrSeats() {
		return nrSeats;
	}

	public int getNrSmallLuggage() {
		return nrSmallLuggage;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public VehicleGroup getVehicleGroup() {
		return vehicleGroup;
	}
	
	public URI getVehicleLargeImage() {
		return vehicleLargeImage;
	}

	@XmlElement(required=false)
	public String getVehicleModel() {
		return vehicleModel;
	}

	@XmlElement(required=false)
	public URI getVehicleSmallImage() {
		return vehicleSmallImage;
	}

	@XmlElement(required=false)
	public String getWeightTotal() {
		return weightTotal;
	}

	@XmlElement(required=false)
	public String getWeightUse() {
		return weightUse;
	}

	public boolean isAircondition() {
		return aircondition;
	}
	
	public boolean isAutomatic() {
		return automatic;
	}
	
	public void setAircondition(boolean aircondition) {
		this.aircondition = aircondition;
	}
	
	public void setAutomatic(boolean automatic) {
		this.automatic = automatic;
	}
	
	public void setBodyStyle(long bodyStyle) {
		this.bodyStyle = bodyStyle;
	}
	
	public void setBodyStyleText(BodyStyleText bodyStyleText) {
		this.bodyStyleText = bodyStyleText;
	}
	
	public void setCargoHeight(String cargoHeight) {
		this.cargoHeight = cargoHeight;
	}
	
	public void setCargoInformation(List<String> cargoInformation) {
		this.cargoInformation = cargoInformation;
	}
	
	public void setCargoLength(String cargoLength) {
		this.cargoLength = cargoLength;
	}
	
	public void setCargoVolume(String cargoVolume) {
		this.cargoVolume = cargoVolume;
	}

	public void setCargoWidth(String cargoWidth) {
		this.cargoWidth = cargoWidth;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public void setDriverMinAge(int driverMinAge) {
		this.driverMinAge = driverMinAge;
	}

	public void setLicenseMinTime(String licenseMinTime) {
		this.licenseMinTime = licenseMinTime;
	}

	public void setLoadInformation(String loadInformation) {
		this.loadInformation = loadInformation;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setNrDoors(int nrDoors) {
		this.nrDoors = nrDoors;
	}

	public void setNrLargeLuggage(int nrLargeLuggage) {
		this.nrLargeLuggage = nrLargeLuggage;
	}

	public void setNrSeats(int nrSeats) {
		this.nrSeats = nrSeats;
	}

	public void setNrSmallLuggage(int nrSmallLuggage) {
		this.nrSmallLuggage = nrSmallLuggage;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public void setVehicleGroup(VehicleGroup vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
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

	public void setWeightTotal(String weightTotal) {
		this.weightTotal = weightTotal;
	}

	public void setWeightUse(String weightUse) {
		this.weightUse = weightUse;
	}
}
