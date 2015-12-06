package com.dev.gis.app.taskmanager.loggingView.service;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.view.elements.ObjectTextControl;


public class LoggingSelectFileListener implements SelectionListener {
	

	private final Shell shell;
	private final ObjectTextControl control;

	public LoggingSelectFileListener(final Shell shell, ObjectTextControl control) {
		super();
		this.shell = shell;
		this.control = control;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);
		    // Set the text
		fileDialog.setText("Select logging dir");		

		// Set the text
//		if ( StringUtils.isNotBlank(control.getSelectedValue()))
//			fileDialog.setFilterPath(control.getSelectedValue());

		String fileName = fileDialog.open();
		
		if (StringUtils.isNotEmpty(fileName) )
			control.setSelectedValue(fileName);
		

	}

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	};


}
