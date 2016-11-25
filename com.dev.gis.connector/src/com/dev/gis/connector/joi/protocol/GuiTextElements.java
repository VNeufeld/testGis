package com.dev.gis.connector.joi.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "text")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GuiTextElements extends BasicProtocol {

	private List<Supplier> supplierList = new ArrayList<>();

	private List<BodyStyleText> bodyStyleList = new ArrayList<>();

	private List<Station> stationList = new ArrayList<Station>();

	private List<PaymentInformation> paymentInfoList;

	private List<SupplierGroup> supplierGroupList = new ArrayList<>();

	public List<BodyStyleText> getBodyStyleList() {
		return bodyStyleList;
	}

	@XmlElement(required=false)
	public List<PaymentInformation> getPaymentInfoList() {
		return paymentInfoList;
	}

	public List<Station> getStationList() {
		return stationList;
	}

	public List<Supplier> getSupplierList() {
		return supplierList;
	}

	public Station retrieveStation (long stationId) {
		for (Station station : this.stationList) {
			if (station.getId() == stationId)
				return station;
		}
		return null;
	}

	public void setBodyStyleList(List<BodyStyleText> bodyStyleList) {
		this.bodyStyleList = bodyStyleList;
	}
	
	public void setPaymentInfoList(List<PaymentInformation> paymentInfoList) {
		this.paymentInfoList = paymentInfoList;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}

	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}

	public List<SupplierGroup> getSupplierGroupList() {
		return supplierGroupList;
	}

	public void setSupplierGroupList(List<SupplierGroup> supplierGroupList) {
		this.supplierGroupList = supplierGroupList;
	}
}
