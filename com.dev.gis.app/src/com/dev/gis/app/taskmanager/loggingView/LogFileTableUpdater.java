package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class LogFileTableUpdater  {
	private static Logger logger = Logger.getLogger(LogFileTableUpdater.class);
	private static int instanceNum = 1;

	public LogFileTableUpdater() {
	}

	public static void showResult() {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.logFilesTableComposite.update();
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}

}
