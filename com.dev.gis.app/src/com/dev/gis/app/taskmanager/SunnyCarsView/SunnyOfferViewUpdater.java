package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.connector.api.SunnyOfferDo;
import com.dev.gis.connector.sunny.VehicleResponse;

public class SunnyOfferViewUpdater  {
	private Logger logger = Logger.getLogger(SunnyOfferViewUpdater.class);
	private int instanceNum = 1;
	
	private String viewID = SunnyOfferDetailView.ID;

	public SunnyOfferViewUpdater() {
	}

	public void showOffer(final SunnyOfferDo offer) {
		logger.info(" showResult ID = "+viewID + " offer : " + offer);
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					SunnyOfferDetailView viewPart =  (SunnyOfferDetailView)wp.showView(
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
	
	public void clearView() {
		logger.info(" clear vehicle list");
		
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				try {
					logger.info(" showResult : run instanceNum = "+instanceNum );
					// Show protocol, show results
					IWorkbenchPage   wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
					SunnyCarsAppView viewPart =  (SunnyCarsAppView)wp.showView(
							SunnyCarsAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					viewPart.clearView();
					
					
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
					SunnyCarsAppView viewPart =  (SunnyCarsAppView)wp.showView(
							SunnyCarsAppView.ID, 
							Integer.toString(instanceNum), 
							IWorkbenchPage.VIEW_ACTIVATE);
					viewPart.showVehicleResponse(response);
					
					
				} catch (PartInitException e) {
					logger.error(e.getMessage(),e);
				}
			}
		});

	}
	

}
