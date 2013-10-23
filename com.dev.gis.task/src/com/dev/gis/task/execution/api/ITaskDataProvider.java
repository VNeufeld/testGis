package com.dev.gis.task.execution.api;

import javax.xml.bind.JAXBException;

public interface ITaskDataProvider {
	final String TASK_ID =  "HSGW_GETCARS";
	
	String getRequest();
	
	ITask   getTask();
	
	void  loadTask(String name) throws JAXBException;
	
	void  saveTask();
	

}
