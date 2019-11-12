package com.dev.gis.task.execution.impl;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.logging.impl.LoggingSessionTask;
import com.dev.gis.task.execution.logging.impl.LoggingTask;
import com.dev.gis.task.persistance.impl.LoggingPersistanceProvider;

public class LoggingDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	
	private LoggingTask task = null;
	private LoggingSessionTask sessionTask = null;
	
	private final int taskNo;
	
	public LoggingDataProvider(int taskNo) {
		super(new LoggingPersistanceProvider());
		this.taskNo = taskNo;
		this.task = new LoggingTask();
		this.sessionTask = new LoggingSessionTask();
	}

	@Override
	public String getRequest() {
		return null;
	}

	@Override
	public ITask getTask() {
		switch ( taskNo) {
		case 1:
			return task;
		case 2:
			return sessionTask;
		default:
			return null;
		}
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
