package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.dev.gis.connector.api.OfferDo;
import com.dev.gis.connector.joi.protocol.VehicleResponse;

public class ClubMobilOfferViewUpdater  {
	private Logger logger = Logger.getLogger(ClubMobilOfferViewUpdater.class);
	private int instanceNum = 0;
	
	private String viewID = ClubMobilOfferDetailView.ID;

	public ClubMobilOfferViewUpdater() {
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
					ClubMobilOfferDetailView viewPart =  (ClubMobilOfferDetailView)wp.showView(
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
					ClubMobilView viewPart =  (ClubMobilView)wp.showView(
							ClubMobilView.ID, 
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
					ClubMobilView viewPart =  (ClubMobilView)wp.showView(
							ClubMobilView.ID, 
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
