package com.dev.gis.app.taskmanager.clubMobilView;

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
import com.dev.gis.connector.api.ClubMobilHttpService;
import com.dev.gis.connector.api.JoiHttpServiceFactory;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.VehicleHttpService;
import com.dev.gis.connector.joi.protocol.VehicleResponse;


public class ClubMobilFilterPageSelectionListener implements SelectionListener {

	private final OutputTextControls pageNoControl;
	private final CheckBox useFilterControl;
	
	private final Shell shell;

	

	public ClubMobilFilterPageSelectionListener(Shell shell, OutputTextControls pageNo,
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
		ClubMobilHttpService service = serviceFactory
				.getClubMobilleJoiService();
		
		
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
			MessageDialog.openError(shell,"CM_Error",err.getMessage());
			return;
		}
		
		if (response != null) {
			new ClubMobilOfferViewUpdater().showResponse(response);
		}
		
	}

}
