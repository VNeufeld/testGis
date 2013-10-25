package com.dev.gis.task.execution.googleMap.impl;

import com.dev.gis.task.execution.api.IEditableTask;

public class GoogleMapTask implements IEditableTask  {

	@Override
	public String getViewID() {
		return "com.dev.gis.app.view.taskGoogleMap";
	}

}
