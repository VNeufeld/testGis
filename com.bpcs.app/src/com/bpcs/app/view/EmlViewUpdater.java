package com.bpcs.app.view;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.bpcs.app.View;
import com.dev.http.server.LogEntry;


public class EmlViewUpdater {
	

	public static void  updateTempView(final String text ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				// Show protocol, show results
				IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				final IViewReference[] viewReferences = wp.getViewReferences();
				if ( viewReferences != null && viewReferences.length > 0) {
					for ( IViewReference window : viewReferences) {
				        final IViewPart view = window.getView(true);
						if ( view instanceof View) {
				            final View graphView = (View) view;
				            graphView.outputText(text);
				            return;

						}
					}
				}
				
			}
		});

	}

	public static void updateTable(LogEntry entry) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				// Show protocol, show results
				IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

				final IViewReference[] viewReferences = wp.getViewReferences();
				if ( viewReferences != null && viewReferences.length > 0) {
					for ( IViewReference window : viewReferences) {
				        final IViewPart view = window.getView(true);
						if ( view instanceof View) {
				            final View graphView = (View) view;
				            graphView.updateTable();
				            return;
						}
					}
				}
				
			}
		});
		
	}
}
