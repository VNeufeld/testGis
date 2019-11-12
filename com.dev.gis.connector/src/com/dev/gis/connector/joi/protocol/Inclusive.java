package com.dev.gis.connector.joi.protocol;

import java.net.URI;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Inclusive extends BasicProtocol {

	private long id;
	
	private URI link;
	
	private String description;

	private int displayPriority;

	private String code;

	private String itemClassName;
	
	private String itemClassCode;
	
	private int itemClassId;
	
	private boolean itemClassFilter;
	
	public String getDescription() {
		return description;
	}

	@XmlElement(required=false)
	public int getDisplayPriority() {
		return displayPriority;
	}

	public long getId() {
		return id;
	}

	public URI getLink() {
		return link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplayPriority(int displayPriority) {
		this.displayPriority = displayPriority;
	}
	
	public void setId(long id) {
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

	public String getItemClassName() {
		return itemClassName;
	}

	public void setItemClassName(String itemClassName) {
		this.itemClassName = itemClassName;
	}

	public String getItemClassCode() {
		return itemClassCode;
	}

	public void setItemClassCode(String itemClassCode) {
		this.itemClassCode = itemClassCode;
	}

	public int getItemClassId() {
		return itemClassId;
	}

	public void setItemClassId(int itemClassId) {
		this.itemClassId = itemClassId;
	}

	public boolean isItemClassFilter() {
		return itemClassFilter;
	}

	public void setItemClassFilter(boolean itemClassFilter) {
		this.itemClassFilter = itemClassFilter;
	}
}