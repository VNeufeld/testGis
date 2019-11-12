package com.dev.gis.connector.joi.protocol;

import java.util.Date;

import com.bpcs.mdcars.model.BasicObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) 
@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialLocal extends BasicObject {
	
	private static final long serialVersionUID = 1499770324131663197L;
	
	private String token;
	private String expired;
	private String user;
	private String pwd;
	private String newpwd;
	
	private boolean reset;
	
	private String smsSecurityCode;
	
	private int calledFrom;

	
	private Date pwdValidTo;
	
	private String appVersion;

	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public boolean isReset() {
		return reset;
	}
	public void setReset(boolean reset) {
		this.reset = reset;
	}
	public Date getPwdValidTo() {
		return pwdValidTo;
	}
	public void setPwdValidTo(Date pwdValidTo) {
		this.pwdValidTo = pwdValidTo;
	}
	public String getNewpwd() {
		return newpwd;
	}
	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}
	public String getSmsSecurityCode() {
		return smsSecurityCode;
	}
	public void setSmsSecurityCode(String smsSecurityCode) {
		this.smsSecurityCode = smsSecurityCode;
	}
	public int getCalledFrom() {
		return calledFrom;
	}
	public void setCalledFrom(int calledFrom) {
		this.calledFrom = calledFrom;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

}
