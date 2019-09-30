package com.dev.gis.connector.sunny;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest extends Request {

	private Availability acceptedAvailability;

	private boolean travelAgent;

	private Person driver;

	private BookingTotalInfo bookingTotalInfo;

	private Customer customer;

	private List<Insurance> insurances;

	private List<Extra> extras;

	private Payment payment;

	private Offer offer;

	private Vehicle vehicle;

	private Agency agency;

	private TravelInformation travelInformation;

	private TransferInformationRequest transferInformation;

	private int commissionTariffId;

	public BookingRequest() {
		super(HSGW_DEMANDEDOBJECTS_BOOKING);
	}

	public Availability getAcceptedAvailability() {
		return acceptedAvailability;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Person getDriver() {
		return driver;
	}

	public List<Extra> getExtras() {
		return extras;
	}


	public List<Insurance> getInsurances() {
		return insurances;
	}

	public Offer getOffer() {
		return offer;
	}

	public Payment getPayment() {
		return payment;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public boolean isTravelAgent() {
		return travelAgent;
	}

	@JsonDeserialize(using = AvailabilityJsonDesirializer.class)
	public void setAcceptedAvailability(Availability acceptedAvailability) {
		this.acceptedAvailability = acceptedAvailability;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}


	public void setInsurances(List<Insurance> insurances) {
		this.insurances = insurances;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void setTravelAgent(boolean travelAgent) {
		this.travelAgent = travelAgent;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	@Override
	public String toString() {
		return "BookingRequest [acceptedAvailability=" + acceptedAvailability + ", transferInformation="
				+ transferInformation + ", travelAgent=" + travelAgent + ", driver=" + driver + ", customer="
				+ customer + ", insurances=" + insurances + ", extras=" + extras 
				+ ", payment=" + payment + ", offer=" + offer + ", vehicle=" + vehicle + ", agency=" + agency
				+ ", travelInformation=" + travelInformation + ", commissionTariffId=" + commissionTariffId + "]";
	}

	public Agency getAgency() {
		return agency;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public TravelInformation getTravelInformation() {
		return travelInformation;
	}

	public void setTravelInformation(TravelInformation travelInformation) {
		this.travelInformation = travelInformation;
	}

	public int getCommissionTariffId() {
		return commissionTariffId;
	}

	public void setCommissionTariffId(int commissionTariffId) {
		this.commissionTariffId = commissionTariffId;
	}

	public TransferInformationRequest getTransferInformation() {
		return transferInformation;
	}

	public void setTransferInformation(TransferInformationRequest transferInformation) {
		this.transferInformation = transferInformation;
	}

	public BookingTotalInfo getBookingTotalInfo() {
		return bookingTotalInfo;
	}

	public void setBookingTotalInfo(BookingTotalInfo bookingTotalInfo) {
		this.bookingTotalInfo = bookingTotalInfo;
	}

}
