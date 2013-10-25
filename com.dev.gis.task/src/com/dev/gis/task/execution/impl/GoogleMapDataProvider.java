package com.dev.gis.task.execution.impl;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.googleMap.impl.GoogleMapTask;

public class GoogleMapDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	private GoogleMapTask task = null;


	public GoogleMapDataProvider() {
		super(null);
		this.task = new GoogleMapTask();
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
		task = new GoogleMapTask();
		
	}

	@Override
	public void saveTask() {
		// TODO Auto-generated method stub
		
	}

}
