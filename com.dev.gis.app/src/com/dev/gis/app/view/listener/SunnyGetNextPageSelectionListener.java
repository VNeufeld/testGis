package com.dev.gis.app.view.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.VehicleResponse;


public class SunnyGetNextPageSelectionListener implements SelectionListener {


	public SunnyGetNextPageSelectionListener(final Shell shell) {
		super();
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();

		VehicleHttpService service = serviceFactory
				.getVehicleJoiService();
		
		ModelProvider.INSTANCE.pageNo++;
		VehicleResponse response = service.getPage((int)ModelProvider.INSTANCE.pageNo);
		
		if (response != null) {
			new SunnyOfferViewUpdater().showResponse(response);
		}
		
	}


}
