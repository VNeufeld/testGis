package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.dialogs.LocationSearchDialog;
import com.dev.gis.app.view.elements.LocationSearchText;
import com.dev.gis.app.view.listener.SearchCitySelectionListener;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.sunny.Hit;


public class AdacSearchAirportSelectionListener extends SearchCitySelectionListener {

	protected final Text airportText;


	public AdacSearchAirportSelectionListener(final Shell shell, Text airportText, LocationSearchText parent) {
		super(shell, airportText, parent);
		this.airportText = airportText;
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		
		final long operator = AdacModelProvider.INSTANCE.operatorId;
		
		LocationSearchDialog mpd = new LocationSearchDialog(shell, operator);
		mpd.setAdac(true);
		
		if (mpd.open() == Dialog.OK) {
			if ( mpd.getResult() != null ) {
				Hit hit = mpd.getResult();
				if ( hit.getAptCode() != null ) {
					airportText.setText(hit.getAptCode());
					parent.saveValue(hit.getAptCode());
				}
			}
				
		}
		
	}

}
