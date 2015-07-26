package com.dev.gis.app.view.elements;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.SunnyModelProvider;
import com.dev.gis.connector.api.TaskProperties;
import com.dev.gis.task.execution.api.ModelProvider;

public class LanguageComboBox extends ObjectsComboBox{
	
	private static Logger logger = Logger.getLogger(LanguageComboBox.class);
	
	private final static String items[] = { "deu ( DE )", "eng ( EN) " };
	
	public LanguageComboBox(Composite parent, int size) {
		super(parent, size);
	}

	public final String getSelectedLanguage() {
		return selectedValue;
	}

	@Override
	protected String getSelectedValue(int index, String st) {
		String selectedLanguage;
		if ( st.contains("EN"))
			selectedLanguage = "EN";
		else
			selectedLanguage = "DE";
		
		SunnyModelProvider.INSTANCE.languageCode = selectedLanguage;
		SunnyModelProvider.INSTANCE.languageId = index +1;

		return selectedLanguage;
	}

	@Override
	protected int getSavedSelectedId() {
		return TaskProperties.getTaskProperties().getLanguage();
	}

	@Override
	protected void saveProperties(int index) {
		TaskProperties.getTaskProperties().setLanguage(index);
	}

	@Override
	protected String getLabel() {
		return "Language";
	}

	@Override
	protected String[] getItems() {
		return items;
	}

	@Override
	protected void logSelectedValue(int index) {
		logger.info("select language "+ index + " languageCode = "+ selectedValue);
	}

}
