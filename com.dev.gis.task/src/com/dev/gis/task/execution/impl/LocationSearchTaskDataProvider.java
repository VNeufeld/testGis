package com.dev.gis.task.execution.impl;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.LocationSearchTask;
import com.dev.gis.task.execution.api.TaskDataProviderException;
import com.dev.gis.task.execution.locationSearch.JoiTask;
import com.dev.gis.task.persistance.impl.LocationSearchPersistanceXmlProvider;

public class LocationSearchTaskDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	private LocationSearchTask task = null;

	public LocationSearchTaskDataProvider(LocationSearchTask task) {
		super(new LocationSearchPersistanceXmlProvider());
		this.task = task;
	}

	public LocationSearchTaskDataProvider() {
		super(new LocationSearchPersistanceXmlProvider());
	}

	@Override
	public String getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITask getTask() {
		return task;
	}

	@Override
	public void loadTask(String name) throws JAXBException  {
		
		task = persistanceProvider.load(name,LocationSearchTask.class);
		
	}

	@Override
	public void saveTask() {
		
		try {
			persistanceProvider.save(task);
		} catch (JAXBException | IOException e) {
			throw new TaskDataProviderException(e);
		}
	}

}
