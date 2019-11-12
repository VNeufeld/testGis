package com.dev.gis.connector.sunny;

import java.net.URI;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Inclusive extends BasicProtocol {

	private int id;
	
	private URI link;

	// flag insurance
	private Boolean insurance;

	// ItemClass ID
	private Integer itemClassId;

	private String itemClassCode;

	private String itemClassName;
	
	protected boolean itemClassFilter;
	
	// description of the inclusive item
	private String description;

	// description of the inclusive item
	private String name;
	
	// flag unique - only one of this extra can be booked
	private Boolean unique;

	private String code;
	
	private int positive;
	
	private int displayPriority;
	
	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public URI getLink() {
		return link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Boolean getInsurance() {
		return insurance;
	}

	public void setInsurance(Boolean insurance) {
		this.insurance = insurance;
	}

	public Integer getItemClassId() {
		return itemClassId;
	}

	public void setItemClassId(Integer itemClassId) {
		this.itemClassId = itemClassId;
	}

	public String getItemClassCode() {
		return itemClassCode;
	}

	public void setItemClassCode(String itemClassCode) {
		this.itemClassCode = itemClassCode;
	}

	public String getItemClassName() {
		return itemClassName;
	}

	public void setItemClassName(String itemClassName) {
		this.itemClassName = itemClassName;
	}

	public boolean isItemClassFilter() {
		return itemClassFilter;
	}

	public void setItemClassFilter(boolean itemClassFilter) {
		this.itemClassFilter = itemClassFilter;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPositive() {
		return positive;
	}

	public void setPositive(int positive) {
		this.positive = positive;
	}

	public int getDisplayPriority() {
		return displayPriority;
	}

	public void setDisplayPriority(int displayPriority) {
		this.displayPriority = displayPriority;
	}
}