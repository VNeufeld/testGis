package com.dev.gis.task.persistance.impl;

import java.util.ArrayList;
import java.util.List;

import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.TaskResultItem;

public class TaskResultImpl implements ITaskResult {
	

	String result ;
	
	List<TaskResultItem> taskResultItems = new ArrayList<TaskResultItem>(); 
	
	public TaskResultImpl(String result) {
		super();
		this.result = result;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	@Override
	public List<TaskResultItem> getTaskList() {
		return taskResultItems;
	}
	
	

}
