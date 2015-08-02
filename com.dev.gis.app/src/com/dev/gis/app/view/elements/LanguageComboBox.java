package com.dev.gis.app.view.elements;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Composite;

import com.dev.gis.connector.api.ModelProvider;

public class LanguageComboBox extends ObjectsComboBox{
	
	private static final String PREFERENCE_PROPERTY = "LANGUAGE_PROPERTY";
	
	private static Logger logger = Logger.getLogger(LanguageComboBox.class);
	
	private final static String items[] = { "deu ( DE )", "eng ( EN) " };
	
	public LanguageComboBox(Composite parent, int size) {
		super(parent, size);
	}

	public final String getSelectedLanguage() {
		return selectedValue;
	}


	@Override
	protected String getLabel() {
		return "Language";
	}
	protected String getPropertyName() {
		return PREFERENCE_PROPERTY;
	}

	@Override
	protected String[] getItems() {
		return items;
	}

	@Override
	protected void logSelectedValue(int index) {
		logger.info("select language "+ index + " languageCode = "+ selectedValue);
	}

	@Override
	protected void saveSelected(int index, String st) {
		
		String selectedLanguage;
		if ( st.contains("EN"))
			selectedLanguage = "EN";
		else
			selectedLanguage = "DE";
		
		ModelProvider.INSTANCE.languageCode = selectedLanguage;
		ModelProvider.INSTANCE.languageId = index +1;

		selectedValue = selectedLanguage;

		saveProperty(PREFERENCE_PROPERTY,String.valueOf(index));
		
		
	}

}
