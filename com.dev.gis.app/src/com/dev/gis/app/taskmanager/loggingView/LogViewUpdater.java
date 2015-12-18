package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.loggingView.service.LogEntry;

public class LogViewUpdater  {
	private static Logger logger = Logger.getLogger(LogViewUpdater.class);
	private static int instanceNum = 1;

	public static void  updateView(final String text ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
	}
	
	public static void  updateViewThread(final String text ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(2), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
	}
	
	
	public static void  updateView(final LogEntry entry ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.outputText(entry.getEntry().get(0));

					viewPart.refreshCriteria(entry.getBookingId(), entry.getSessionId());
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}
	
	public static void  updateTempView( ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info("updateTempView ");
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
					            graphView.updateTempView();
								logger.info("updateTempView viewpart  "+view);
					            break;
					        }
					    }
					}
					
					
					LogBookingEntryView viewPart =  (LogBookingEntryView)wp.showView(
							LogBookingEntryView.ID, 
							Integer.toString(0), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	


}
