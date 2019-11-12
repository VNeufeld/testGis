package com.dev.gis.app.taskmanager.loggingView;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.task.model.LogEntryModel;
import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;

public class LogEntryTableUpdater  {
	private static Logger logger = Logger.getLogger(LogEntryTableUpdater.class);
	private static int instanceNum = 1;

	public LogEntryTableUpdater() {
	}

	public static void showResult(final List<LogEntry> entries, final int viewNo) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LogEntryTableView viewPart =  (LogEntryTableView)wp.showView(
							LogEntryTableView.ID, 
							Integer.toString(viewNo), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					if (entries != null )
						LogEntryModel.getInstance().updateLoggingEntries((entries));
					
					viewPart.update();
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}

	public static void showResultTable(final List<LogEntry> entries, final int viewNo) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LogEntryTableView viewPart =  (LogEntryTableView)wp.showView(
							LogEntryTableView.ID, 
							Integer.toString(viewNo), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					if ( viewNo == 2)
						viewPart.setName("Thread Search");
					
					viewPart.update(entries);
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
		
	}

}
