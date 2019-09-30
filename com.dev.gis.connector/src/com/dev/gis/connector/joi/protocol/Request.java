package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;



/**
 * @author bre
 *         The class provides the minimal content of a response in the JOI
 *         context.
 */
@XmlTransient
public class Request extends BasicProtocol {

	public static final int INVALID_INT_VALUE = Integer.MIN_VALUE;
	
	private Administration administration;
	
	private int demandedObject;


	
	public Administration getAdministration() {
		return administration;
	}

	@XmlElement(required=false)
	public int getDemandedObject() {
		return demandedObject;
	}

	public String requestName() {
		return "XML";
	}
	
	public void setAdministration(Administration administration) {
		this.administration = administration;
	}
	
	public void setDemandedObject(int demandedObject) {
		this.demandedObject = demandedObject;
	}
	
	public String userType() {
		return "P";
	}
}