package com.dev.gis.connector.joi.protocol;

import java.net.URI;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Extra extends BasicProtocol {

	private long id;

	private URI link;

	private boolean selected = false;
	
	

	private String status;
	
	private String code;
	
	private String name; 

	private MoneyAmount totalPrice;

	private Set<Long> requiredItems;
	private String requiredItemsString;

	private Set<Long> competingItems;
	private String competingItemsString;

	private boolean mandatory;

	private String payType;

	private boolean insurance;

	private ItemClass extraClass;
	
	private MoneyAmount price;
	
	private String value1;
	private String value2;
	private String value3;
	private String value4;
	private String dimension1;
	private String dimension2;
	
	private boolean bookable = true;
	

	public String getCode() {
		return code;
	}

	public Set<Long> getCompetingItems() {
		return competingItems;
	}

	public String getCompetingItemsString() {
		return competingItemsString;
	}

	public String getDimension1() {
		return dimension1;
	}

	public String getDimension2() {
		return dimension2;
	}

	public ItemClass getExtraClass() {
		return extraClass;
	}

	public long getId() {
		return id;
	}
	public URI getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public String getPayType() {
		return payType;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	public Set<Long> getRequiredItems() {
		return requiredItems;
	}

	public String getRequiredItemsString() {
		return requiredItemsString;
	}

	public String getStatus() {
		return status;
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
	
	public String getValue3() {
		return value3;
	}
	
	public String getValue4() {
		return value4;
	}
	
	public boolean isInsurance() {
		return insurance;
	}

	public boolean isMandatory() {
		return mandatory;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setCompetingItems(Set<Long> competingItems) {
		this.competingItems = competingItems;
	}
	
	public void setCompetingItemsString(String competingItemsString) {
		this.competingItemsString = competingItemsString;
	}
	
	public void setDimension1(String dimension1) {
		this.dimension1 = dimension1;
	}
	
	public void setDimension2(String dimension2) {
		this.dimension2 = dimension2;
	}
	
	public void setExtraClass(ItemClass extraClass) {
		this.extraClass = extraClass;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	public void setPrice(MoneyAmount price) {
		this.price = price;
	}

	
	public void setRequiredItems(Set<Long> requiredItems) {
		this.requiredItems = requiredItems;
	}

	
	public void setRequiredItemsString(String requiredItemsString) {
		this.requiredItemsString = requiredItemsString;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	
	public void setTotalPrice(MoneyAmount totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	
	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isBookable() {
		return bookable;
	}

	public void setBookable(boolean bookable) {
		this.bookable = bookable;
	}
	
}
