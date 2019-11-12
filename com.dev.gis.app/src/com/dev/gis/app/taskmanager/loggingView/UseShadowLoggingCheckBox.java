package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.CheckBox;

public class UseShadowLoggingCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(UseShadowLoggingCheckBox.class);


	public UseShadowLoggingCheckBox(Composite parent, String name) {
		super(parent, name);
		String useDates = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_USE_SHADOW, "1");
		if ( "1".equals(useDates))
			getButton().setSelection(true);
		else
			getButton().setSelection(false);

		LoggingModelProvider.INSTANCE.useShadow = getButton().getSelection();
		
	}

	@Override
	protected SelectionListener getCheckboxSelectionListener() {
		UseDatesListener listener = new UseDatesListener();
		return listener;
	}
	
	protected class UseDatesListener implements SelectionListener {

		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			LoggingModelProvider.INSTANCE.useShadow = getButton().getSelection();
			logger.info("Change useShadow = "+LoggingModelProvider.INSTANCE.useShadow);
			LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_USE_SHADOW, LoggingModelProvider.INSTANCE.useShadow ? "1":"0");
			
		}

	}

	

}
