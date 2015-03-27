package com.dev.gis.connector.sunny;

public class TravelInformation extends BasicProtocol {

	private DayAndHour pickUpTime;

	private Location pickUpLocation;

	private DayAndHour dropOffTime;

	private Location dropOffLocation;
	
	private String promoCode;

	
	
	public Location getDropOffLocation() {
		return dropOffLocation;
	}

	public DayAndHour getDropOffTime() {
		return dropOffTime;
	}

	public Location getPickUpLocation() {
		return pickUpLocation;
	}
	public DayAndHour getPickUpTime() {
		return pickUpTime;
	}

	public void setDropOffLocation(Location dropOffLocation) {
		this.dropOffLocation = dropOffLocation;
	}

	public void setDropOffTime(DayAndHour dropOffTime) {
		this.dropOffTime = dropOffTime;
	}

	public void setPickUpLocation(Location pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}

	public void setPickUpTime(DayAndHour pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
}