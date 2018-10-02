package com.dev.gis.connector.joi.protocol;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.bpcs.mdcars.model.Address;
import com.bpcs.mdcars.soap.protocol.CustomerRequirements;
import com.dev.gis.connector.joi.protocol.Extra;

public class CMBookingRequest extends BasicProtocol {

	private String promoCode;
	
	private String acceptedAvailability;

	private int transferType;

	private String flightNo;

	private Address pickUpService;

	private Address dropDownService;

	private List<Extra> extras;
	
	private CustomerRequirements customerRequirements;

	@XmlElement(required = false)
	public Address getDropDownService() {
		return dropDownService;
	}


	@XmlElement(required = false)
	public List<Extra> getExtras() {
		return extras;
	}

	@XmlElement(required = false)
	public String getFlightNo() {
		return flightNo;
	}


	@XmlElement(required = false)
	public Address getPickUpService() {
		return pickUpService;
	}


	@XmlElement(required = false)
	public String getPromoCode() {
		return promoCode;
	}

	public void setDropDownService(Address dropDownService) {
		this.dropDownService = dropDownService;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public void setPickUpService(Address pickUpService) {
		this.pickUpService = pickUpService;
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

	

}
