package com.dev.gis.connector.sunny;

import java.net.URI;

public class Supplier extends BasicProtocol {

	private long id;

	private URI link;
	

	
	private long supplierGroupId;

	private URI logo;
	
	private String name;

	
	
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

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		if (  this.name == null )
			this.name="";
		return 31 * this.name.hashCode();
	}
}