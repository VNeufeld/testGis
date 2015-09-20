package com.dev.gis.app.taskmanager.testAppView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.TaskProperties;

public class AdacAirportLocationSearch extends AirportLocationSearch {
	private static Logger logger = Logger.getLogger(AdacAirportLocationSearch.class);

	private AdacAirportLocationSearch(Composite parent) {
		super(parent);
	}

	public static void createAirportLocationSearch(Composite parent) {
		new AdacAirportLocationSearch(parent);
	}


	@Override
	protected String getLabel() {
		return "Search Adac Airport";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell,
			Text text) {
		SelectionListener selectionListener = new AdacSearchAirportSelectionListener(shell, text, this);
		return selectionListener;
	}
	
	@Override
	public void saveValue(String value) {
		AdacModelProvider.INSTANCE.airport = value;
		
		logger.info("selected airport : "+AdacModelProvider.INSTANCE.airport);
		
		if ( !StringUtils.isBlank(value))
			AdacModelProvider.INSTANCE.cityId = 0;
		
		TaskProperties.getTaskProperties().setAptCode(value);
		TaskProperties.getTaskProperties().saveProperty();
		
	}

	@Override
	protected String getDefaultValue() {
		String apt = TaskProperties.getTaskProperties().getAptCode();
		if ( StringUtils.isEmpty(apt))
			apt = "PMI";
		return apt;
	}

}
