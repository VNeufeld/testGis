package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.model.IStationService;
import com.dev.gis.app.taskmanager.SunnyCarsView.SelectPickupStationDialog;
import com.dev.gis.connector.api.AdacModelProvider;

public class AdacSelectPickupStationDialog extends SelectPickupStationDialog{

	public AdacSelectPickupStationDialog(Shell parentShell, String offerId, IStationService stationService) {
		super(parentShell, offerId, stationService);
	}
	protected String getModelAirport() {
		return AdacModelProvider.INSTANCE.airport;
	}

	protected long getModelCityId() {
		return AdacModelProvider.INSTANCE.cityId;
	}

}
