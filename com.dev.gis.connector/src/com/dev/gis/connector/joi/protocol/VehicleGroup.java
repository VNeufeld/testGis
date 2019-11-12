package com.dev.gis.connector.joi.protocol;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleGroup")
public class VehicleGroup extends BasicProtocol {

	long id;

	URI link;
	
	

	String name;

	
	
	public long getId() {
		return id;
	}

	@XmlElement(required=false)
	public URI getLink() {
		return link;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLink(URI link) {
		this.link = link;
	}

	public void setName(String name) {
		this.name = name;
	}
}
