package com.dev.gis.app.taskmanager;

import org.eclipse.ui.part.ViewPart;

import com.dev.gis.task.execution.api.ITaskResult;

public abstract class TaskViewAbstract extends ViewPart {

	public abstract void refresh();
	
	public abstract void refresh(ITaskResult result);


}
