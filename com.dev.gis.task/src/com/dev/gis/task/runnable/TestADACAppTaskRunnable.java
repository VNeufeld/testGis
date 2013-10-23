package com.dev.gis.task.runnable;

import java.util.Date;

import com.dev.gis.task.execution.api.IResultView;
import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskResult;

public class TestADACAppTaskRunnable extends AbstractTaskRunnable implements
		Runnable {

	public TestADACAppTaskRunnable(ITask task, IResultView resultView) {
		super(task, resultView);
	}

	@Override
	protected ITaskResult executeHttpCall(ITask task, Date startDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
