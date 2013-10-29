package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlElement;


public class StationInfo extends BasicProtocol {
	
	private boolean providesTransfer;
	private String transfer;
	private String openingHours;
	private MoneyAmount outOfHourFee;

	
	
	@XmlElement(required=false)
	public String getOpeningHours() {
		return openingHours;
	}
	
	@XmlElement(required=false)
	public MoneyAmount getOutOfHourFee() {
		return outOfHourFee;
	}
	
	@XmlElement(required=false)
	public String getTransfer() {
		return transfer;
	}
	
	@XmlElement(required=false)
	public boolean isProvidesTransfer() {
		return providesTransfer;
	}
	
	public void setOpeningHours(String openingHours) {
		this.openingHours = openingHours;
	}
	
	public void setOutOfHourFee(MoneyAmount outOfHourFee) {
		this.outOfHourFee = outOfHourFee;
	}
	
	public void setProvidesTransfer(boolean providesTransfer) {
		this.providesTransfer = providesTransfer;
	}
	
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
}