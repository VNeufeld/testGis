package com.nbb.apps.carreservationv2.base;




/**
 * @author bre
 *         The class provides the minimal content of a response in the JOI
 *         context.
 */
public class Request extends BasicProtocol implements HsgwConstants {

	public Request(String demandedObject) {
		super();
		this.demandedObject = demandedObject;
	}

	public static final int INVALID_INT_VALUE = Integer.MIN_VALUE;
	
	private Administration administration;
	
	private String demandedObject;
	
	public Administration getAdministration() {
		return administration;
	}

	public String getDemandedObject() {
		return demandedObject;
	}

	public String requestName() {
		return "XML";
	}
	
	public void setAdministration(Administration administration) {
		this.administration = administration;
	}
	
	public void setDemandedObject(String demandedObject) {
		this.demandedObject = demandedObject;
	}
	
	public String userType() {
		return "P";
	}
}