package com.dev.gis.task.execution.impl;

import com.dev.gis.task.execution.api.IEditableTask;

public class EmlTask implements IEditableTask  {

	@Override
	public String getViewID() {
		return "com.dev.gis.app.task.EmlView";
	}

}
