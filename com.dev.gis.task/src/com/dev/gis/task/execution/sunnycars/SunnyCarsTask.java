package com.dev.gis.task.execution.sunnycars;

import com.dev.gis.task.execution.api.IEditableTask;

public class SunnyCarsTask implements IEditableTask  {

	@Override
	public String getViewID() {
		return IEditableTask.ID_TestSunnyCarsView;
	}

}
