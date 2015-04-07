package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.bpcs.mdcars.protocol.Offer;
import com.dev.gis.app.taskmanager.offerDetailView.OfferDetailView;
import com.dev.gis.task.execution.api.IResultView;
import com.dev.gis.task.execution.api.ITaskResult;
import com.dev.gis.task.execution.api.OfferDo;
import com.dev.gis.task.execution.api.SunnyOfferDo;

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

}
