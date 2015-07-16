package com.dev.gis.connector.sunny;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="payPageResponse")
public class PayPageResponse extends Response {

	private static final long serialVersionUID = -8022386031831364752L;

	private URI link;

	private String creditCardPaymentReference;

	
	@XmlElement(name="reference")
	public String getCreditCardPaymentReference() {
		return creditCardPaymentReference;
	}

	public URI getLink() {
		return link;
	}

	public void setCreditCardPaymentReference(String creditCardPaymentReference) {
		this.creditCardPaymentReference = creditCardPaymentReference;
	}

	public void setLink(URI link) {
		this.link = link;
	}
}
