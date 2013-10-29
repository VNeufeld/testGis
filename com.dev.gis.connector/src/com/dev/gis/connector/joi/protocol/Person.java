package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="person")
@XmlType(propOrder={ "gender", "salutation", "firstName", "name" } )
public class Person extends BasicProtocol {

	private Salutation salutation;

	private Gender gender;

	private String firstName = "";

	private String name = "";

	
	
	public String getFirstName() {
		return firstName;
	}

	@XmlElement(required=false)
	public Gender getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	@XmlElement(required=false)
	public Salutation getSalutation() {
		return salutation;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}
}