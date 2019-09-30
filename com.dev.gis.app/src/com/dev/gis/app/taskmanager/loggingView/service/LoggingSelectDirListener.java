package com.dev.gis.app.taskmanager.loggingView.service;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.ObjectTextControl;


public class LoggingSelectDirListener implements SelectionListener {
	

	private final Shell shell;
	private final ObjectTextControl control;

	public LoggingSelectDirListener(final Shell shell, ObjectTextControl control) {
		super();
		this.shell = shell;
		this.control = control;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		DirectoryDialog dirDialog = new DirectoryDialog(shell);
		    // Set the text
		dirDialog.setText("Select logging dir");		

		// Set the text
		if ( StringUtils.isNotBlank(control.getSelectedValue()))
			dirDialog.setFilterPath(control.getSelectedValue());

		String dir = dirDialog.open();
		
		if (StringUtils.isNotEmpty(dir) )
			control.setSelectedValue(dir);
		

	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	};


}
