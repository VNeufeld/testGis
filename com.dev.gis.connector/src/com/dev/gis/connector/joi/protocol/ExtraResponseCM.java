package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="extraResponse")
public class ExtraResponseCM extends Response
{
	private List<com.bpcs.mdcars.model.Extra> extras = new ArrayList<com.bpcs.mdcars.model.Extra>();
	
	private long remainingCacheSeconds;

	
	
	@XmlElement(required=false)
	public List<com.bpcs.mdcars.model.Extra> getExtras() {
		return extras;
	}
	
	public long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}
	
	public void setExtras(List<com.bpcs.mdcars.model.Extra> stations) {
		this.extras = stations;
	}
	
	public void setRemainingCacheSeconds(long remainingCacheSeconds) {
		this.remainingCacheSeconds = remainingCacheSeconds;
	}
}
