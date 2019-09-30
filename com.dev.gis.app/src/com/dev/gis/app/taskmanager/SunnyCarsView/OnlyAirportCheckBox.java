package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class OnlyAirportCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(OnlyAirportCheckBox.class);


	public OnlyAirportCheckBox(Composite parent, String name) {
		super(parent, name);
		getButton().setSelection(SunnyModelProvider.INSTANCE.onlyAirportFlag);
	}

	@Override
	protected SelectionListener getCheckboxSelectionListener() {
		OnlyAirportListener listener = new OnlyAirportListener();
		return listener;
	}
	
	protected class OnlyAirportListener implements SelectionListener {


		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			SunnyModelProvider.INSTANCE.onlyAirportFlag = getButton().getSelection();
			logger.info("Change onlyAirportFlag = "+SunnyModelProvider.INSTANCE.onlyAirportFlag);
			
		}

	}

	

}
