package com.dev.gis.app.taskmanager;

import com.dev.gis.app.task.execution.LocationSearchTaskRunnable;
import com.dev.gis.task.execution.api.GetVehicleTask;
import com.dev.gis.task.execution.api.JoiTask;
import com.dev.gis.task.execution.api.LocationSearchTask;

public class TaskExecuter {
	
	void startExecutionTask(JoiTask task) {
		
		Runnable runnableTask = createRunnableTask(task,new TaskExecutionViewUpdater());
		Thread tr = new Thread(runnableTask);
		tr.start();
	}
	
	private Runnable createRunnableTask(JoiTask task, IResultView resultView) {
		if ( task instanceof LocationSearchTask) 
			return new LocationSearchTaskRunnable(task,resultView);
		
		if ( task instanceof GetVehicleTask) {
		}
		return null;
	}
}
