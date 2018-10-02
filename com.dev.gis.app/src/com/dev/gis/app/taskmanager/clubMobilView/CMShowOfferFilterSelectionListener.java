package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;

import com.dev.gis.app.taskmanager.testAppView.AdacOfferFilterDialog;
import com.dev.gis.connector.api.ModelProvider;


public class CMShowOfferFilterSelectionListener implements SelectionListener {

	private final Shell shell;
	

	public CMShowOfferFilterSelectionListener(final Shell shell) {
		super();
		this.shell = shell;
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		ClubMobilOfferFilterDialog mpd = new ClubMobilOfferFilterDialog(shell);
		if (mpd.open() == Dialog.OK) {
			ModelProvider.INSTANCE.vehicleRequestFilter = mpd.getVehicleRequestFilter();
		}
	}

}
