package com.dev.gis.task.execution.api;

import javax.xml.bind.JAXBException;

public interface ITaskDataProvider {
	public static String TASK_APP_BOOKING = "TaskAppBooking";
	public static String TASK_APP_PAYMENT = "TaskAppPayment";
	
	final String TASK_ID =  "HSGW_GETCARS";
	
	String getRequest();
	
	ITask   getTask();
	
	void  loadTask(String name) throws JAXBException;
	
	void  saveTask();
	

}
