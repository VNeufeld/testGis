package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.taskmanager.SunnyCarsView.SunnyExtServcatFilterTextControl;
import com.dev.gis.app.view.elements.ObjectTextControl;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class AdacExtServcatFilterTextControl extends SunnyExtServcatFilterTextControl {
	
	private static Logger logger = Logger.getLogger(AdacExtServcatFilterTextControl.class);

	public AdacExtServcatFilterTextControl(final Composite parent) {
		super(parent);
		
	}

	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.servcatFilter = value;
		logger.info("servcatFilter: "+value);
	
	}

}
