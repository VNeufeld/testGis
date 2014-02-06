package com.dev.gis.task.execution.logging.impl;

import com.dev.gis.task.execution.api.IEditableTask;

public class LoggingTask implements IEditableTask  {

	@Override
	public String getViewID() {
		return "com.dev.gis.app.task.LoggingAppView";
	}

}
