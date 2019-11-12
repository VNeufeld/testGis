package com.dev.gis.app.taskmanager;

import com.dev.gis.task.execution.api.IExecutableTask;

public class TaskExecuter {

	public static void startExecutionTask(IExecutableTask task)  {
			TaskViewUpdater viewUpdater = new TaskViewUpdater(task.getViewID());
			Runnable runnableTask = task.getRunnable(viewUpdater);
			Thread tr = new Thread(runnableTask);
			tr.start();
	}
	
}
