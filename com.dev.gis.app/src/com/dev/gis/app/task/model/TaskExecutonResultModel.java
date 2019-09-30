package com.dev.gis.app.task.model;

import java.util.LinkedList;
import java.util.List;

import com.dev.gis.task.execution.api.TaskResultItem;

public class TaskExecutonResultModel {
	private static TaskExecutonResultModel taskExecutonResultModel = null;
	private LinkedList<TaskResultItem> taskResults= new java.util.LinkedList<TaskResultItem>();

	public synchronized void  addItem(String text) {
		taskResults.addFirst(new TaskResultItem(text));
	}
	
	public static TaskExecutonResultModel getInstance() {
		if ( taskExecutonResultModel != null)
			return taskExecutonResultModel;
		taskExecutonResultModel = new TaskExecutonResultModel();

		return taskExecutonResultModel;
	}
	
	public List<TaskResultItem> getTaskList() {
		return taskResults;
	}

}
