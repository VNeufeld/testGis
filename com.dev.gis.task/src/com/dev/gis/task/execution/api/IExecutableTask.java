package com.dev.gis.task.execution.api;

public interface IExecutableTask extends ITask {
	
	void execute();
	
	void editInputParameter();
	
	Runnable  getRunnable(IResultView resultView);

}
