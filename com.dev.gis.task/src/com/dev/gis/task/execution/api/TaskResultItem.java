package com.dev.gis.task.execution.api;

import java.util.Date;

public class TaskResultItem {
	
	public String getText() {
		return text;
	}

	public Date date;
	public String text;

	public TaskResultItem() {
	}
	
	public TaskResultItem(String text) {
		super();
		this.text = text;
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}


	
}