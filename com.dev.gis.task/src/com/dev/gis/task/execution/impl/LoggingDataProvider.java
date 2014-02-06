package com.dev.gis.task.execution.impl;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.logging.impl.LoggingTask;
import com.dev.gis.task.persistance.impl.LoggingPersistanceProvider;

public class LoggingDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	
	private LoggingTask task = null;
	
	public LoggingDataProvider() {
		super(new LoggingPersistanceProvider());
		this.task = new LoggingTask();
	}

	@Override
	public String getRequest() {
		return null;
	}

	@Override
	public ITask getTask() {
		return task;
	}

	@Override
	public void loadTask(String name) throws JAXBException {
		task = new LoggingTask();
		
	}

	@Override
	public void saveTask() {
		// TODO Auto-generated method stub
		
	}
}
