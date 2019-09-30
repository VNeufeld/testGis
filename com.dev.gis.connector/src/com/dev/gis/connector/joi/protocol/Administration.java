package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="administration")
@XmlType(propOrder = {"broker", "brokerId", "calledFrom", "language", "operator", "salesChannel", "sessionId", "requestPaymentInformation" })
public class Administration extends BasicProtocol {

	private String language = "";

	private long operator;

	private int salesChannel;

	private boolean broker;

	private String brokerId;
	
	private int calledFrom;
	
	private String sessionId;
	
	private Boolean requestPaymentInformation = true;


	
	
	@XmlElement(required=false)
	public String getBrokerId() {
		return brokerId;
	}
	
	public int getCalledFrom() {
		return calledFrom;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public long getOperator() {
		return operator;
	}
	
	public int getSalesChannel() {
		return salesChannel;
	}
	
	@XmlElement(required=false)
	public String getSessionId() {
		return sessionId;
	}
	
	public boolean isBroker() {
		return broker;
	}
	
	@XmlElement(required=false)
	public Boolean isRequestPaymentInformation() {
		return requestPaymentInformation;
	}
	
	public void setBroker(boolean broker) {
		this.broker = broker;
	}
	
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	
	public void setCalledFrom(int calledFrom) {
		this.calledFrom = calledFrom;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	
	public void setOperator(long operator) {
		this.operator = operator;
	}

	
	public void setRequestPaymentInformation(Boolean requestPaymentInformation) {
		this.requestPaymentInformation = requestPaymentInformation;
	}

	public void setSalesChannel(int salesChannel) {
		this.salesChannel = salesChannel;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
