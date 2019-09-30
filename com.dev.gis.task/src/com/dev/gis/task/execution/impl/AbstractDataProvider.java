package com.dev.gis.task.execution.impl;

import org.apache.log4j.Logger;

import com.dev.gis.task.execution.api.ITaskPersistanceProvider;

public class AbstractDataProvider {
	
	final ITaskPersistanceProvider persistanceProvider;
	
	protected Logger logger = Logger.getLogger(getClass().getName());

	public AbstractDataProvider(ITaskPersistanceProvider persistanceProvider) {
		this.persistanceProvider = persistanceProvider; 
	}

}
