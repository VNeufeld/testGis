package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.listener.SearchCitySelectionListener;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.task.execution.api.ModelProvider;

public class AirportLocationSearch extends LocationSearchText {
	private static Logger logger = Logger.getLogger(AirportLocationSearch.class);

	public static void createAirportLocationSearch(Composite parent) {
		new AirportLocationSearch(parent);
	}

	public AirportLocationSearch(Composite parent) {
		super(parent, 300, true);
	}

	@Override
	protected String getLabel() {
		return "Search Airport";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell,
			Text text) {
		SelectionListener selectionListener = new SearchCitySelectionListener(shell, text, this);
		return selectionListener;
	}
	@Override
	public void saveValue(String value) {
		ModelProvider.INSTANCE.airport = value;
		logger.info("selected airport : "+value);
		
		TaskProperties.getTaskProperties().setAptCode(value);
		TaskProperties.getTaskProperties().saveProperty();
		
	}

	@Override
	protected String getDefaultValue() {
		return TaskProperties.getTaskProperties().getAptCode();
	}

}
