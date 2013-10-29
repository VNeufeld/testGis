package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "travelInformation")
@XmlType(propOrder =
	{ "pickUpTime", "dropOffTime", "pickUpLocation", "dropOffLocation" })
public class TravelInformation extends BasicProtocol {

	private DayAndHour pickUpTime;

	private Location pickUpLocation;

	private DayAndHour dropOffTime;

	private Location dropOffLocation;

	
	
	@XmlElement(required=false)
	public Location getDropOffLocation() {
		return dropOffLocation;
	}

	@XmlElement(required=false)
	public DayAndHour getDropOffTime() {
		return dropOffTime;
	}

	@XmlElement(required=false)
	public Location getPickUpLocation() {
		return pickUpLocation;
	}

	@XmlElement(required=false)
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
}