package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyExtStationFilterTextControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class AdacExtStationFilterTextControl extends SunnyExtStationFilterTextControl {
	
	private static Logger logger = Logger.getLogger(AdacExtStationFilterTextControl.class);

	public AdacExtStationFilterTextControl(final Composite parent) {
		super(parent);
	}

	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.stationFilter = value;
		logger.info("Station Filter: "+value);
	
	}

}
