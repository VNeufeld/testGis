package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.connector.api.SunnyModelProvider;

public class OnlyCityCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(OnlyCityCheckBox.class);


	public OnlyCityCheckBox(Composite parent, String name) {
		super(parent, name);
		getButton().setSelection(SunnyModelProvider.INSTANCE.onlyCityFlag);
		
	}

	@Override
	protected SelectionListener getCheckboxSelectionListener() {
		OnlyCityListener listener = new OnlyCityListener();
		return listener;
	}
	
	protected class OnlyCityListener implements SelectionListener {


		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			SunnyModelProvider.INSTANCE.onlyCityFlag = getButton().getSelection();
			logger.info("Change onlyCityFlag = "+SunnyModelProvider.INSTANCE.onlyCityFlag);
			
		}

	}

	

}
