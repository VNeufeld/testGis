package com.dev.gis.app.taskmanager.loggingView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.taskmanager.loggingView.service.LoggingModelProvider;
import com.dev.gis.app.view.elements.CheckBox;

public class UseDatesCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(UseDatesCheckBox.class);


	public UseDatesCheckBox(Composite parent, String name) {
		super(parent, name);
		String useDates = LoggingSettings.readProperty(LoggingSettings.PREFERENCE_USE_DATES, "0");
		if ( "1".equals(useDates))
			getButton().setSelection(true);
		else
			getButton().setSelection(false);
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
			LoggingModelProvider.INSTANCE.useDates = getButton().getSelection();
			logger.info("Change useDates = "+LoggingModelProvider.INSTANCE.useDates);
			LoggingSettings.saveProperty(LoggingSettings.PREFERENCE_USE_DATES, LoggingModelProvider.INSTANCE.useDates ? "1":"0");
			
		}

	}

	

}
