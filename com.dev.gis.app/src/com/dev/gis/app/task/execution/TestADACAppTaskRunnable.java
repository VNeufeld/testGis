package com.dev.gis.app.task.execution;

import java.util.Date;

import com.dev.gis.app.taskmanager.IResultView;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiTask;

public class TestADACAppTaskRunnable extends AbstractTaskRunnable implements
		Runnable {

	public TestADACAppTaskRunnable(JoiTask task, IResultView resultView) {
		super(task, resultView);
	}

	@Override
	protected ITaskResult executeHttpCall(JoiTask task, Date startDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
