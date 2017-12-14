package com.dev.gis.task.execution.impl;

import com.dev.gis.task.execution.api.IEditableTask;

public class ClubMobilReservationTask implements IEditableTask  {

	@Override
	public String getViewID() {
		return IEditableTask.ID_ClubMobilReservationView;
	}

}
