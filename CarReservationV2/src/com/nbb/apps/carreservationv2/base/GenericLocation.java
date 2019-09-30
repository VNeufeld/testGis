package com.nbb.apps.carreservationv2.base;

import java.net.URI;

public abstract class GenericLocation extends BasicProtocol {
	
	private long id;
	
	private URI link;
	
	private String identifier;
	
	private HitType type;
	
	
	public  GenericLocation() {
	}
	
	public GenericLocation(HitType type) {
		this.type = type;
	}
	
	public GenericLocation(long id) {
		this.id = id;
	}
	
	
	public HitType hitType () {
		return this.type;
	}
	
	public long getId() {
		return id;
	}
		
	public String getIdentifier() {
		return identifier;
	}

	public URI getLink() {
		return link;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setLink(URI link) {
		this.link = link;
	}
}
