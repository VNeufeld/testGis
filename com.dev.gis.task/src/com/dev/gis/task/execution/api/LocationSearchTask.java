package com.dev.gis.task.execution.api;

public class LocationSearchTask extends JoiTask {
	
	private String location;
	private Integer country;
	private String resultFilter;

	
	public LocationSearchTask(String location) {
		super();
		this.location = location;
	}
	
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the country
	 */
	public Integer getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(Integer country) {
		this.country = country;
	}
	/**
	 * @return the resultFilter
	 */
	public String getResultFilter() {
		return resultFilter;
	}
	/**
	 * @param resultFilter the resultFilter to set
	 */
	public void setResultFilter(String resultFilter) {
		this.resultFilter = resultFilter;
	}
}
