/**
 * 
 */
package com.dev.gis.connector.sunny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author neu
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingTotalInfo {
	
	private MoneyAmount extrasPrice;
	private MoneyAmount carPrice;
	private MoneyAmount insurancePrice;
	private MoneyAmount totalPrice;
	
	public MoneyAmount getExtrasPrice() {
		return extrasPrice;
	}
	public void setExtrasPrice(MoneyAmount extrasPrice) {
		this.extrasPrice = extrasPrice;
	}
	public MoneyAmount getCarPrice() {
		return carPrice;
	}
	public void setCarPrice(MoneyAmount carPrice) {
		this.carPrice = carPrice;
	}
	public MoneyAmount getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(MoneyAmount totalPrice) {
		this.totalPrice = totalPrice;
	}
	public MoneyAmount getInsurancePrice() {
		return insurancePrice;
	}
	public void setInsurancePrice(MoneyAmount insurancePrice) {
		this.insurancePrice = insurancePrice;
	}
	


}
