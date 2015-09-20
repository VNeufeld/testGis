package com.dev.gis.app.taskmanager.testAppView;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.elements.CityLocationSearch;
import com.dev.gis.connector.api.AdacModelProvider;
import com.dev.gis.connector.api.SunnyModelProvider;

public class AdacCityLocationSearch extends CityLocationSearch {

	private static Logger logger = Logger.getLogger(AdacCityLocationSearch.class);

	public static void createCityLocationSearch(Composite parent) {
		new AdacCityLocationSearch(parent);
	}

	private AdacCityLocationSearch(Composite parent) {
		super(parent);
	}

	@Override
	protected String getLabel() {
		return "Search Sunny City";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell, Text text) {
		SelectionListener selectionListener = new AdacSearchCitySelectionListener(
				shell, text, this);
		return selectionListener;
	}

	@Override
	public void saveValue(String value) {
		if (!StringUtils.isEmpty(value)) {
			AdacModelProvider.INSTANCE.cityId = Long.valueOf(value);
			logger.info("selected city : " + AdacModelProvider.INSTANCE.cityId);

			AdacModelProvider.INSTANCE.airport = null;
			logger.info("selected airport : " + AdacModelProvider.INSTANCE.airport);
		}

	}

}
