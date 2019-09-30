package com.dev.gis.app.taskmanager.loggingView;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.loggingView.service.SessionFileService;
import com.dev.gis.app.view.elements.SessionIdControl;

public class StopButtonListener implements SelectionListener {


	public static boolean terminate = false;
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		terminate = true;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

}
