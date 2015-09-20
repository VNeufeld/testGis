package com.dev.gis.app.view.listener;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.dialogs.LocationSearchDialog;
import com.dev.gis.app.view.elements.LocationSearchText;
import com.dev.gis.connector.api.AdacModelProvider;


public class SearchCitySelectionListener implements SelectionListener {

	protected final Shell shell;
	protected final Text cityText;
	
	protected LocationSearchText parent;
	

	public SearchCitySelectionListener(final Shell shell, Text cityText, LocationSearchText parent) {
		super();
		this.shell = shell;
		this.cityText = cityText;
		
		this.parent = parent;
		
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent arg0) {
//		
//		LocationSearchDialog mpd = new LocationSearchDialog(shell, AdacModelProvider.INSTANCE.operatorId);
//		
//		if (mpd.open() == Dialog.OK) {
//			if ( mpd.getResult() != null ) {
//				String  id = String.valueOf(mpd.getResult().getId());
//				cityText.setText(id);
//				parent.saveValue(id);
//			}
//				
//		}
		
	}


}
