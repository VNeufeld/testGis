package com.dev.gis.app.view.listener;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.sunny.VehicleResponse;


public class SunnyGetNextPageSelectionListener implements SelectionListener {

	private final OutputTextControls pageNoControl;
	private final CheckBox useFilterControl;
	private final Shell shell;


	public SunnyGetNextPageSelectionListener(final Shell shell, OutputTextControls pageNo,
			CheckBox useFilter) {
		super();
		this.pageNoControl = pageNo;
		this.useFilterControl = useFilter;
		this.shell = shell;
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		boolean useFilter = useFilterControl.isSelected();
		
		if ( pageNoControl.getValue().isEmpty() || !StringUtils.isNumeric(pageNoControl.getValue()))
			pageNoControl.setValue("0");
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();

		VehicleHttpService service = serviceFactory
				.getVehicleJoiService();

		int pageSizeInt = (int) ModelProvider.INSTANCE.pageSize;
		
		VehicleResponse response = null;

		
		try {
			if ( pageNoControl.getValue().isEmpty() || !StringUtils.isNumeric(pageNoControl.getValue()))
				pageNoControl.setValue("0");
				
			int pageNo = Integer.valueOf(pageNoControl.getValue());
		
			if ( useFilter)
				response = service.getBrowsePage(pageNo,pageSizeInt);
			else
				response = service.getPage(pageNo);
		}
		catch(Exception err) {
			MessageDialog.openError(shell,"Error",err.getMessage());
			return;
		}
		
		if (response != null) {
			new SunnyOfferViewUpdater().showResponse(response);
		}

		
	}


}
