package com.dev.gis.connector.sunny;

public class VehicleRequest extends Request {


	public VehicleRequest() {
		super(HSGW_DEMANDEDOBJECTS_GETCARS);
	}

	private Customer customer;

	// not used now
	private String specialId = null;

	private Long serviceCatalogId = null;
	private TravelInformation travel;
	private PayType payment;
	private OfferFilter filter;
	private Agency agency;
	
	//private int module;
	
	public Agency getAgency() {
		return agency;
	}

	public Customer getCustomer() {
		return customer;
	}

	public OfferFilter getFilter() {
		return filter;
	}

	public PayType getPayment() {
		return payment;
	}

	public Long getServiceCatalogId() {
		return serviceCatalogId;
	}

	public String getSpecialId() {
		return specialId;
	}


	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setFilter(OfferFilter filter) {
		this.filter = filter;
	}
	
	
	public void setPayment(PayType payment) {
		this.payment= payment;
	}
	
	
	public void setServiceCatalogId(Long serviceCatalogId) {
		this.serviceCatalogId = serviceCatalogId;
	}
	
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	

	@Override
	public String toString() {
		return "VehicleRequest [customer=" + customer + ", promoCode="
				+ ", serviceCatalogId=" + serviceCatalogId
				+ ", travel=" + travel + ", payment="
				+ payment + ", filter=" + filter 
				+ ", agency=" + agency + "]";
	}

	public TravelInformation getTravel() {
		return travel;
	}

	public void setTravel(TravelInformation travel) {
		this.travel = travel;
	}


}