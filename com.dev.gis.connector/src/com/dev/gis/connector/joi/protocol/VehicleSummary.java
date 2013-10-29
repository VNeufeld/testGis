package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleSummary")
public class VehicleSummary extends BasicProtocol {

	private int quantity;
	
	private int totalQuantity;
	
	MoneyAmount minimalPrice;
	
	MoneyAmount maximalPrice;

	
	
	
	public MoneyAmount getMaximalPrice() {
		return maximalPrice;
	}
	
	public MoneyAmount getMinimalPrice() {
		return minimalPrice;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public int getTotalQuantity() {
		return totalQuantity;
	}
	
	public void setMaximalPrice(MoneyAmount maximalPrice) {
		this.maximalPrice = maximalPrice;
	}
	
	public void setMinimalPrice(MoneyAmount minimalPrice) {
		this.minimalPrice = minimalPrice;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
}
