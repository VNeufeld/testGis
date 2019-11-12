package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="extraResponse")
public class ExtraResponse extends Response
{
	private List<Extra> extras = new ArrayList<Extra>();
	
	private long remainingCacheSeconds;

	
	
	@XmlElement(required=false)
	public List<Extra> getExtras() {
		return extras;
	}
	
	public long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}
	
	public void setExtras(List<Extra> stations) {
		this.extras = stations;
	}
	
	public void setRemainingCacheSeconds(long remainingCacheSeconds) {
		this.remainingCacheSeconds = remainingCacheSeconds;
	}
}
