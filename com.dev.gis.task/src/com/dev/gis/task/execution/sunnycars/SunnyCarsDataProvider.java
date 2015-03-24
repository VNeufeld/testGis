package com.dev.gis.task.execution.sunnycars;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.impl.AbstractDataProvider;

public class SunnyCarsDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	private SunnyCarsTask task = null;


	public SunnyCarsDataProvider() {
		super(null);
		this.task = new SunnyCarsTask();
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
		task = new SunnyCarsTask();
		
	}

	@Override
	public void saveTask() {
		// TODO Auto-generated method stub
		
	}

}
