package com.dev.gis.app.taskmanager.testAppView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.connector.api.AdacModelProvider;

public class CrossOfferCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(CrossOfferCheckBox.class);


	public CrossOfferCheckBox(Composite parent, String name) {
		super(parent, name);
		getButton().setSelection(AdacModelProvider.INSTANCE.crossOfferFlag);
	}

	@Override
	protected SelectionListener getCheckboxSelectionListener() {
		AddCrossOfferListener listener = new AddCrossOfferListener();
		return listener;
	}
	
	protected class AddCrossOfferListener implements SelectionListener {


		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			AdacModelProvider.INSTANCE.crossOfferFlag = getButton().getSelection();
			logger.info("Change CrossOfferFlag = "+AdacModelProvider.INSTANCE.crossOfferFlag);
			
		}

	}

	

}
