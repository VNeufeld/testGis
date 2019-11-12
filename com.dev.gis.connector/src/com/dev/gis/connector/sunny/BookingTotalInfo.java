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
	
	private MoneyAmount totalExtraPriceInSellCurrency;
	private MoneyAmount totalExtraPoaPrice;
	private MoneyAmount oneWayFeeInSellCurrency;
	private MoneyAmount oneWayFee;
	private Double exchangeRate;
	private MoneyAmount expectedTotalPrice;
	
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
	public MoneyAmount getTotalExtraPriceInSellCurrency() {
		return totalExtraPriceInSellCurrency;
	}
	public void setTotalExtraPriceInSellCurrency(MoneyAmount totalExtraPriceInSellCurrency) {
		this.totalExtraPriceInSellCurrency = totalExtraPriceInSellCurrency;
	}
	public MoneyAmount getTotalExtraPoaPrice() {
		return totalExtraPoaPrice;
	}
	public void setTotalExtraPoaPrice(MoneyAmount totalExtraPoaPrice) {
		this.totalExtraPoaPrice = totalExtraPoaPrice;
	}
	public MoneyAmount getOneWayFeeInSellCurrency() {
		return oneWayFeeInSellCurrency;
	}
	public void setOneWayFeeInSellCurrency(MoneyAmount oneWayFeeInSellCurrency) {
		this.oneWayFeeInSellCurrency = oneWayFeeInSellCurrency;
	}
	public MoneyAmount getOneWayFee() {
		return oneWayFee;
	}
	public void setOneWayFee(MoneyAmount oneWayFee) {
		this.oneWayFee = oneWayFee;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public MoneyAmount getExpectedTotalPrice() {
		return expectedTotalPrice;
	}
	public void setExpectedTotalPrice(MoneyAmount expectedTotalPrice) {
		this.expectedTotalPrice = expectedTotalPrice;
	}
	


}
