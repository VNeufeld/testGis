package com.dev.gis.app.taskmanager.loggingView;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.task.model.FileNameEntryModel;

public class LogFileTableUpdater  {
	private static Logger logger = Logger.getLogger(LogFileTableUpdater.class);
	private static int instanceNum = 1;


	public static void updateFileStatus(final File logFile, final String status) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					FileNameEntryModel.getInstance().setStatus(logFile, status);
					
					viewPart.logFilesTableComposite.update();
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}
	
	public static void updateFileList(final File[] files) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					if (files == null )
						FileNameEntryModel.getInstance().clear();
					else
						FileNameEntryModel.getInstance().create(files);
					
					viewPart.logFilesTableComposite.update();
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}
	

}
