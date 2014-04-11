package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="incentiveCard")
public class IncentiveCard extends BasicProtocol {

	private String id;

	private String number;

	private Person owner;

	
	
	public String getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}

	public Person getOwner() {
		return owner;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}
}
