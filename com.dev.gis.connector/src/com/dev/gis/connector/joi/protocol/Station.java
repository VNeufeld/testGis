package com.dev.gis.connector.joi.protocol;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "station")
@XmlType(propOrder =
	{ "id", "link", "stationName", "stationPriority", "airportStation", "airport", "cityId", "supplierId", "supplierGroupId",
			"supplierPriority", "assignedCityIds", "modules", "area", "countryId", "address", "info", "phone", "fax" })
public class Station extends BasicProtocol {

	private long id;

	private URI link;



	private Long supplierId;

	private String stationName;



	private Long supplierGroupId;

	private Integer stationPriority;

	private Integer supplierPriority;

	private Boolean airportStation;

	private String airport;

	private long[] assignedCityIds;

	private String[] modules;

	private Long countryId = null;

	private Long cityId = null;

	private Long area = null;

	private Address address = null;

	private StationInfo info = null;

	private PhoneNumber phone = null;

	private PhoneNumber fax = null;
	



	public Station() {
	}

	public Station(int stationId) {
		this.id = stationId;
	}

	

	@XmlElement(required = false)
	public Address getAddress() {
		return address;
	}

	@XmlElement(required = false)
	public String getAirport() {
		return airport;
	}

	@XmlElement(required = false)
	public Long getArea() {
		return area;
	}

	@XmlElement(required = false)
	public long[] getAssignedCityIds() {
		return assignedCityIds;
	}

	@XmlElement(required = false)
	public Long getCityId() {
		return cityId;
	}

	@XmlElement(required = false)
	public Long getCountryId() {
		return countryId;
	}

	@XmlElement(required=false)
	public PhoneNumber getFax() {
		return fax;
	}

	public long getId() {
		return id;
	}

	@XmlElement(required = false)
	public StationInfo getInfo() {
		return info;
	}

	@XmlElement(required = false)
	public URI getLink() {
		return link;
	}

	@XmlElement(required = false)
	public String[] getModules() {
		return modules;
	}

	@XmlElement(required=false)
	public PhoneNumber getPhone() {
		return phone;
	}

	@XmlElement(required = false)
	public String getStationName() {
		return stationName;
	}

	@XmlElement(required = false)
	public Integer getStationPriority() {
		return stationPriority;
	}

	@XmlElement(required = false)
	public Long getSupplierGroupId() {
		return supplierGroupId;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	@XmlElement(required = false)
	public Integer getSupplierPriority() {
		return supplierPriority;
	}

	public Boolean isAirportStation() {
		return airportStation;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public void setAirportStation(Boolean airportStation) {
		this.airportStation = airportStation;
	}

	public void setArea(Long area) {
		this.area = area;
	}

	public void setAssignedCityIds(long[] assignedCityIds) {
		this.assignedCityIds = assignedCityIds;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public void setFax(PhoneNumber fax) {
		this.fax = fax;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setInfo(StationInfo info) {
		this.info = info;
	}

	public void setLink(URI link) {
		this.link = link;
	}

	public void setModules(String[] modules) {
		this.modules = modules;
	}

	public void setPhone(PhoneNumber phone) {
		this.phone = phone;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public void setStationPriority(Integer stationPriority) {
		this.stationPriority = stationPriority;
	}

	public void setSupplierGroupId(Long supplierGroupId) {
		this.supplierGroupId = supplierGroupId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public void setSupplierPriority(Integer supplierPriority) {
		this.supplierPriority = supplierPriority;
	}
}