package com.dev.gis.task.dialogs;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.jface.dialogs.Dialog;

import com.dev.gis.task.execution.api.ITaskDataProvider;

public class TaskDialogAbstract extends Dialog {
	protected ITaskDataProvider dataProvider;
	
	public TaskDialogAbstract(Shell parent) {
		super(parent);
	}
	
	public void setData(ITaskDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}


}
