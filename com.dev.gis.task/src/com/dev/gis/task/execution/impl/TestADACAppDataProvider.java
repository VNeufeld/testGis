package com.dev.gis.task.execution.impl;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.api.JoiTask;
import com.dev.gis.task.execution.api.TestADACAppTask;
import com.dev.gis.task.persistance.impl.TestADACAppPersistanceProvider;

public class TestADACAppDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	private TestADACAppTask task = null;


	public TestADACAppDataProvider() {
		super(new TestADACAppPersistanceProvider());
		this.task = new TestADACAppTask();
	}

	@Override
	public String getRequest() {
		return null;
	}

	@Override
	public JoiTask getTask() {
		return task;
	}

	@Override
	public void loadTask(String name) throws JAXBException {
		task = new TestADACAppTask();
		
	}

	@Override
	public void saveTask() {
		// TODO Auto-generated method stub
		
	}

}
