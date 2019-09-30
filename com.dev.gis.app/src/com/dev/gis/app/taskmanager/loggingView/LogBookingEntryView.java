package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;
import com.dev.gis.app.taskmanager.loggingView.service.LoggingSelectDirListener;
import com.dev.gis.app.view.elements.ButtonControl;
import com.dev.gis.app.view.elements.ObjectTextControl;

public class LogBookingEntryView extends ViewPart {
	public static final String ID = "com.dev.gis.app.task.LoggingTableView";
	
	private final static Logger logger = Logger.getLogger(LogBookingEntryView.class);
	
	
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

	private SelectionListener selectDirListener(final Composite composite, ObjectTextControl dirControl) {
		
		LoggingSelectDirListener listener = new LoggingSelectDirListener(composite.getShell(), dirControl);
		return listener;
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

	public static void  updateView() {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					
					final int instanceNum = 8;//the number of instances that are created
					for (int index = 0; index < instanceNum; index++) {
					    final IViewReference viewReference = wp.findViewReference(
					    		LogBookingEntryView.ID, Integer.toString(index));
					
					    if (viewReference != null) {
					        final IViewPart view = viewReference.getView(true);
					        if (view instanceof LogBookingEntryView) {
					            final LogBookingEntryView graphView = (LogBookingEntryView) view;
					            graphView.logTable.update();
					            break;
					        }
					    }
					}
					
					LogBookingEntryView viewPart =  (LogBookingEntryView)wp.showView(
							LogBookingEntryView.ID, 
							Integer.toString(0), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.logTable.update();
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	
	public void updateTempView() {
		if ( logTable != null)
			logTable.updateTemp();
	}




}
