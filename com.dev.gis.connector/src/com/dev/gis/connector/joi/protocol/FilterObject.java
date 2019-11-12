package com.dev.gis.connector.joi.protocol;

public class FilterObject {
	private String name;
	private Object object;
	private int count;
	private Long id;
	
	
	public FilterObject() {
	}

	public FilterObject(String key, Object value) {
		this.name = key;
		this.object = value;
		this.count = 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return object;
	}
	public void setValue(Object value) {
		this.object = value;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void incr(int value) {
		this.count = this.count+value;
	}
	
}
