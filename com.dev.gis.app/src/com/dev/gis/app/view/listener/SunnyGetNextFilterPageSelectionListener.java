package com.dev.gis.app.view.listener;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.VehicleResponse;
import com.dev.gis.task.execution.api.ModelProvider;


public class SunnyGetNextFilterPageSelectionListener implements SelectionListener {

	private final Text browseFilter;

	public SunnyGetNextFilterPageSelectionListener(final Text text) {
		super();
		browseFilter = text;
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();

		VehicleHttpService service = serviceFactory
				.getVehicleJoiService();
		
		int pageSizeInt = (int) ModelProvider.INSTANCE.pageSize;
		
		int pageNo = 0;
		
		VehicleResponse response = service.getBrowsePage(browseFilter.getText(),pageNo,pageSizeInt);
		
		if (response != null) {
			new SunnyOfferViewUpdater().showResponse(response);
		}
		
	}

}
