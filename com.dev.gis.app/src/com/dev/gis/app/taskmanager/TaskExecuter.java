package com.dev.gis.app.taskmanager;

import com.dev.gis.app.task.execution.LocationSearchTaskRunnable;
import com.dev.gis.app.task.execution.TestADACAppTaskRunnable;
import com.dev.gis.task.execution.api.GetVehicleTask;
import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.api.TestADACAppTask;

public class TaskExecuter {
	
	public static void startExecutionTask(ITask task) {
		
		Runnable runnableTask = createRunnableTask(task,new TaskExecutionViewUpdater());
		Thread tr = new Thread(runnableTask);
		tr.start();
	}
	
	private static Runnable createRunnableTask(ITask task, IResultView resultView) {
		if ( task instanceof LocationSearchTask) 
			return new LocationSearchTaskRunnable(task,resultView);
		
		if ( task instanceof GetVehicleTask) {
		}
		
		if ( task instanceof TestADACAppTask) {
			return new TestADACAppTaskRunnable(task, resultView);
		}
		return null;
	}
}
