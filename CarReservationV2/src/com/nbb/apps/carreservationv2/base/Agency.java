package com.nbb.apps.carreservationv2.base;


public class Agency {
	// ID of trvale agency ( Reise Büro )
	private Long agencyId;
	// alternative to agency Id
	private String agencyNo;
	// optional  - ID of the computer
	private String terminalId;
	// flag - is the person the agency employee
	private boolean travelAgentBooking;
	
//	// id of the person ( employee ? )
	private Long travelAgentId;
//	 alternative to travelAgentId
	private String travelAgentNo;
	
	
	
	
	public Long getAgencyId() {
		return agencyId;
	}
	public boolean isTravelAgentBooking() {
		return travelAgentBooking;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public void setTravelAgentBooking(boolean travelAgentBooking) {
		this.travelAgentBooking = travelAgentBooking;
	}
//	@Override
//	public String toString() {
//		return "Agency [agencyId=" + agencyId + ", agencyNo=" + agencyNo + ", terminalId=" + terminalId
//				+ ", travelAgentBooking=" + travelAgentBooking + ", travelAgentId=" + travelAgentId
//				+ ", travelAgentNo=" + travelAgentNo + "]";
//	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
//	public Long getTravelAgentId() {
//		return travelAgentId;
//	}
//	public void setTravelAgentId(Long travelAgentId) {
//		this.travelAgentId = travelAgentId;
//	}
//	public String getTravelAgentNo() {
//		return travelAgentNo;
//	}
//	public void setTravelAgentNo(String travelAgentNo) {
//		this.travelAgentNo = travelAgentNo;
//	}
	public String getAgencyNo() {
		return agencyNo;
	}
	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}
	public Long getTravelAgentId() {
		return travelAgentId;
	}
	public void setTravelAgentId(Long travelAgentId) {
		this.travelAgentId = travelAgentId;
	}
	public String getTravelAgentNo() {
		return travelAgentNo;
	}
	public void setTravelAgentNo(String travelAgentNo) {
		this.travelAgentNo = travelAgentNo;
	}
}
