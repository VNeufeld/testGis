package com.dev.gis.app.taskmanager;

import com.dev.gis.task.execution.api.IExecutableTask;
import com.dev.gis.task.execution.api.IResultView;

public class TaskExecuter {
	
	public static void startExecutionTask(IExecutableTask task)  {
		
		
		try {
			IResultView viewUpdater = (IResultView)Class.forName(task.getResultViewClassName()).newInstance();
			Runnable runnableTask = task.getRunnable(viewUpdater);
			Thread tr = new Thread(runnableTask);
			tr.start();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
