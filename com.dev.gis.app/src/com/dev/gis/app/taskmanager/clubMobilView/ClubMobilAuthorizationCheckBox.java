package com.dev.gis.app.taskmanager.clubMobilView;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.app.view.elements.CheckBox;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ClubMobilModelProvider;

public class ClubMobilAuthorizationCheckBox extends CheckBox {
	private static Logger logger = Logger.getLogger(ClubMobilAuthorizationCheckBox.class);


	public ClubMobilAuthorizationCheckBox(Composite parent, String name) {
		super(parent, name);
		getButton().setSelection(ClubMobilModelProvider.INSTANCE.authorization);
	}

	@Override
	protected SelectionListener getCheckboxSelectionListener() {
		AddListener listener = new AddListener();
		return listener;
	}
	
	protected class AddListener implements SelectionListener {


		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			
		}

		@Override
		public void widgetSelected(SelectionEvent arg0) {
			ClubMobilModelProvider.INSTANCE.authorization = getButton().getSelection();
			logger.info("Change authorization = "+ClubMobilModelProvider.INSTANCE.authorization);
			
		}

	}

	

}
