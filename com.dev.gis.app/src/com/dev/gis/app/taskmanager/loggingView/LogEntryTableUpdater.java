package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class LogEntryTableUpdater  {
	private static Logger logger = Logger.getLogger(LogEntryTableUpdater.class);
	private static int instanceNum = 1;

	public LogEntryTableUpdater() {
	}

	public static void showResult() {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LogEntryTableView viewPart =  (LogEntryTableView)wp.showView(
							LogEntryTableView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.update();
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}

}
