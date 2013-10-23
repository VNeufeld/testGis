package com.dev.gis.task.execution.api;

import javax.xml.bind.annotation.XmlRootElement;

import com.dev.gis.task.runnable.LocationSearchTaskRunnable;

@XmlRootElement(name="locationSearchTask")
public class LocationSearchTask extends JoiTask implements IExecutableTask {
	
	private static String TASKNAME_DEFAULT = "locationSearch";
	
	private static final String RESULT_VIEW_CLASS_NAME = "com.dev.gis.app.taskmanager.TaskExecutionViewUpdater";
	
	private String location;
	private Integer country;
	private String resultFilter;


	public LocationSearchTask() {
		super(TASKNAME_DEFAULT);
	}
	
	public LocationSearchTask(String location) {
		super(TASKNAME_DEFAULT);
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

	public static LocationSearchTask getDefaultTask() {
		return new LocationSearchTask("lon");
	}

	@Override
	public String toString() {
		return "LocationSearchTask [location=" + location + ", country="
				+ country + ", resultFilter=" + resultFilter
				+ ", haJoiServiceLink=" + haJoiServiceLink + ", name=" + name
				+ "]";
	}

	@Override
	public void execute() {

		
	}

	@Override
	public void editInputParameter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Runnable getRunnable(IResultView resultView) {
		return new LocationSearchTaskRunnable(this,resultView);
	}

	@Override
	public String getResultViewClassName() {
		return RESULT_VIEW_CLASS_NAME;
	}

}