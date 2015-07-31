package com.dev.gis.app.view.listener.adac;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.offerDetailView.OfferViewUpdater;
import com.dev.gis.app.view.sunny.requestUtils.AdacGetOffersOperation;
import com.dev.gis.connector.joi.protocol.VehicleRequest;
import com.dev.gis.connector.joi.protocol.VehicleResponse;
import com.dev.gis.task.execution.api.ModelProvider;


public class AdacJoiGetOffersSelectionListener implements SelectionListener {

	private final Shell shell;
	

	public AdacJoiGetOffersSelectionListener(final Shell shell) {
		super();
		this.shell = shell;
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		Object obj = arg0.getSource();
		
		getOffer1();
		
		//buttonGetOffer.setEnabled(true);
		
	}


	private void getOffer1() {
		VehicleRequest request = CreateVehicleRequestUtils.createVehicleRequest();
		
		int pageSizeInt = (int)ModelProvider.INSTANCE.pageSize;

		VehicleResponse response = null;				
		AdacGetOffersOperation getOfferOperation = new AdacGetOffersOperation(request, pageSizeInt);
		
		ProgressMonitorDialog pd = new ProgressMonitorDialog(shell);
		
		try {
			pd.run(true, true, getOfferOperation);
			response = getOfferOperation.getResponse();

			if (response != null) {
				new OfferViewUpdater().showResponse(response);
				ModelProvider.INSTANCE.pageNo = 0;
			}
			
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
