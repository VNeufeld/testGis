package com.dev.gis.app.task.execution;

import java.util.Date;

import com.bpcs.mdcars.protocol.LocationSearchResult;
import com.dev.gis.app.taskmanager.IResultView;
import com.dev.gis.connector.api.HttpConnectorFactory;
import com.dev.gis.connector.api.IHttpConnector;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.JoiTask;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.api.TaskResultFactory;

public class LocationSearchTaskRunnable extends AbstractTaskRunnable  implements Runnable {
	
	public LocationSearchTaskRunnable(JoiTask task, IResultView resultView) {
		super(task,resultView);
	}

	@Override
	protected ITaskResult executeHttpCall(JoiTask task,Date startDate) {
		LocationSearchTask locationSearchTask = (LocationSearchTask)task;
		IHttpConnector httpConnector = HttpConnectorFactory.createLocationSearchConnector( locationSearchTask);
		LocationSearchResult result = httpConnector.joiLocationSearch(locationSearchTask.getLocation());
		return TaskResultFactory.createLocationResult(result,startDate);
	}

}
