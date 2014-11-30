package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class LoggingTableView extends ViewPart {
	public static final String ID = "com.dev.gis.app.task.LoggingTableView";
	
	private final static Logger logger = Logger.getLogger(LoggingTableView.class);
	
	
	private LogTable  logTable;
	
	
	
	@Override
	public void createPartControl(final Composite parent) {
		
		//cursorManager = new CursorManager(parent);
		
		final Composite group = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).equalWidth(true).applyTo(group);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(group);

		Composite compositeLogTable = createCompositeLogTable(group);

		logTable = new LogTable(compositeLogTable,getSite());
		
	}




	private Composite createCompositeLogTable(final Composite group) {
		Composite compositeLogTable = new Composite(group, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(1).applyTo(compositeLogTable);

		GridData gdLogTable = new GridData();
		gdLogTable.grabExcessHorizontalSpace = true;
		//gdLogTable.grabExcessVerticalSpace = true;
		gdLogTable.horizontalAlignment = SWT.FILL;
		gdLogTable.verticalAlignment = SWT.FILL;
		gdLogTable.heightHint = 800;
		gdLogTable.horizontalSpan = 1;

		compositeLogTable.setLayoutData(gdLogTable);
		return compositeLogTable;
	}





	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}





}
