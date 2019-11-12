package com.dev.gis.connector.joi.protocol;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="supplier")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier extends BasicProtocol {

	private long id;

	private URI link;
	

	
	private long supplierGroupId;

	private URI logo;
	
	private String name;

	private String displayName;
	
	
	public long getId() {
		return id;
	}

	public URI getLink() {
		return link;
	}

	public URI getLogo() {
		return logo;
	}

	public String getName() {
		return name;
	}

	public long getSupplierGroupId() {
		return supplierGroupId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLink(URI link) {
		this.link = link;
	}

	public void setLogo(URI logo) {
		this.logo = logo;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSupplierGroupId(long supplierGroupId) {
		this.supplierGroupId = supplierGroupId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}