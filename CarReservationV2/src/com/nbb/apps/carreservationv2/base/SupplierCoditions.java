package com.nbb.apps.carreservationv2.base;

import java.util.ArrayList;
import java.util.List;

import android.util.Pair;


public class SupplierCoditions {
	
	private	List<Pair<String,String>> supplierConditions = new ArrayList<Pair<String,String>>();

	public List<Pair<String,String>> getSupplierConditions() {
		return supplierConditions;
	}

	public void setSupplierConditions(List<Pair<String, String>> supplierConditions) {
		this.supplierConditions =  	supplierConditions;	
	}
	

}
