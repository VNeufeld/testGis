package com.dev.gis.connector.joi.protocol;

import java.util.Calendar;

public class TravelInformation extends BasicProtocol {

	private DayAndHour pickUpTime;

	private Location pickUpLocation;

	private DayAndHour dropOffTime;

	private Location dropOffLocation;
	
	public TravelInformation() {
		
	}

	public TravelInformation(long pickupStationId, long dropoffStationId) {
		pickUpLocation = new Location();
		pickUpLocation.setStationId(pickupStationId);
		
		dropOffLocation = new Location();
		dropOffLocation.setStationId(dropoffStationId);
		
	}
	
	
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
	
	
	public static DayAndHour createDate(Calendar cal) {
		String sday = String.format("%4d-%02d-%02d",
				cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DAY_OF_MONTH));
		String sTime = String.format("%02d:%02d",
				cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));

		DayAndHour dh = new DayAndHour();
		dh.setDate(sday);
		dh.setTime(sTime);
		return dh;
	}

	public void setDates(Calendar pickupDate, Calendar dropoffDate) {
		DayAndHour dh = createDate(pickupDate);
		setPickUpTime(dh);

		dh = createDate(dropoffDate);
		setDropOffTime(dh);
		
	}

}