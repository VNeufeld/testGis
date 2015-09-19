package com.dev.gis.app.view.listener;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.app.view.sunny.requestUtils.CreateVehicleRequestUtils;
import com.dev.gis.app.view.sunny.requestUtils.GetOffersOperation;
import com.dev.gis.app.view.sunny.requestUtils.ShowTimer;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.VehicleRequest;
import com.dev.gis.connector.sunny.VehicleResponse;


public class SunnyGetOffersSelectionListener implements SelectionListener {

	private final Shell shell;
	

	public SunnyGetOffersSelectionListener(final Shell shell) {
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

		
		//buttonGetOffer.setEnabled(true);
		
	}


	private void getOffer() {
		VehicleRequest request = CreateVehicleRequestUtils.createVehicleRequest();
		
		int pageSizeInt = (int)ModelProvider.INSTANCE.pageSize;

		VehicleResponse response = null;				
		GetOffersOperation getOfferOperation = new GetOffersOperation(request, pageSizeInt);
		
		ProgressMonitorDialog pd = new ProgressMonitorDialog(shell);
		
		try {
			pd.run(true, true, getOfferOperation);
			response = getOfferOperation.getResponse();

			if (response != null) {
				new SunnyOfferViewUpdater().showResponse(response);
				ModelProvider.INSTANCE.pageNo = 0;
			}
			
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	

}
