package com.dev.gis.connector.sunny;

import java.net.URI;

public class PayPageResponse extends Response {

	private URI link;

	private String reference;


	public URI getLink() {
		return link;
	}

	public void setLink(URI link) {
		this.link = link;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
