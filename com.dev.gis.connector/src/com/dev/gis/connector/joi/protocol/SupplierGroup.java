package com.dev.gis.connector.joi.protocol;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="supplierGroup")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierGroup extends BasicProtocol {

	private long id;

	private String name;

	private String displayName;

	
	public long getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}