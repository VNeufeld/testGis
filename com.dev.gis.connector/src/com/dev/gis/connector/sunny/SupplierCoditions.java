package com.dev.gis.connector.sunny;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SupplierCoditions {
	
	private	List<Pair<String,String>> supplierConditions = new ArrayList<Pair<String,String>>();
	
	private URI supplierConditionsUrl;

	public List<Pair<String,String>> getSupplierConditions() {
		return supplierConditions;
	}

	public void setSupplierConditions(List<Pair<String, String>> supplierConditions) {
		this.supplierConditions =  	supplierConditions;	
	}

	public URI getSupplierConditionsUrl() {
		return supplierConditionsUrl;
	}

	public void setSupplierConditionsUrl(URI supplierConditionsUrl) {
		this.supplierConditionsUrl = supplierConditionsUrl;
	}
	

}

