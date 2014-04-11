package com.dev.gis.connector.joi.protocol;

import java.net.URI;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="extra")
@XmlType(propOrder={ 	"id", "link", "code", "name", "payType", "status", "insurance", "mandatory", 
						"price", "totalPrice", "requiredItems" , "competingItems", "requiredItemsString" , "competingItemsString",
						"extraClass", "value1" , "value2", "value3", "value4", "dimension1", "dimension2" } )
public class Extra extends BasicProtocol {

	private long id;

	private URI link;

	
	
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


	
	
	@XmlElement(required=false)
	public String getCode() {
		return code;
	}

	@XmlElement(required=false)
	public Set<Long> getCompetingItems() {
		return competingItems;
	}

	@XmlElement(required=false)
	public String getCompetingItemsString() {
		return competingItemsString;
	}

	@XmlElement(required=false)
	public String getDimension1() {
		return dimension1;
	}

	@XmlElement(required=false)
	public String getDimension2() {
		return dimension2;
	}

	@XmlElement(required=false)
	public ItemClass getExtraClass() {
		return extraClass;
	}

	public long getId() {
		return id;
	}

	@XmlElement(required=false)
	public URI getLink() {
		return link;
	}

	@XmlElement(required=false)
	public String getName() {
		return name;
	}

	@XmlElement(required=false)
	public String getPayType() {
		return payType;
	}

	@XmlElement(required=false)
	public MoneyAmount getPrice() {
		return price;
	}

	@XmlElement(required=false)
	public Set<Long> getRequiredItems() {
		return requiredItems;
	}

	@XmlElement(required=false)
	public String getRequiredItemsString() {
		return requiredItemsString;
	}

	@XmlElement(required=false)
	public String getStatus() {
		return status;
	}
	
	@XmlElement(required=false)
	public MoneyAmount getTotalPrice() {
		return totalPrice;
	}
	
	@XmlElement(required=false)
	public String getValue1() {
		return value1;
	}
	
	@XmlElement(required=false)
	public String getValue2() {
		return value2;
	}
	
	@XmlElement(required=false)
	public String getValue3() {
		return value3;
	}
	
	@XmlElement(required=false)
	public String getValue4() {
		return value4;
	}
	
	@XmlElement(required=false)
	public boolean isInsurance() {
		return insurance;
	}
	
	@XmlElement(required=false)
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
}
