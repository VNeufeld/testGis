package com.dev.gis.connector.joi.protocol;

public class ObjectValuePair {
	private String name;
	private String value;
	
	
	public ObjectValuePair() {
	}

	public ObjectValuePair(String key, String value) {
		this.name = key;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
