package com.dev.gis.connector.joi.protocol;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="bookingRequest")
@XmlType(propOrder={ 	"customer", "driver", "payment", "incentiveCard", 
						"priceLimit", "promoCode", "acceptedAvailability", "transferType", "flightNo", "pickUpService", "dropDownService", 
						"travelAgent", "earliestDocumentPrintDate", 
						"insurances", "extras" } )
public class BookingRequest extends BasicProtocol {

	private String promoCode;

	private String acceptedAvailability;

	private Integer transferType;

	private String flightNo;

	private Address pickUpService;

	private Address dropDownService;

	private boolean travelAgent;

	private Person driver;

	private MoneyAmount priceLimit;

	private DayAndHour earliestDocumentPrintDate;

	private Customer customer;

	private List<Insurance> insurances;

	private List<Extra> extras;

	private IncentiveCard incentiveCard;

	private Payment payment;
	
	
	
	@XmlElement(required = false)
	public String getAcceptedAvailability() {
		return acceptedAvailability;
	}

	@XmlElement(required = false)
	public Customer getCustomer() {
		return customer;
	}

	@XmlElement(required = false)
	public Person getDriver() {
		return driver;
	}

	@XmlElement(required = false)
	public Address getDropDownService() {
		return dropDownService;
	}

	@XmlElement(required = false)
	public DayAndHour getEarliestDocumentPrintDate() {
		return earliestDocumentPrintDate;
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
	public IncentiveCard getIncentiveCard() {
		return incentiveCard;
	}

	@XmlElement(required = false)
	public List<Insurance> getInsurances() {
		return insurances;
	}

	@XmlElement(required = false)
	public Payment getPayment() {
		return payment;
	}

	@XmlElement(required = false)
	public Address getPickUpService() {
		return pickUpService;
	}

	@XmlElement(required = false)
	public MoneyAmount getPriceLimit() {
		return priceLimit;
	}

	@XmlElement(required = false)
	public String getPromoCode() {
		return promoCode;
	}


	public boolean isTravelAgent() {
		return travelAgent;
	}

	public void setAcceptedAvailability(String acceptedAvailability) {
		this.acceptedAvailability = acceptedAvailability;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public void setDropDownService(Address dropDownService) {
		this.dropDownService = dropDownService;
	}

	public void setEarliestDocumentPrintDate(DayAndHour earliestDocumentPrintDate) {
		this.earliestDocumentPrintDate = earliestDocumentPrintDate;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public void setIncentiveCard(IncentiveCard incentiveCard) {
		this.incentiveCard = incentiveCard;
	}

	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void setPickUpService(Address pickUpService) {
		this.pickUpService = pickUpService;
	}

	public void setPriceLimit(MoneyAmount priceLimit) {
		this.priceLimit = priceLimit;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}


	
	public void setTravelAgent(boolean travelAgent) {
		this.travelAgent = travelAgent;
	}

	public Integer getTransferType() {
		return transferType;
	}

	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}
}
