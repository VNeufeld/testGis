package com.dev.gis.connector.sunny;

public class Text extends BasicProtocol {

	private Long id;
	private String text;
	private String iso;

	
	public Long getId() {
		return id;
	}
	public String getIso() {
		return iso;
	}
	public String getText() {
		return text;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public void setText(String text) {
		this.text = text;
	}
}
