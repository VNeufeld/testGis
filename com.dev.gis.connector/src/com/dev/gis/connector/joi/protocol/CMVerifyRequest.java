package com.dev.gis.connector.joi.protocol;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.bpcs.mdcars.model.CarPoolInfo;
import com.bpcs.mdcars.soap.protocol.CustomerRequirements;
import com.dev.gis.connector.joi.protocol.Extra;

public class CMVerifyRequest extends BasicProtocol {

	private String promoCode;

	private String acceptedAvailability;

	private int transferType;

	private String flightNo;

	
	private CustomerRequirements customerRequirements;
	
	private com.bpcs.mdcars.model.Customer customer;
	
	private com.bpcs.mdcars.model.Customer driver;

	private List<com.bpcs.mdcars.model.Extra> extras;

	private CarPoolInfo carPoolInfo;


	@XmlElement(required = false)
	public String getFlightNo() {
		return flightNo;
	}

	@XmlElement(required = false)
	public String getPromoCode() {
		return promoCode;
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

	public CustomerRequirements getCustomerRequirements() {
		return customerRequirements;
	}

	public void setCustomerRequirements(CustomerRequirements customerRequirements) {
		this.customerRequirements = customerRequirements;
	}

	public List<com.bpcs.mdcars.model.Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<com.bpcs.mdcars.model.Extra> extras) {
		this.extras = extras;
	}

	public com.bpcs.mdcars.model.Customer getCustomer() {
		return customer;
	}

	public void setCustomer(com.bpcs.mdcars.model.Customer customer) {
		this.customer = customer;
	}

	public com.bpcs.mdcars.model.Customer getDriver() {
		return driver;
	}

	public void setDriver(com.bpcs.mdcars.model.Customer driver) {
		this.driver = driver;
	}

	public CarPoolInfo getCarPoolInfo() {
		return carPoolInfo;
	}

	public void setCarPoolInfo(CarPoolInfo carPoolInfo) {
		this.carPoolInfo = carPoolInfo;
	}


	
}
