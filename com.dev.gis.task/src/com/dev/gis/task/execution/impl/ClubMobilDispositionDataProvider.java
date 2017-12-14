package com.dev.gis.task.execution.impl;

import javax.xml.bind.JAXBException;

import com.dev.gis.task.execution.api.ITask;
import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.impl.AbstractDataProvider;

public class ClubMobilDispositionDataProvider extends AbstractDataProvider implements ITaskDataProvider {
	private ClubMobilDispositionTask task = null;


	public ClubMobilDispositionDataProvider() {
		super(null);
		this.task = new ClubMobilDispositionTask();
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
		task = new ClubMobilDispositionTask();
		
	}

	@Override
	public void saveTask() {
		// TODO Auto-generated method stub
		
	}

}
