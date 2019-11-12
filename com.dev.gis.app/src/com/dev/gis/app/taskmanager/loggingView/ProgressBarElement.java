package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ProgressBarElement {
	private static Logger logger = Logger.getLogger(ProgressBarElement.class);
	
	private Text pbText;
	private ProgressBar pb;

	
	void create(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false).applyTo(composite);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).span(2, 1).applyTo(composite);

		GridData gdText = new GridData();
		gdText.grabExcessHorizontalSpace = false;
		gdText.grabExcessVerticalSpace = false;
		gdText.horizontalAlignment = SWT.FILL;
		gdText.verticalAlignment = SWT.FILL;
		gdText.widthHint = 500;
		
		pbText = new Text(composite, SWT.BORDER | SWT.SINGLE);
		pbText.setEditable(false);
		pbText.setText(".");
		pbText.setLayoutData(gdText);

		pb = new  ProgressBar(composite, SWT.NULL);
		pb.setSelection(0);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(pb);

	}
	
	
	public static void  updateProgressBar(final long size, final long maxSize ) {
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(1), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.progressBarElement.updateProgressb(size, maxSize);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

		
	}
	
	public static void updateFileName(final String text) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					LoggingAppView viewPart =  (LoggingAppView)wp.showView(
							LoggingAppView.ID, 
							Integer.toString(1), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					viewPart.progressBarElement.updateProgressText(text);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});
		
	}

	protected void updateProgressText(String text) {
		pbText.setText(text);
		pbText.setForeground(pbText.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
		
	}
	

	protected void updateProgressb(final long size, final long maxSize) {
		int imax = (int) ( maxSize / 1000 );
		int isize = (int) ( size / 1000 );
		pb.setMaximum(imax);
		pb.setSelection(isize);

	}


	public void reset() {
		pb.setSelection(0);
	}

}
