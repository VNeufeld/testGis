package com.dev.gis.connector.joi.protocol;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Offer extends BasicProtocol {
	
	private final static int MAX_INCLUSIVES = 10;

	private UUID id;

	private URI link;
	
	private URI bookLink;
	
	private Long remainingCacheSeconds;

	
	
	private long supplierId;
	
	private MoneyAmount price;

	private MoneyAmount stdPrice;

	private MoneyAmount dailyPrice;
	
	private String status;

	private List<Inclusive> inclusives;

	private String promoText;

	private Boolean oneWayInclusive;

	private MoneyAmount oneWayFee;
	
	private long ServiceCatalogId;
	
	private String ServiceCatalogCode;
	
	private String offeredPayment;
	
	private boolean member;
	
	private boolean noMember;
	
	private long businessSegmentId;
	
	private String businessSegmentCode;
	
	
	public URI getBookLink() {
		return bookLink;
	}

	public UUID getId() {
		return id;
	}

	@XmlElement(required=false)
	public List<Inclusive> getInclusives() {
		return inclusives;
	}

	@XmlElement(required=false)
	public URI getLink() {
		return link;
	}

	public String getOfferedPayment() {
		return offeredPayment;
	}

	@XmlElement(required=false)
	public MoneyAmount getOneWayFee() {
		return oneWayFee;
	}

	public MoneyAmount getPrice() {
		return price;
	}

	@XmlElement(required=false)
	public String getPromoText() {
		return promoText;
	}

	@XmlElement(required=false)
	public Long getRemainingCacheSeconds() {
		return remainingCacheSeconds;
	}
	
	public String getServiceCatalogCode() {
		return ServiceCatalogCode;
	}
	
	public long getServiceCatalogId() {
		return ServiceCatalogId;
	}

	public String getStatus() {
		return status;
	}

	public MoneyAmount getStdPrice() {
		return stdPrice;
	}

	public long getSupplierId() {
		return supplierId;
	}

	@XmlElement(required=false)
	public boolean isMember() {
		return member;
	}

	@XmlElement(required=false)
	public boolean isNoMember() {
		return noMember;
	}

	@XmlElement(required=false)
	public Boolean isOneWayInclusive() {
		return oneWayInclusive;
	}

	public void limitInclusives() {
		if(inclusives.size() > MAX_INCLUSIVES) {
			List<Inclusive> newList = new ArrayList<>();
			int index = 0;

			for(Inclusive inclusive : inclusives) {
				newList.add(inclusive);
				index++;
				if(index > MAX_INCLUSIVES)
					break;
			}
			setInclusives(newList);
		}
	}

	public void setBookLink(URI bookLink) {
		this.bookLink = bookLink;
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
	
	public void setMember(boolean member) {
		this.member = member;
	}
	
	public void setNoMember(boolean noMember) {
		this.noMember = noMember;
	}
	
	public void setOfferedPayment(String offeredPayment) {
		this.offeredPayment = offeredPayment;
	}
	
	public void setOneWayFee(MoneyAmount oneWayFee) {
		this.oneWayFee = oneWayFee;
	}
	
	public void setOneWayInclusive(Boolean oneWayInclusive) {
		this.oneWayInclusive = oneWayInclusive;
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

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setStdPrice(MoneyAmount stdPrice) {
		this.stdPrice = stdPrice;
	}
	
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public MoneyAmount getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(MoneyAmount dailyPrice) {
		this.dailyPrice = dailyPrice;
	}

	public long getBusinessSegmentId() {
		return businessSegmentId;
	}

	public void setBusinessSegmentId(long businessSegmentId) {
		this.businessSegmentId = businessSegmentId;
	}

	public String getBusinessSegmentCode() {
		return businessSegmentCode;
	}

	public void setBusinessSegmentCode(String businessSegmentCode) {
		this.businessSegmentCode = businessSegmentCode;
	}
}
