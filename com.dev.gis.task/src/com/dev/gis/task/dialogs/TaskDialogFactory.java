package com.dev.gis.task.dialogs;

import org.eclipse.swt.widgets.Shell;

import com.dev.gis.task.execution.api.ITaskDataProvider;
import com.dev.gis.task.execution.impl.LocationSearchTaskDataProvider;

public class TaskDialogFactory {
	public static TaskDialogAbstract   createTaskDialog(ITaskDataProvider dataProvider,Shell shell) {
		if ( dataProvider instanceof LocationSearchTaskDataProvider)
			return new TaskLocationSearchDialog(shell);
		return null;
		
	}

}
