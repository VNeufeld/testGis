package com.dev.gis.connector.sunny;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiTextElements extends BasicProtocol {

	private List<Supplier> supplierList = new ArrayList<Supplier>();

	private Map<Long,Text> bodyStyleMap = new HashMap<Long,Text>();

	private List<Station> stationList = new ArrayList<Station>();

	public void addStationList(Station station) {
		this.stationList.add(station);
	}

	public Map<Long,Text> getBodyStyleMap() {
		return bodyStyleMap;
	}

	public List<Station> getStationList() {
		return stationList;
	}

	public List<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setBodyStyleMap(Map<Long,Text> bodyStyleMap) {
		this.bodyStyleMap = bodyStyleMap;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}

	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}
}
