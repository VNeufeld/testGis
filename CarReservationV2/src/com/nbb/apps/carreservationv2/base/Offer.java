package com.nbb.apps.carreservationv2.base;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import android.util.Pair;



public class Offer extends Response {
	
	public final String PROMOTION_NOT_REQUIRED = "0";
	public final String PROMOTION_NOT_VALID = "1";    // Not valid
	public final String PROMOTION_NOT_USED = "2";  // promotion exist but not used for this car
	public final String PROMOTION_USED = "3";	  //promotion exist and used for this car
	public final String PROMOTION_ERROR = "-1";	  //promotion exist and used for this car
	
	public final static String TARIF_NORMAL = "1";
	public final static String TARIF_SUNSHINE = "2";

	private UUID id;
	private URI link;
	private URI bookLink;
	private Long remainingCacheSeconds;

	
	private String name;
	private long supplierId;
	private long ServiceCatalogId;
	private String ServiceCatalogCode;
	
	private PayType offeredPayment;
	private MoneyAmount price;
	private MoneyAmount stdPrice;
	private MoneyAmount promoValue;
	private MoneyAmount cancelFee;
	
	private MoneyAmount oneWayFee;

	private MoneyAmount outOfHourPrice;
	
	private MoneyAmount excessPrice;
	
	private Availability status;

	private List<Inclusive> inclusives;
	
	private List<Extra> extras;
	
	
	private String promoText;

	private long pickUpStationId;
	private long dropOffStationId;
	private long vehicleId;

	private String commissionType = TARIF_NORMAL;
	
	private String carUsedPromotion;
	
	private Long   carCategoryId;
	
	private SupplierCoditions supplierCoditions = new SupplierCoditions();
	
	private Upsell  upsell;
	
	public URI getBookLink() {
		return bookLink;
	}

	public long getDropOffStationId() {
		return dropOffStationId;
	}

	public UUID getId() {
		return id;
	}

	public List<Inclusive> getInclusives() {
		return inclusives;
	}

	public URI getLink() {
		return link;
	}

	public PayType getOfferedPayment() {
		return offeredPayment;
	}

	public MoneyAmount getOneWayFee() {
		return oneWayFee;
	}

	public long getPickUpStationId() {
		return pickUpStationId;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	public String getPromoText() {
		return promoText;
	}

	public Long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}

	public String getServiceCatalogCode() {
		return ServiceCatalogCode;
	}

	public long getServiceCatalogId() {
		return ServiceCatalogId;
	}

	public Availability getStatus() {
		return status;
	}

	public MoneyAmount getStdPrice() {
		return stdPrice;
	}
	
	public long getSupplierId() {
		return supplierId;
	}

	public long getVehicleId() {
		return vehicleId;
	}


	public void setBookLink(URI bookLink) {
		this.bookLink = bookLink;
	}

	public void setDropOffStationId(long dropOffStationId) {
		this.dropOffStationId = dropOffStationId;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setInclusives(List<Inclusive> inclusives) {
		this.inclusives = inclusives;
	}
	
	public void setLink(URI link) {
		this.link = link;
	}
	
	public void setOfferedPayment(PayType offeredPayment) {
		this.offeredPayment = offeredPayment;
	}
	
	public void setOneWayFee(MoneyAmount oneWayFee) {
		this.oneWayFee = oneWayFee;
	}
	
	
	public void setPickUpStationId(long pickUpStationId) {
		this.pickUpStationId = pickUpStationId;
	}
	
	public void setPrice(MoneyAmount price) {
		this.price = price;
	}
	
	public void setPromoText(String promoText) {
		this.promoText = promoText;
	}
	
	public void setRemainingCacheSeconds(Long remainingCacheSeconds) {
		this.remainingCacheSeconds = remainingCacheSeconds;
	}
	
	public void setServiceCatalogCode(String serviceCatalogCode) {
		ServiceCatalogCode = serviceCatalogCode;
	}

	public void setServiceCatalogId(long serviceCatalogId) {
		ServiceCatalogId = serviceCatalogId;
	}

	@JsonDeserialize(using = AvailabilityJsonDesirializer.class)
	public void setStatus(Availability status) {
		this.status = status;
	}
	
	public void setStdPrice(MoneyAmount stdPrice) {
		this.stdPrice = stdPrice;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public void setVehicleId(long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Extra> getExtras() {
		return extras;
	}

	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}

	public String getCommissionType() {
		return commissionType;
	}

	public void setCommissionType(String commissionType) {
		if ( commissionType != null )
			this.commissionType = commissionType;
	}


	public MoneyAmount getExcessPrice() {
		return excessPrice;
	}

	public void setExcessPrice(MoneyAmount excessPrice) {
		this.excessPrice = excessPrice;
	}

	public MoneyAmount getOutOfHourPrice() {
		return outOfHourPrice;
	}

	public void setOutOfHourPrice(MoneyAmount outOfHourPrice) {
		this.outOfHourPrice = outOfHourPrice;
	}

	public MoneyAmount getPromoValue() {
		return promoValue;
	}

	public void setPromoValue(MoneyAmount promoValue) {
		this.promoValue = promoValue;
	}

	public SupplierCoditions getSupplierCoditions() {
		return supplierCoditions;
	}

	public void setSupplierCoditions(List<Pair<String, String>> supplierConditions) {
		supplierCoditions.setSupplierConditions(supplierConditions);
	}

	public String getCarUsedPromotion() {
		return carUsedPromotion;
	}
	
	public boolean isSunshineTariff() {
		return TARIF_SUNSHINE.equals(this.commissionType);
	}
	

	public void setCarUsedPromotion(String carUsedPromotion, String userPromotion) {
		
		// avoid null pointer exception
		if ( carUsedPromotion == null)
			carUsedPromotion="";
		
		if (isEmpty(userPromotion) )
			this.carUsedPromotion = PROMOTION_NOT_REQUIRED;
		else if ("-1".equals(userPromotion))
			this.carUsedPromotion = PROMOTION_NOT_VALID;
		else if ( !carUsedPromotion.contains(userPromotion) )
			this.carUsedPromotion = PROMOTION_NOT_USED;
		else if ( carUsedPromotion.contains(userPromotion) )
			this.carUsedPromotion = PROMOTION_USED;
		else
			this.carUsedPromotion = PROMOTION_ERROR;
	}

	public Upsell getUpsell() {
		return upsell;
	}

	public Long getCarCategoryId() {
		return carCategoryId;
	}

	public void setCarCategoryId(Long carCategoryId) {
		this.carCategoryId = carCategoryId;
	}

	public void setUpsell(Upsell upsell) {
		this.upsell = upsell;
	}

	public MoneyAmount getCancelFee() {
		return cancelFee;
	}

	public void setCancelFee(MoneyAmount cancelFee) {
		this.cancelFee = cancelFee;
		if ( cancelFee != null ) {
			if ( this.cancelFee.getCurrency() == null && this.price != null)
				this.cancelFee.setCurrency(this.price.getCurrency());
		}
	}

	@Override
	public String toString() {
		return "Of: "+name+" price: "+ this.price.getAmount()+ " "+this.price.getCurrency();
	}

}
