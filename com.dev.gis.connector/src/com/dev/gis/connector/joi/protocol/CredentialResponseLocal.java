package com.dev.gis.connector.joi.protocol;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.bpcs.mdcars.json.protocol.AbstractJsonResponse;
import com.bpcs.mdcars.json.protocol.CredentialResponse;
import com.bpcs.mdcars.model.Clerk;
import com.bpcs.mdcars.model.Credential;
import com.bpcs.mdcars.model.Token;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="credential")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialResponseLocal extends AbstractJsonResponse {
	
	private static final long serialVersionUID = 1879202014273515881L;
	
	private CredentialLocal credential;

	private Clerk clerk;
	
	private Token token;
	
	private boolean smsCheckNeeded = false;
	
	private String smsSecurityCode;
	

	public CredentialLocal getCredential() {
		return credential;
	}

	public void setCredential(CredentialLocal credential) {
		this.credential = credential;
	}

	public Clerk getClerk() {
		return clerk;
	}

	public void setClerk(Clerk clerk) {
		this.clerk = clerk;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public boolean isSmsCheckNeeded() {
		return smsCheckNeeded;
	}

	public void setSmsCheckNeeded(boolean smsCheckNeeded) {
		this.smsCheckNeeded = smsCheckNeeded;
	}

	public String getSmsSecurityCode() {
		return smsSecurityCode;
	}

	public void setSmsSecurityCode(String smsSecurityCode) {
		this.smsSecurityCode = smsSecurityCode;
	}

}
