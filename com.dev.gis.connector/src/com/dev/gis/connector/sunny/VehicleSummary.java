package com.dev.gis.connector.sunny;

public class VehicleSummary extends BasicProtocol {

	private int quantityVehicles;
	private int totalQuantityVehicles;
	private int quantityOffers;
	private int totalQuantityOffers;
	private int quantityGroups;
	private int totalQuantityGroups;
	
	private MoneyAmount minimalPrice;
	private MoneyAmount maximalPrice;

	private MoneyAmount minimalStdPrice;
	private MoneyAmount maximalStdPrice;
	
	
	
	public MoneyAmount getMaximalPrice() {
		return maximalPrice;
	}
	
	public MoneyAmount getMinimalPrice() {
		return minimalPrice;
	}
	
	
	public int getQuantityOffers() {
		return quantityOffers;
	}
	
	
	public int getTotalQuantityOffers() {
		return totalQuantityOffers;
	}
	
	public void setMaximalPrice(MoneyAmount maximalPrice) {
		this.maximalPrice = maximalPrice;
	}
	
	public void setMinimalPrice(MoneyAmount minimalPrice) {
		this.minimalPrice = minimalPrice;
	}


	public void setQuantityOffers(int quantityOffers) {
		this.quantityOffers = quantityOffers;
	}

	public void setTotalQuantityOffers(int totalQuantityOffers) {
		this.totalQuantityOffers = totalQuantityOffers;
	}

	public MoneyAmount getMinimalStdPrice() {
		return minimalStdPrice;
	}

	public void setMinimalStdPrice(MoneyAmount minimalStdPrice) {
		this.minimalStdPrice = minimalStdPrice;
	}

	public MoneyAmount getMaximalStdPrice() {
		return maximalStdPrice;
	}

	public void setMaximalStdPrice(MoneyAmount maximalStdPrice) {
		this.maximalStdPrice = maximalStdPrice;
	}

	public int getQuantityVehicles() {
		return quantityVehicles;
	}

	public void setQuantityVehicles(int quantityVehicles) {
		this.quantityVehicles = quantityVehicles;
	}

	public int getTotalQuantityVehicles() {
		return totalQuantityVehicles;
	}

	public void setTotalQuantityVehicles(int totalQuantityVehicles) {
		this.totalQuantityVehicles = totalQuantityVehicles;
	}

	public int getQuantityGroups() {
		return quantityGroups;
	}

	public void setQuantityGroups(int quantityGroups) {
		this.quantityGroups = quantityGroups;
	}

	public int getTotalQuantityGroups() {
		return totalQuantityGroups;
	}

	public void setTotalQuantityGroups(int totalQuantityGroups) {
		this.totalQuantityGroups = totalQuantityGroups;
	}
}