package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="token")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Token extends BasicProtocol {
	private String token;
	private String expired;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}

}
