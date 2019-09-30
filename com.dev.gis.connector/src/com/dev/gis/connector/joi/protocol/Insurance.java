package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;



@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="insurance")
public class Insurance extends BasicProtocol {

	private int id;

	private MoneyAmount price;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	public void setPrice(MoneyAmount price) {
		this.price = price;
	}
}