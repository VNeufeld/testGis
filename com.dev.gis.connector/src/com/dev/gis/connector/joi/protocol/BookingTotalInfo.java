/**
 * 
 */
package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author neu
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingTotalInfo {
	
	private MoneyAmount carPrice;
	
	private PayType offeredPayment;
	
	private MoneyAmount totalExtraPriceInSellCurrency;
	private MoneyAmount totalExtraPoaPrice;
	private MoneyAmount totalExtraPrepaidPrice;

	private MoneyAmount oneWayFeeInSellCurrency;
	private MoneyAmount oneWayFee;
	private boolean oneWayFeeIncludedInRate;
	private boolean oneWayFeeTaxIncluded;
	
	private Double exchangeRate;
	private MoneyAmount expectedTotalPriceinSellCurrency;
	
	private MoneyAmount totalPoaPrice;
	private MoneyAmount totalPrepaidPrice;
	private MoneyAmount totalPoaPriceInSellCurrency;
	
	private MoneyAmount totalAdditionalsPrice;

	
	private final List<Extra> extras = new ArrayList<Extra>();
	
	private final List<Inclusive> inclusives = new ArrayList<Inclusive>(); 
	
	private final List<Extra> selectedAdditional = new ArrayList<Extra>();

	
	public MoneyAmount getCarPrice() {
		return carPrice;
	}
	public void setCarPrice(MoneyAmount carPrice) {
		this.carPrice = carPrice;
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
	
	public MoneyAmount getTotalPoaPrice() {
		return totalPoaPrice;
	}
	public void setTotalPoaPrice(MoneyAmount totalPoaPrice) {
		this.totalPoaPrice = totalPoaPrice;
	}
	public MoneyAmount getTotalPoaPriceInSellCurrency() {
		return totalPoaPriceInSellCurrency;
	}
	public void setTotalPoaPriceInSellCurrency(MoneyAmount totalPoaPriceInSellCurrency) {
		this.totalPoaPriceInSellCurrency = totalPoaPriceInSellCurrency;
	}
	public List<Extra> getExtras() {
		return extras;
	}
	public MoneyAmount getTotalExtraPrepaidPrice() {
		return totalExtraPrepaidPrice;
	}
	public void setTotalExtraPrepaidPrice(MoneyAmount totalExtraPrepaidPrice) {
		this.totalExtraPrepaidPrice = totalExtraPrepaidPrice;
	}
	public boolean isOneWayFeeIncludedInRate() {
		return oneWayFeeIncludedInRate;
	}
	public void setOneWayFeeIncludedInRate(boolean oneWayFeeIncludedInRate) {
		this.oneWayFeeIncludedInRate = oneWayFeeIncludedInRate;
	}
	public boolean isOneWayFeeTaxIncluded() {
		return oneWayFeeTaxIncluded;
	}
	public void setOneWayFeeTaxIncluded(boolean oneWayFeeTaxIncluded) {
		this.oneWayFeeTaxIncluded = oneWayFeeTaxIncluded;
	}
	public List<Inclusive> getInclusives() {
		return inclusives;
	}
	public PayType getOfferedPayment() {
		return offeredPayment;
	}
	public void setOfferedPayment(PayType offeredPayment) {
		this.offeredPayment = offeredPayment;
	}
	public MoneyAmount getExpectedTotalPriceinSellCurrency() {
		return expectedTotalPriceinSellCurrency;
	}
	public void setExpectedTotalPriceinSellCurrency(
			MoneyAmount expectedTotalPriceinSellCurrency) {
		this.expectedTotalPriceinSellCurrency = expectedTotalPriceinSellCurrency;
	}
	public MoneyAmount getTotalPrepaidPrice() {
		return totalPrepaidPrice;
	}
	public void setTotalPrepaidPrice(MoneyAmount totalPrepaidPrice) {
		this.totalPrepaidPrice = totalPrepaidPrice;
	}
	public MoneyAmount getTotalAdditionalsPrice() {
		return totalAdditionalsPrice;
	}
	public void setTotalAdditionalsPrice(MoneyAmount totalAdditionalsPrice) {
		this.totalAdditionalsPrice = totalAdditionalsPrice;
	}
	public List<Extra> getSelectedAdditional() {
		return selectedAdditional;
	}


}
