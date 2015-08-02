package com.dev.gis.app.view.listener.adac;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyOfferViewUpdater;
import com.dev.gis.app.taskmanager.testAppView.OfferViewUpdater;
import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.app.view.elements.OutputTextControls;
import com.dev.gis.connector.api.AdacVehicleHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.joi.protocol.VehicleResponse;


public class AdacGetNextFilterPageSelectionListener implements SelectionListener {

	private final OutputTextControls pageNoControl;
	private final CheckBox useFilterControl;
	
	private final Shell shell;

	

	public AdacGetNextFilterPageSelectionListener(Shell shell, OutputTextControls pageNo,
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
	public void widgetSelected(SelectionEvent event) {
		
		JoiHttpServiceFactory serviceFactory = new JoiHttpServiceFactory();

		AdacVehicleHttpService service = serviceFactory.getAdacVehicleJoiService();
		
		
		boolean useFilter = useFilterControl.isSelected();
		
		VehicleResponse response = null;
		
		try {
			if ( pageNoControl.getValue().isEmpty() || !StringUtils.isNumeric(pageNoControl.getValue()))
				pageNoControl.setValue("0");
				
			int pageNo = Integer.valueOf(pageNoControl.getValue());
		
			if ( useFilter)
				response = service.getBrowsePage(pageNo,ModelProvider.INSTANCE.vehicleRequestFilter);
			else
				response = service.getPage(pageNo);
		}
		catch(Exception err) {
			MessageDialog.openError(shell,"Error",err.getMessage());
			return;
		}
		
		if (response != null) {
			new OfferViewUpdater().showResponse(response);
		}
		
	}

}
