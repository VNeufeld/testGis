package com.dev.gis.connector.sunny;

import java.net.URI;

import com.dev.gis.connector.joi.protocol.MoneyAmount;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Extra extends BasicProtocol {

	private int id;

	private URI link;

	
	private String status;
	
	private String code;
	
	private String name; 

	// description of the inclusive item
	private String description;

	private MoneyAmount totalPrice;
	
	
	private MoneyAmount totalPriceInSellCurrency;
	

	// isn't use for HA
	private int[] requiredItems;

	// isn't use for HA
	private int[] competingItems;

	// flag mandatory - extra must be booked
	private Boolean mandatory;

	private PayType payType = PayType.PAY_ON_ARRIVAL;

	// flag insurance
	private Boolean insurance;

	// flag unique - only one of this extra can be booked
	private Boolean unique;
	
	// ItemClass ID
	private Integer itemClassId;
	
	
	private MoneyAmount price;
	private MoneyAmount upperPrice;
	private MoneyAmount lowerPrice;
	
	private String value1;
	
	// isn't use for HA
	private String value2;
	
	private String dimension1;
	private String dimension2;

	private String dependency;

	
	private boolean selected = false;

	
	
	public String getCode() {
		return code;
	}

	public int[] getCompetingItems() {
		return competingItems;
	}

	public String getDimension1() {
		return dimension1;
	}

	public String getDimension2() {
		return dimension2;
	}


	public int getId() {
		return id;
	}

	public URI getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public PayType getPayType() {
		return payType;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	public int[] getRequiredItems() {
		return requiredItems;
	}


	public MoneyAmount getTotalPrice() {
		return totalPrice;
	}

	public String getValue1() {
		return value1;
	}

	public String getValue2() {
		return value2;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCompetingItems(int[] competingItems) {
		this.competingItems = competingItems;
	}
	
	public void setDimension1(String dimension1) {
		this.dimension1 = dimension1;
	}
	
	public void setDimension2(String dimension2) {
		this.dimension2 = dimension2;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public void setLink(URI link) {
		this.link = link;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonDeserialize(using = PayTypeJsonDesirializer.class)
	public void setPayType(PayType payType) {
		this.payType = payType;
	}
	
	public void setPrice(MoneyAmount price) {
		this.price = price;
	}
	
	public void setRequiredItems(int[] requiredItems) {
		this.requiredItems = requiredItems;
	}
	
//	@JsonDeserialize(using = AvailabilityJsonDesirializer.class)
//	public void setStatus(Availability status) {
//		this.status = status;
//	}
	
	public void setTotalPrice(MoneyAmount totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public MoneyAmount getUpperPrice() {
		return upperPrice;
	}

	public void setUpperPrice(MoneyAmount upperPrice) {
		this.upperPrice = upperPrice;
	}

	public MoneyAmount getLowerPrice() {
		return lowerPrice;
	}

	public void setLowerPrice(MoneyAmount lowerPrice) {
		this.lowerPrice = lowerPrice;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getInsurance() {
		return insurance;
	}

	public void setInsurance(Boolean insurance) {
		this.insurance = insurance;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public Integer getItemClassId() {
		return itemClassId;
	}

	public void setItemClassId(Integer itemClassId) {
		this.itemClassId = itemClassId;
	}

	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MoneyAmount getTotalPriceInSellCurrency() {
		return totalPriceInSellCurrency;
	}

	public void setTotalPriceInSellCurrency(MoneyAmount totalPriceInSellCurrency) {
		this.totalPriceInSellCurrency = totalPriceInSellCurrency;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
