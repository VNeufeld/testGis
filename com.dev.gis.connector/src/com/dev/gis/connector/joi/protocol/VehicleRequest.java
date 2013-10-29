package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="vehicleRequest")
@XmlType(propOrder = {"administration", "demandedObject", "customer", "module", "travelSpecification", "serviceCatalogId", "promoCode", "specialId", "payment", "filter" })
public class VehicleRequest extends Request {

	private Customer customer;

	private String promoCode;

	/**
	 * value range:
	 * >0: specified special
	 * Integer.MIN_VALUE: not initialized
	 */
	private String specialId = null;

	/**
	 * value range:
	 * -1: default
	 * -9: all
	 * >0: specifies catalog explicitly 
	 * Integer.MIN_VALUE: not initialized
	 */
	private Integer serviceCatalogId = null;

	private TravelInformation travelSpecification;
	
	private PayType payment;

	private VehicleRequestFilter filter;
	
	private Module module;

	
	
	@XmlElement(required=false)
	public Customer getCustomer() {
		return customer;
	}

	@XmlElement(required=false)
	public VehicleRequestFilter getFilter() {
		return filter;
	}

	@XmlElement(required=false)
	public PayType getPayment() {
		return payment;
	}

	@XmlElement(required=false)
	public String getPromoCode() {
		return promoCode;
	}

	@XmlElement(required=false)
	public Integer getServiceCatalogId() {
		return serviceCatalogId;
	}

	@XmlElement(required=false)
	public String getSpecialId() {
		return specialId;
	}

	@XmlElement(name="travel")
	public TravelInformation getTravelSpecification() {
		return travelSpecification;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setFilter(VehicleRequestFilter filter) {
		this.filter = filter;
	}

	public void setPayment(PayType payment) {
		this.payment= payment;
	}
	
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	
	public void setServiceCatalogId(Integer serviceCatalogId) {
		this.serviceCatalogId = serviceCatalogId;
	}
	
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	
	public void setTravelSpecification(TravelInformation travelSpecification) {
		this.travelSpecification = travelSpecification;
	}
	
	public Module getModule() {
		return module;
	}
	
	public void setModule(Module module) {
		this.module = module;
	}
}