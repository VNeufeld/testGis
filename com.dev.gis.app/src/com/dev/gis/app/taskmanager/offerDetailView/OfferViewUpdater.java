package com.dev.gis.app.taskmanager.offerDetailView;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyCarsAppView;
import com.dev.gis.app.taskmanager.testAppView.TestAppView;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.IResultView;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.OfferDo;

public class OfferViewUpdater  {
	private Logger logger = Logger.getLogger(OfferViewUpdater.class);
	private int instanceNum = 1;
	
	private String viewID = OfferDetailView.ID;

	public OfferViewUpdater() {
	}

	public void showOffer(final OfferDo offer) {
		logger.info(" showResult ID = "+viewID + " offer : " + offer);
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					OfferDetailView viewPart =  (OfferDetailView)wp.showView(
							viewID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					
					if ( offer != null)
						viewPart.showOffer(offer);
					
					instanceNum++;
					
					logger.info(" add view :"+viewPart.getTitle());
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}
	
	public void showResponse(final VehicleResponse response) {
		logger.info(" clear vehicle list");
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					TestAppView viewPart =  (TestAppView)wp.showView(
							TestAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					viewPart.showVehicleResponse(response);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}
	
	
	public void clearView() {
		logger.info(" clear vehicle list");
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					TestAppView viewPart =  (TestAppView)wp.showView(
							TestAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					viewPart.clearView();
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}



}
