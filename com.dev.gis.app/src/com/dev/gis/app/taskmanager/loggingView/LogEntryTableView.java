package com.dev.gis.app.taskmanager.loggingView;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingExportFileListener;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingSelectDirListener;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingSelectFileListener;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class LogEntryTableView extends ViewPart {
	public static final String ID = "com.dev.gis.app.task.LogEntryTableView";
	
	private final static Logger logger = Logger.getLogger(LogEntryTableView.class);
	
	private LogEntryTable  logEntryTable;
	
	public void setName(String name) {
		this.setPartName(name);
	}
	
	@Override
	public void createPartControl(final Composite parent) {
		
		
		final Composite group = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(group);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);
		
		Composite composite = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).equalWidth(false).applyTo(composite);
		LogOutputDirControl logOutputFileControl = new LogOutputDirControl(composite);
		
		new ButtonControl(composite, "Select Output File", 0,  selectFileListener(composite, logOutputFileControl));
		
		new ButtonControl(composite, "Export", 0,  exportFileListener(composite));

		Composite compositeLogTable = createCompositeLogTable(group);

		logEntryTable = new LogEntryTable(compositeLogTable,getSite());
		
	}

	private SelectionListener selectFileListener(final Composite composite, ObjectTextControl dirControl) {
		
		LoggingSelectFileListener listener = new LoggingSelectFileListener(composite.getShell(), dirControl);
		return listener;
	}

	private SelectionListener exportFileListener(final Composite composite) {
		
		LoggingExportFileListener listener = new LoggingExportFileListener();
		return listener;
	}



	private Composite createCompositeLogTable(final Composite group) {
		Composite compositeLogTable = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(compositeLogTable);

		GridData gdLogTable = new GridData();
		gdLogTable.grabExcessHorizontalSpace = true;
		gdLogTable.grabExcessVerticalSpace = true;
		gdLogTable.horizontalAlignment = SWT.FILL;
		gdLogTable.verticalAlignment = SWT.FILL;
		//gdLogTable.heightHint = 800;
		gdLogTable.horizontalSpan = 1;

		compositeLogTable.setLayoutData(gdLogTable);
		return compositeLogTable;
	}



	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}




	public void update() {
		logEntryTable.update();
	}

	public void update(List<LogEntry> entries) {
		logEntryTable.update(entries);
	}

}
