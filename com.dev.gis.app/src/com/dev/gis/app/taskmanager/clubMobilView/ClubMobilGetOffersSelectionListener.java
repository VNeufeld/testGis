package com.dev.gis.app.taskmanager.clubMobilView;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.testAppView.OfferViewUpdater;
import com.dev.gis.app.view.listener.adac.AdacCreateVehicleRequestUtils;
import com.dev.gis.app.view.sunny.requestUtils.AdacGetOffersOperation;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;


public class ClubMobilGetOffersSelectionListener implements SelectionListener {

	private final Shell shell;
	

	public ClubMobilGetOffersSelectionListener(final Shell shell) {
		super();
		this.shell = shell;
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		Object obj = arg0.getSource();
		
		getOffer();
		
	}


	private void getOffer() {
		VehicleRequest request = ClubMobilCreateVehicleRequestUtils.createVehicleRequest();
		
		int pageSizeInt = (int)ModelProvider.INSTANCE.pageSize;

		VehicleResponse response = null;				
		
		ProgressMonitorDialog pd = new ProgressMonitorDialog(shell);
		
		try {
			if ( request != null) {
				ClubMobilGetOffersOperation getOfferOperation = new ClubMobilGetOffersOperation(request, pageSizeInt);
				
				pd.run(true, true, getOfferOperation);
				response = getOfferOperation.getResponse();
	
				if (response != null) {
					
					if ( response.getResultList().size() == 0) {
						if ( !response.getErrors().isEmpty()) {
							MessageDialog.openError(
									shell,"Error",response.getErrors().get(0).getErrorText());
							
						}
					}
					
					new ClubMobilOfferViewUpdater().showResponse(response);
					ModelProvider.INSTANCE.pageNo = 0;
				}
			}
		}
		catch(Exception err) {
			String message = err.getMessage();
			if ( err instanceof InvocationTargetException) {
				InvocationTargetException ii = (InvocationTargetException) err;
				if ( ii.getTargetException() != null ) {
					message = ii.getTargetException().getMessage();
				}
			}
			MessageDialog.openError(
					shell,"Error",message);
			
		}
		
		
	}

}
