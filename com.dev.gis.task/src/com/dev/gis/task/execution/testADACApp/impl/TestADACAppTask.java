package com.dev.gis.task.execution.testADACApp.impl;

import com.dev.gis.task.execution.api.IEditableTask;

public class TestADACAppTask implements IEditableTask  {

	@Override
	public String getViewID() {
		return "com.dev.gis.app.task.TestAppView";
	}

}
