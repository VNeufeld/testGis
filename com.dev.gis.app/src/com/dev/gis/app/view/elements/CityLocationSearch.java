package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.dev.gis.app.view.listener.SearchCitySelectionListener;
import com.dev.gis.task.execution.api.ModelProvider;

public class CityLocationSearch extends LocationSearchText {
	
	private static Logger logger = Logger.getLogger(CityLocationSearch.class);

	public static void createCityLocationSearch(Composite parent) {
		new CityLocationSearch(parent);
	}

	public CityLocationSearch(Composite parent) {
		super(parent, 300, true);
	}

	@Override
	protected String getLabel() {
		return "Search City";
	}

	@Override
	protected SelectionListener getSelectionListener(Shell shell,
			Text text) {
		SelectionListener selectionListener = new SearchCitySelectionListener(shell, text, this);
		return selectionListener;
	}

	@Override
	public void saveValue(String value) {
		ModelProvider.INSTANCE.cityId = Long.valueOf(value);
		logger.info("selected city : "+value);
//		TaskProperties.getTaskProperties().setServerProperty(
//				serverUrl.getText());
//		TaskProperties.getTaskProperties().saveProperty();
		
	}

}
