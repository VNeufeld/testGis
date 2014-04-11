package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="item")
public class ItemClass extends BasicProtocol {

	private long id;

	
	
	private long code;

	private int displayPriority;

	
	
	public long getCode() {
		return code;
	}

	public int getDisplayPriority() {
		return displayPriority;
	}

	public long getId() {
		return id;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public void setDisplayPriority(int displayPriority) {
		this.displayPriority = displayPriority;
	}

	public void setId(long id) {
		this.id = id;
	}
}
