package com.dev.gis.task.execution.impl;

import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.JoiTask;
import com.dev.gis.task.execution.api.LocationSearchTask;

public class LocationSearchTaskDataProvider implements ITaskDataProvider {
	private LocationSearchTask task = null;

	@Override
	public String getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JoiTask getTask() {
		return task;
	}

	@Override
	public void loadTask() {
		task = new LocationSearchTask("lon");
		
	}

}
