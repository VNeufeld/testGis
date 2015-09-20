package com.dev.gis.app.taskmanager.SunnyCarsView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.AirportLocationSearch;
import com.dev.gis.connector.api.ModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.TaskProperties;

public class SunnyAirportLocationSearch extends AirportLocationSearch {
	private static Logger logger = Logger.getLogger(SunnyAirportLocationSearch.class);

	private SunnyAirportLocationSearch(Composite parent) {
		super(parent);
	}

	public static void createAirportLocationSearch(Composite parent) {
		new SunnyAirportLocationSearch(parent);
	}


	@Override
	protected String getLabel() {
		return "Search Sunny Airport";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell,
			Text text) {
		SelectionListener selectionListener = new SunnySearchAirportSelectionListener(shell, text, this);
		return selectionListener;
	}
	
	@Override
	public void saveValue(String value) {
		SunnyModelProvider.INSTANCE.airport = value;
		
		logger.info("selected airport : "+SunnyModelProvider.INSTANCE.airport);
		
		if ( !StringUtils.isBlank(value))
			SunnyModelProvider.INSTANCE.cityId = 0;
		
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
