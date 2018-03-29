package com.dev.gis.connector.joi.protocol;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.dev.gis.connector.joi.protocol.Extra;

public class CMVerifyRequest extends BasicProtocol {

	private String promoCode;

	private String acceptedAvailability;

	private int transferType;

	private String flightNo;

	private List<Extra> extras;


	@XmlElement(required = false)
	public List<Extra> getExtras() {
		return extras;
	}

	@XmlElement(required = false)
	public String getFlightNo() {
		return flightNo;
	}

	@XmlElement(required = false)
	public String getPromoCode() {
		return promoCode;
	}


	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getAcceptedAvailability() {
		return acceptedAvailability;
	}

	public void setAcceptedAvailability(String acceptedAvailability) {
		this.acceptedAvailability = acceptedAvailability;
	}

	public int getTransferType() {
		return transferType;
	}

	public void setTransferType(int transferType) {
		this.transferType = transferType;
	}


	
}