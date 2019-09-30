package com.dev.gis.connector.sunny;

public class TransferInformationRequest extends BasicProtocol{

	private int transferTypeId;
	private String flightNo;
	private String puName;
	private String puAddress;
	private String puPhone;
	private String doName;
	private String doAddress;
	private String doPhone;
	
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	public String getPuName() {
		return puName;
	}
	public void setPuName(String puName) {
		this.puName = puName;
	}
	public String getPuAddress() {
		return puAddress;
	}
	public void setPuAddress(String puAddress) {
		this.puAddress = puAddress;
	}
	public String getPuPhone() {
		return puPhone;
	}
	public void setPuPhone(String puPhone) {
		this.puPhone = puPhone;
	}
	public String getDoName() {
		return doName;
	}
	public void setDoName(String doName) {
		this.doName = doName;
	}
	public String getDoAddress() {
		return doAddress;
	}
	public void setDoAddress(String doAddress) {
		this.doAddress = doAddress;
	}
	public String getDoPhone() {
		return doPhone;
	}
	public void setDoPhone(String doPhone) {
		this.doPhone = doPhone;
	}

}
