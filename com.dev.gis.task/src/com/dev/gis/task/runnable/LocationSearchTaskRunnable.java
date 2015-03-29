package com.dev.gis.task.runnable;

import java.util.Date;

import com.bpcs.mdcars.protocol.HitType;
import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.task.execution.api.IResultView;
import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.LocationSearchConnector;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.api.TaskResultFactory;

public class LocationSearchTaskRunnable extends AbstractTaskRunnable  implements Runnable {
	
	public LocationSearchTaskRunnable(ITask task, IResultView resultView) {
		super(task,resultView);
	}

	@Override
	protected ITaskResult executeHttpCall(ITask task,Date startDate) {
		LocationSearchTask locationSearchTask = (LocationSearchTask)task;
		LocationSearchConnector httpConnector = new LocationSearchConnector(locationSearchTask);
		LocationSearchResult result = httpConnector.joiLocationSearch(locationSearchTask.getLocation(), HitType.UNINITIALIZED, 1l, 1l);
		return TaskResultFactory.createLocationResult(result,startDate);
	}

}
