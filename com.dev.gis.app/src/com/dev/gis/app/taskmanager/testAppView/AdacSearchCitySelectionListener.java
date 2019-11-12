package com.dev.gis.app.taskmanager.testAppView;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.dialogs.LocationSearchDialog;
import com.dev.gis.app.view.elements.LocationSearchText;
import com.dev.gis.app.view.listener.SearchCitySelectionListener;
import com.dev.gis.connector.api.AdacModelProvider;


public class AdacSearchCitySelectionListener extends SearchCitySelectionListener {

	protected final Text cityText;


	public AdacSearchCitySelectionListener(final Shell shell, Text cityText, LocationSearchText parent) {
		super(shell, cityText, parent);
		this.cityText = cityText;
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
		final long operator = AdacModelProvider.INSTANCE.operatorId;

		LocationSearchDialog mpd = new LocationSearchDialog(shell, operator);
		mpd.setAdac(true);

		if (mpd.open() == Dialog.OK) {
			if ( mpd.getResult() != null ) {
				String  id = String.valueOf(mpd.getResult().getId());
				cityText.setText(id);
				parent.saveValue(id);
			}
				
		}
		
	}

}
