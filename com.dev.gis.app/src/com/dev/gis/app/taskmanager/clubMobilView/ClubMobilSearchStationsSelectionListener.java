package com.dev.gis.app.taskmanager.clubMobilView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.model.StationModel;
import com.dev.gis.app.view.elements.LocationSearchText;
import com.dev.gis.app.view.listener.SearchCitySelectionListener;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilModelProvider;


public class ClubMobilSearchStationsSelectionListener extends SearchCitySelectionListener {

	protected final Text stationText;


	public ClubMobilSearchStationsSelectionListener(final Shell shell, Text cityText, LocationSearchText parent) {
		super(shell, cityText, parent);
		this.stationText = cityText;
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {

		SelectStationDialog mpd = new ClubMobilSelectStationDialog(shell, null);

		if (mpd.open() == Dialog.OK) {
			StationModel st = mpd.getSelectedStation();
			if ( st != null) {
				stationText.setText(st.getId()+ " "+st.getName());
				if ( st.getId() != null) {
					if ( this.parent instanceof ClubMobilStationSearch ) {
						ClubMobilStationSearch search = (ClubMobilStationSearch) parent;
						if ( search.isPickup() )
							ClubMobilModelProvider.INSTANCE.pickupStation = st.getStation();
						else
							ClubMobilModelProvider.INSTANCE.dropoffStation = st.getStation();
						
						parent.saveValue(""+st.getStation().getId());

					}
				}
			}
				
		}
		
	}

}
