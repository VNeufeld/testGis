package com.dev.gis.app.taskmanager;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.task.execution.api.ITaskResult;

public abstract class TaskViewAbstract extends ViewPart {


	protected class AbstractListener implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			widgetSelected(arg0);

		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	public void refresh(ITaskResult result) {
		// TODO Auto-generated method stub
		
	}

}
