package com.dev.gis.task.execution.impl;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.testADACApp.impl.TestADACAppPaymentTask;
import com.dev.gis.task.execution.testADACApp.impl.TestADACAppTask;
import com.dev.gis.task.persistance.impl.TestADACAppPersistanceProvider;

public class TestADACAppPaymentDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	private TestADACAppPaymentTask task = null;


	public TestADACAppPaymentDataProvider() {
		super(new TestADACAppPersistanceProvider());
		this.task = new TestADACAppPaymentTask();
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
		task = new TestADACAppPaymentTask();
		
	}

	@Override
	public void saveTask() {
		// TODO Auto-generated method stub
		
	}

}
