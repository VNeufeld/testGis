package com.dev.gis.connector.sunny;

public class Administration extends BasicProtocol {

	private String language = "";
	private long operator;
	private long salesChannel;
	private boolean broker;
	private String brokerId;
	private long calledFrom;
	private String provider;
	private long providerId;

	
	
	public String getBrokerId() {
		return brokerId;
	}

	public long getCalledFrom() {
		return calledFrom;
	}

	public String getLanguage() {
		return language;
	}

	public long getOperator() {
		return operator;
	}

	public String getProvider() {
		return provider;
	}

	public long getProviderId() {
		return providerId;
	}
	public long getSalesChannel() {
		return salesChannel;
	}

	public boolean isBroker() {
		return broker;
	}
	
	public void setBroker(boolean broker) {
		this.broker = broker;
	}
	
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	
	public void setCalledFrom(long calledFrom) {
		this.calledFrom = calledFrom;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setOperator(long operator) {
		this.operator = operator;
	}
	
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	
	public void setSalesChannel(long salesChannel) {
		this.salesChannel = salesChannel;
	}
}
