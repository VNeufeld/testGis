package com.dev.gis.connector.joi.protocol;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="inclusive")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Inclusive extends BasicProtocol {

	private long id;
	
	private URI link;
	
	private String description;

	private int displayPriority;

	private String code;

	private String itemClassName;
	
	
	public String getDescription() {
		return description;
	}

	@XmlElement(required=false)
	public int getDisplayPriority() {
		return displayPriority;
	}

	public long getId() {
		return id;
	}

	public URI getLink() {
		return link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisplayPriority(int displayPriority) {
		this.displayPriority = displayPriority;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getItemClassName() {
		return itemClassName;
	}

	public void setItemClassName(String itemClassName) {
		this.itemClassName = itemClassName;
	}
}