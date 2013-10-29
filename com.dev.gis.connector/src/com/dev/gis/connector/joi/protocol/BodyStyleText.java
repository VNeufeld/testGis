package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "bodyStyle")
public class BodyStyleText extends BasicProtocol {

	private long id;

	private String text;

	
	
	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}
}
