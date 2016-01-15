package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.taskmanager.SunnyCarsView.SelectDropoffStationDialog;
import com.dev.gis.connector.api.AdacModelProvider;

public class AdacSelectDropoffStationDialog extends SelectDropoffStationDialog{

	public AdacSelectDropoffStationDialog(Shell parentShell, String offerId, IStationService stationService) {
		super(parentShell, offerId , stationService);
	}
	protected String getModelAirport() {
		return AdacModelProvider.INSTANCE.dropoffAirport;
	}

	protected long getModelCityId() {
		return AdacModelProvider.INSTANCE.dropoffCityId;
	}
	protected long getPickupStationId() {
		return AdacModelProvider.INSTANCE.selectedPickupStationId;
	}

}
